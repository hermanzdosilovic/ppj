package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class ListaParametara extends Rule {
  public static ListaParametara LISTA_PARAMETARA = new ListaParametara();

  private ListaParametara() {
    super(new NonTerminalSymbol("<lista_parametara>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<deklaracija_parametara>"))) {
      SNode deklaracija_parametara = node.getChildren().get(0);
      node.setTypes(deklaracija_parametara.getTypes());
      node.setNames(deklaracija_parametara.getNames());

      // 1
      deklaracija_parametara.visit(new Scope(scope));
    } else if (children.equals(Arrays.asList("<lista_parametara>", "ZAREZ",
        "<deklaracija_parametra>"))) {
      SNode lista_parametara = node.getChildren().get(0);
      SNode deklaracija_parametra = node.getChildren().get(2);
      List<Type> novaLista = lista_parametara.getTypes();
      novaLista.add(lista_parametara.getType());
      node.setTypes(novaLista);

      List<String> imena = deklaracija_parametra.getNames();
      imena.add(deklaracija_parametra.getName());
      node.setNames(imena);

      // 1
      lista_parametara.visit(new Scope(scope));

      // 2
      deklaracija_parametra.visit(new Scope(scope));

      // 3
      if (lista_parametara.getNames().contains(deklaracija_parametra.getName())) {
        throw new SemanticException(getErrorMessage(node));
      }
    }
  }

}
