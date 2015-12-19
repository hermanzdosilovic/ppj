package hr.fer.zemris.ppj.lab3.rules.structure;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Ivan Trubic
 */
public class VanjskaDeklaracija extends Rule {
  public static VanjskaDeklaracija VANJSKA_DEKLARACIJA = new VanjskaDeklaracija();

  private VanjskaDeklaracija() {
    super(new NonTerminalSymbol("<vanjska_deklaracija>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    for (SNode child : node.getChildren()) {
      child.visit(scope);
    }
  }
}
