package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class ListaDeklaracija extends Rule {
  public static ListaDeklaracija LISTA_DEKLARACIJA = new ListaDeklaracija();

  private ListaDeklaracija() {
    super(new NonTerminalSymbol("<lista_deklaracija>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<deklaracija>"))) {
      node.getChildren().get(0).visit(scope);
    } else if (children.equals(Arrays.asList("<lista_deklaracija>", "<deklaracija>"))) {
      node.getChildren().get(0).visit(scope);
      node.getChildren().get(1).visit(scope);
    }
  }
}
