package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.List;
import java.util.Arrays;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class MultiplikativniIzraz extends Rule {
  public static MultiplikativniIzraz MULTIPLIKATIVNI_IZRAZ = new MultiplikativniIzraz();

  private MultiplikativniIzraz() {
    super(new NonTerminalSymbol("<multiplikativni_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<cast_izraz>"))) {
      SNode cast_izraz = node.getChildren().get(0);
      cast_izraz.visit(scope);
      node.setType(cast_izraz.getType());
      node.setlValue(cast_izraz.islValue());
    } else if (children.equals(Arrays.asList("<multiplikativni_izraz>", "OP_PUTA", "<cast_izraz>"))
        || children.equals(Arrays.asList("<multiplikativni_izraz>", "OP_DIJELI", "<cast_izraz>"))
        || children.equals(Arrays.asList("<multiplikativni_izraz>", "OP_MOD", "<cast_izraz>"))) {
      SNode multiplikativni_izraz = node.getChildren().get(0);
      SNode cast_izraz = node.getChildren().get(2);

      multiplikativni_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(multiplikativni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      cast_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(cast_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
