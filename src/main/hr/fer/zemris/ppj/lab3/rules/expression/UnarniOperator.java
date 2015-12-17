package hr.fer.zemris.ppj.lab3.rules.expression;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class UnarniOperator extends Rule {
  public static UnarniOperator UNARNI_OPERATOR = new UnarniOperator();
  
  private UnarniOperator() {
    super(new NonTerminalSymbol("<unarni operator>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    return;
  }
}
