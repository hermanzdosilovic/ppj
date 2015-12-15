package hr.fer.zemris.ppj.lab3.rules.structure;

import java.util.Arrays;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.rules.RuleFactory;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class PrijevodnaJedinica extends Rule {
  public static PrijevodnaJedinica PRIJEVODNA_JEDINICA = new PrijevodnaJedinica();

  private PrijevodnaJedinica() {
    super(new NonTerminalSymbol("<prijevodna_jedinica>"));
  }

  @Override
  public void visit(SNode node, Scope scope) throws SemanticException {
    if (node.getValuesOfChildren().equals(Arrays.asList("<vanjska_deklaracija>"))) {
    } else if (node.getValuesOfChildren()
        .equals(Arrays.asList("<prijevodna_jedinica>", "<vanjska_deklaracija>"))) {
    } else {
      throw new SemanticException("Invalid production.");
    }

    for (SNode child : node.getChildren()) {
      RuleFactory.getRule(child.getSymbol()).visit(child, new Scope(scope));
    }
  }
}
