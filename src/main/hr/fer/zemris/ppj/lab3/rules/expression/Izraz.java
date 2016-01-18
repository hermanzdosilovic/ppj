package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class Izraz extends Rule {
  public static Izraz IZRAZ = new Izraz();

  private Izraz() {
    super(new NonTerminalSymbol("<izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<izraz_pridruzivanja>"))) {
      SNode izraz_pridruzivanja = node.getChildren().get(0);
      izraz_pridruzivanja.visit(scope);
      node.setType(izraz_pridruzivanja.getType());
      node.setlValue(izraz_pridruzivanja.islValue());
    } else if (children.equals(Arrays.asList("<izraz>", "ZAREZ", "<izraz_pridruzivanja>"))) {
      SNode izraz = node.getChildren().get(0);
      SNode izraz_pridruzivanja = node.getChildren().get(2);

      izraz.visit(scope);
      izraz_pridruzivanja.visit(scope);

      node.setType(izraz_pridruzivanja.getType());
      node.setlValue(false);
    }
  }
}
