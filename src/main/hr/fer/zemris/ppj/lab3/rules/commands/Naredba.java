package hr.fer.zemris.ppj.lab3.rules.commands;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class Naredba extends Rule {

  public static Naredba NAREDBA = new Naredba();
  
  private Naredba() {
    super(new NonTerminalSymbol("<naredba>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    node.getChildren().get(0).visit(scope);
  }

}
