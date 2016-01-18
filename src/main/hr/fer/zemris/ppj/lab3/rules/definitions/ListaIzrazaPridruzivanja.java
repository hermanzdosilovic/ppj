package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class ListaIzrazaPridruzivanja extends Rule {
  public static ListaIzrazaPridruzivanja LISTA_IZRAZA_PRIDRUZIVANJA =
      new ListaIzrazaPridruzivanja();

  private ListaIzrazaPridruzivanja() {
    super(new NonTerminalSymbol("<lista_izraza_pridruzivanja>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<izraz_pridruzivanja>"))) {
      SNode izraz_pridruzivanja = node.getChildren().get(0);

      // 1
      izraz_pridruzivanja.visit(scope);

      node.setTypes(Arrays.asList(izraz_pridruzivanja.getType()));
      node.setElemCount(1);
    } else if (children.equals(Arrays.asList("<lista_izraza_pridruzivanja>", "ZAREZ",
        "<izraz_pridruzivanja>"))) {
      SNode lista_izraza_pridruzivanja = node.getChildren().get(0);
      SNode izraz_pridruzivanja = node.getChildren().get(2);

      // 1
      lista_izraza_pridruzivanja.visit(scope);

      // 2
      izraz_pridruzivanja.visit(scope);

      List<Type> novaLista = lista_izraza_pridruzivanja.getTypes();
      novaLista.add(izraz_pridruzivanja.getType());
      node.setTypes(novaLista);

      node.setElemCount(lista_izraza_pridruzivanja.getElemCount() + 1);
    }
  }
}
