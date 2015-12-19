package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class ListaDeklaracija extends Rule {
  public static ListaDeklaracija LISTA_DEKLARACIJA = new ListaDeklaracija();

  private ListaDeklaracija() {
    super(new NonTerminalSymbol("<lista_deklaracija>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    for (SNode child : node.getChildren()) {
      child.visit(scope);
    }
  }
}
