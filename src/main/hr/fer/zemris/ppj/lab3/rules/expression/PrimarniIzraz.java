package hr.fer.zemris.ppj.lab3.rules.expression;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Array;
import hr.fer.zemris.ppj.lab3.types.Char;
import hr.fer.zemris.ppj.lab3.types.ConstChar;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.List;

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
          node.setType(scope.getType(child.getName()));
          node.setlValue(TypesHelper.isLType(scope.getType(child.getName())));
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
        System.out.println("\tLOAD R0, " + value);
        System.out.println("\tPUSH R0");
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
