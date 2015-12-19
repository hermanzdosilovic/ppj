package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.ConstChar;
import hr.fer.zemris.ppj.lab3.types.ConstInt;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.Void;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class ImeTipa extends Rule {
  public static ImeTipa IME_TIPA = new ImeTipa();

  private ImeTipa() {
    super(new NonTerminalSymbol("<ime_tipa>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<specifikator_tipa>"))) {
      SNode specifikator_tipa = node.getChildren().get(0);
      specifikator_tipa.visit(scope);
      node.setType(specifikator_tipa.getType());
    } else if (children.equals(Arrays.asList("KR_CONST", "<specifikator_tipa>"))) {
      SNode specifikator_tipa = node.getChildren().get(1);
      specifikator_tipa.visit(scope);
      if (specifikator_tipa.getType() instanceof Void) {
        throw new SemanticException(getErrorMessage(node));
      }
      node.setType(
          (specifikator_tipa.getType() instanceof Int) ? ConstInt.CONST_INT : ConstChar.CONST_CHAR);
    }
  }
}
