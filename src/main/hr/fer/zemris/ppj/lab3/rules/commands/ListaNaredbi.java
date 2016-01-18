package hr.fer.zemris.ppj.lab3.rules.commands;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class ListaNaredbi extends Rule {

  public static ListaNaredbi LISTA_NAREDBI = new ListaNaredbi();

  private ListaNaredbi() {
    super(new NonTerminalSymbol("<lista_naredbi>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    for (SNode child : node.getChildren()) {
      child.visit(scope);
    }
  }

}
