package hr.fer.zemris.ppj.lab3.rules;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public abstract class Rule {
  private Symbol symbol;

  private List<List<Symbol>> productions;

  public Rule(Symbol symbol) {
    this.symbol = symbol;
    productions = new ArrayList<>();
  }

  public abstract void visit(SNode node) throws Exception;

  public Symbol getSymbol() {
    return symbol;
  }

  public List<List<Symbol>> getProductions() {
    return productions;
  }

  public void setProductions(List<List<Symbol>> productions) {
    this.productions = productions;
  }
}
