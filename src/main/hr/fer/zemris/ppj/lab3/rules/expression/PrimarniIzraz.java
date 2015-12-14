package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.Arrays;

import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Array;
import hr.fer.zemris.ppj.lab3.types.Char;
import hr.fer.zemris.ppj.lab3.types.ConstChar;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class PrimarniIzraz extends Rule {

  public static PrimarniIzraz primarniIzraz = new PrimarniIzraz();

  public PrimarniIzraz() {
    super(new NonTerminalSymbol("<primarni_izraz>"));
  }

  @Override
  public void visit(SNode node, Scope scope) throws Exception {
    if (node.getValuesOfChildren().equals(Arrays.asList("IDN"))) {
      SNode child = node.getChildren().get(0);

      node.setType(child.getType());
      node.setlValue(child.islValue());

      while (scope != null) {
        if (scope.checkName(child.getName())) {
          return;
        }
        scope = scope.getParentScope();
      }

      throw new Exception("Name not declared.");
    } else if (node.getValuesOfChildren().equals(Arrays.asList("BROJ"))) {
      SNode child = node.getChildren().get(0);

      node.setType(Int.INT);
      node.setlValue(false);

      Long value = Long.parseLong(child.getValue());
      if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
        return;
      }

      throw new Exception("Invalid value.");
    } else if (node.getValuesOfChildren().equals(Arrays.asList("ZNAK"))) {
      SNode child = node.getChildren().get(0);

      node.setType(Char.CHAR);
      node.setlValue(false);

      String value = child.getValue();
      if (value.length() == 2) {
        if (value.charAt(0) != '\\') {
          throw new Exception("Invalid escaping");
        }
        if (!Arrays.asList('t', 'n', '0', '\\', '"', '\'').contains(value.charAt(1))) {
          throw new Exception("Invalid escaping");
        }
      } else {
        Character character = new Character(value.charAt(0));
        if (character >= Character.MIN_VALUE && character <= Character.MAX_VALUE) {
          return;
        } else {
          throw new Exception("Invalid character");
        }
      }

      return;
    } else if (node.getValuesOfChildren().equals(Arrays.asList("NIZ_ZNAKOVA"))) {
      SNode child = node.getChildren().get(0);

      node.setType(new Array(ConstChar.CONST_CHAR));
      node.setlValue(false);

      // ...
    }
  }

}
