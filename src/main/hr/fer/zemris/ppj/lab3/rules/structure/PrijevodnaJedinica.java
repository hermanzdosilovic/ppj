package hr.fer.zemris.ppj.lab3.rules.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.rules.RuleFactory;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class PrijevodnaJedinica extends Rule {
  public static PrijevodnaJedinica prijevodnaJedinica = new PrijevodnaJedinica();

  private PrijevodnaJedinica() {
    super(new NonTerminalSymbol("<prijevodna_jedinica>"));
    List<List<Symbol>> productions = new ArrayList<>();
    productions.add(Arrays.asList(this.getSymbol()));
    productions
        .add(Arrays.asList(this.getSymbol(), VanjskaDeklaracija.vanjskaDeklaracija.getSymbol()));

    setProductions(productions);
  }

  @Override
  public void visit(SNode node) throws Exception {
    if (!getProductions().contains(node.getSymbolsOfChildren())) {
      throw new Exception("Invalid production!");
    }

    for (SNode child : node.getChildren()) {
      RuleFactory.getRule(child.getSymbol()).visit(child);
    }
  }
}
