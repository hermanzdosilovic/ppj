package hr.fer.zemris.ppj.lab3.rules.definitions;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class Deklaracija extends Rule {
  public static Deklaracija DEKLARACIJA = new Deklaracija();

  private Deklaracija() {
    super(new NonTerminalSymbol("<deklaracija>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<ime_tipa>", "<lista_init_deklaratora>", "TOCKAZAREZ"))) {
      SNode ime_tipa = node.getChildren().get(0);
      // 1
      ime_tipa.visit(scope);

      // 2
      SNode lista_init_deklaratora = node.getChildren().get(1);
      lista_init_deklaratora.setnType(ime_tipa.getType());
      lista_init_deklaratora.visit(scope);
    }
  }

}
