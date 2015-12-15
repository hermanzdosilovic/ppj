package hr.fer.zemris.ppj.lab3.rules.structure;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Ivan Trubic
 */
public class PrijevodnaJedinica extends Rule {
  public static PrijevodnaJedinica PRIJEVODNA_JEDINICA = new PrijevodnaJedinica();

  private PrijevodnaJedinica() {
    super(new NonTerminalSymbol("<prijevodna_jedinica>"));
  }

  @Override
  public void visit(SNode node, Scope scope) throws SemanticException {
    for (SNode child : node.getChildren()) {
      child.visit(new Scope(scope));
    }
  }
}
