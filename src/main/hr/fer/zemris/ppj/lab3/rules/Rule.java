package hr.fer.zemris.ppj.lab3.rules;

import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public abstract class Rule {
  private Symbol symbol;

  public Rule(Symbol symbol) {
    this.symbol = symbol;
  }

  public abstract void visit(SNode node, Scope scope) throws Exception;

  public Symbol getSymbol() {
    return symbol;
  }
}
