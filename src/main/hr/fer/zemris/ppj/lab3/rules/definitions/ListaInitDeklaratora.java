package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class ListaInitDeklaratora extends Rule {
  public static ListaInitDeklaratora LISTA_INIT_DEKLARATORA = new ListaInitDeklaratora();

  private ListaInitDeklaratora() {
    super(new NonTerminalSymbol("<lista_init_deklaratora>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<init_deklarator>"))) {
      SNode init_deklarator = node.getChildren().get(0);
      init_deklarator.setnType(node.getnType());
      init_deklarator.visit(scope);
    } else if (children.equals(Arrays.asList("<lista_init_deklaratora>", "ZAREZ",
        "<init_deklarator>"))) {
      SNode lista_init_deklaratora = node.getChildren().get(0);
      lista_init_deklaratora.setnType(node.getnType());
      lista_init_deklaratora.visit(scope);

      SNode init_deklarator = node.getChildren().get(0);
      init_deklarator.setnType(node.getnType());
      init_deklarator.visit(scope);
    }
  }
}
