package hr.fer.zemris.ppj.lab3.rules.expression;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Array;
import hr.fer.zemris.ppj.lab3.types.Char;
import hr.fer.zemris.ppj.lab3.types.ConstChar;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab4.GeneratorKoda;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;


/**
 * @author Herman Zvonimir Dosilovic
 */
public class PrimarniIzraz extends Rule {
  public static PrimarniIzraz PRIMARNI_IZRAZ = new PrimarniIzraz();
  private static final CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();

  public PrimarniIzraz() {
    super(new NonTerminalSymbol("<primarni_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("IDN"))) {
      SNode child = node.getChildren().get(0);

      // 1
      while (scope != null) {
        if (scope.hasDeclared(child.getName())) {
          Type type = scope.getType(child.getName());

          if (!TypesHelper.isFunction(type)) {

            if (scope.getParentScope() == null) {
              String globalLabel = GeneratorKoda.getGlobalVariableLabel(child.getName());
              GeneratorKoda.writeln("\tMOVE " + globalLabel + ", R0");

            } else {
              GeneratorKoda.writeln("\tADD R6, %D " + scope.getOffset(child.getName()) + ", R0");
            }

            boolean isAlone = true;
            boolean isArgument = false;
            SNode parent = node.getParent();
            while (parent != null) {
              if (parent.getSymbol().getValue().equals("<lista_argumenata>")) {
                isArgument = true & isAlone;
                break;
              }
              if (parent.getChildren().size() > 1) {
                isAlone = false;
              }
              parent = parent.getParent();
            }
            if (isArgument && (TypesHelper.isLType(type) || TypesHelper.isArray(type))) {
              GeneratorKoda.writeln("\tLOAD R0, (R0)");
            } else if (!isArgument && !TypesHelper.isLType(type) && !TypesHelper.isArray(type)) {
              GeneratorKoda.writeln("\tLOAD R0, (R0)");
            }
            GeneratorKoda.writeln("\tPUSH R0");
          }
          node.setType(type);
          node.setlValue(TypesHelper.isLType(type));
          return; // all good
        }
        scope = scope.getParentScope();
      }

      throw new SemanticException(getErrorMessage(node));
    } else if (children.equals(Arrays.asList("BROJ"))) {
      SNode child = node.getChildren().get(0);

      // 1
      Long value = null;
      try {
        value = Long.decode(child.getValue());
      } catch (NumberFormatException e) {
        throw new SemanticException(getErrorMessage(node));
      }
      if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
        node.setType(Int.INT);
        node.setlValue(false);
        
        GeneratorKoda.constants.add(value);
        GeneratorKoda.writeln("\tLOAD R0, (" + GeneratorKoda.getConstantLabel(value) + ")");
        GeneratorKoda.writeln("\tPUSH R0");
        return; // all good
      }

      throw new SemanticException(getErrorMessage(node));
    } else if (children.equals(Arrays.asList("ZNAK"))) {
      SNode child = node.getChildren().get(0);

      // 1
      String value = child.getValue();
      value = value.substring(1, value.length() - 1); // skip ''
      if (invalidCharacter(value)) {
        throw new SemanticException(getErrorMessage(node));

      }
      int znak = charToInt(value);
      GeneratorKoda.writeln("\tMOVE %D " + znak + ",R0");
      GeneratorKoda.writeln("\tPUSH R0");
      node.setType(Char.CHAR);
      node.setlValue(false);
    } else if (children.equals(Arrays.asList("NIZ_ZNAKOVA"))) {
      SNode child = node.getChildren().get(0);

      // 1
      String value = child.getValue();
      value = value.substring(1, value.length() - 1); // skip ""
      value = value + "!"; // helper symbol if \ is last
      for (int i = 0; i < value.length() - 1; i++) {
        if (value.charAt(i) == '\\') {
          if (invalidCharacter(value.substring(i, i + 2))) {
            throw new SemanticException(getErrorMessage(node));
          } else {
            i++;
          }
        } else if (invalidCharacter(value.substring(i, i + 1))) {
          throw new SemanticException(getErrorMessage(node));
        } else if (value.charAt(i) == '\"') {
          throw new SemanticException(getErrorMessage(node));
        }
      }

      node.setType(new Array(ConstChar.CONST_CHAR));
      node.setlValue(false);
    } else if (children.equals(Arrays.asList("L_ZAGRADA", "<izraz>", "D_ZAGRADA"))) {
      SNode child = node.getChildren().get(1); // <izraz>

      // 1
      child.visit(scope);

      node.setType(child.getType());
      node.setlValue(child.islValue());
    }
  }

  /**
   * Returns <code>true</code> if given character is invalid.
   * 
   * @param character character to analyze
   * @return <code>true</code> if given character is invalid, <code>false</code> otherwise.
   */

  private int charToInt(String value) {
    int znak = 0;
    if (value.length() == 1) {
      znak = (int) value.charAt(0);

    } else {

      if (value.charAt(2) == 't') {
        znak = '\t';
      } else if (value.charAt(2) == 'n') {
        znak = '\n';
      } else if (value.charAt(2) == '0') {
        znak = '\0';
      } else if (value.charAt(2) == '\\') {
        znak = '\\';
      } else if (value.charAt(2) == '\'') {
        znak = '\'';
      } else if (value.charAt(2) == '\"') {
        znak = '\"';
      }
    }
    return znak;
  }

  private boolean invalidCharacter(String character) {
    if (character.length() == 2 && character.charAt(0) == '\\') {
      if (!Arrays.asList('t', 'n', '0', '\\', '\'', '\"').contains(character.charAt(1))) {
        return true;
      }
      return false;
    } else if (character.length() == 1 && character.charAt(0) != '\\') {
      return !asciiEncoder.canEncode(character);
    } else {
      return true;
    }
  }
}
