package hr.fer.zemris.ppj.lab3.rules.structure;

import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class VanjskaDeklaracija extends Rule {
  public static VanjskaDeklaracija vanjskaDeklaracija = new VanjskaDeklaracija();

  private VanjskaDeklaracija() {
    super(new NonTerminalSymbol("<vanjska_deklaracija>"));
  }

  @Override
  public void visit(SNode node, Scope scope) {
    // ...
  }
}
