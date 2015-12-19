package hr.fer.zemris.ppj.lab3.rules.expression;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Char;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.Void;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class SpecifikatorTipa extends Rule {
  public static SpecifikatorTipa SPECIFIKATOR_TIPA = new SpecifikatorTipa();

  private SpecifikatorTipa() {
    super(new NonTerminalSymbol("<specifikator_tipa>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    String child = node.getValuesOfChildren().get(0);
    if (child.equals("KR_VOID")) {
      node.setType(Void.VOID);
    } else if (child.equals("KR_CHAR")) {
      node.setType(Char.CHAR);
    } else {
      node.setType(Int.INT);
    }
  }
}
