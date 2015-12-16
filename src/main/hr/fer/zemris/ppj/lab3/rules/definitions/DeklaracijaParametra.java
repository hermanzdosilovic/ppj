package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Void;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class DeklaracijaParametra extends Rule {
  public static DeklaracijaParametra DEKLARACIJA_PARAMETRA = new DeklaracijaParametra();

  private DeklaracijaParametra() {
    super(new NonTerminalSymbol("<deklaracija_parametra>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<ime_tipa>", "IDN"))) {
      SNode ime_tipa = node.getChildren().get(0);
      node.setType(ime_tipa.getType());
      node.setName(node.getChildren().get(1).getName());

      // 1
      ime_tipa.visit(new Scope(scope));

      // 2
      if (ime_tipa.getType().equals(Void.VOID)) {
        throw new SemanticException(getErrorMessage(node));
      }
    } else if (children
        .equals(Arrays.asList("<ime_tipa>", "IDN", "L_UGL_ZAGRADA", "D_UGL_ZAGRADA"))) {
      SNode ime_tipa = node.getChildren().get(0);
      node.setTypes(ime_tipa.getTypes());
      node.setName(node.getChildren().get(1).getName());

      // 1
      ime_tipa.visit(new Scope(scope));

      // 2
      if (ime_tipa.getType().equals(Void.VOID)) {
        throw new SemanticException(getErrorMessage(node));
      }
    }
  }
}
