package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class ListaArgumenata extends Rule {
  public static ListaArgumenata LISTA_ARGUMENATA = new ListaArgumenata();

  private ListaArgumenata() {
    super(new NonTerminalSymbol("<lista_argumenata>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<izraz_pridruzivanja>"))) {
      SNode izraz_pridruzivanja = node.getChildren().get(0);
      izraz_pridruzivanja.visit(scope);
      node.setTypes(Arrays.asList(izraz_pridruzivanja.getType()));
    } else
      if (children.equals(Arrays.asList("<lista_argumenata>", "ZAREZ", "<izraz_pridruzivanja>"))) {
      SNode lista_argumenata = node.getChildren().get(0);
      SNode izraz_pridruzivanja = node.getChildren().get(2);

      lista_argumenata.visit(scope);
      izraz_pridruzivanja.visit(scope);

      List<Type> types = new ArrayList<>(lista_argumenata.getTypes());
      types.addAll(Arrays.asList(izraz_pridruzivanja.getType()));
      node.setTypes(types);
    }
  }

}
