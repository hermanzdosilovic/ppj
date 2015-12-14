package hr.fer.zemris.ppj.lab3.rules.structure;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;

public class VanjskaDeklaracija extends Rule {
  public static VanjskaDeklaracija vanjskaDeklaracija = new VanjskaDeklaracija();

  private VanjskaDeklaracija() {
    super(new NonTerminalSymbol("<vanjska_deklaracija>"));
    List<List<Symbol>> productions = new ArrayList<>();
    // ...

    setProductions(productions);
  }

  @Override
  public void visit(SNode node) {}
}
