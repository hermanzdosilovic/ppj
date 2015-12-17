package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.Arrays;
import java.util.List;

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
public class BinIIzraz extends Rule {
  public static BinIIzraz BIN_I_IZRAZ = new BinIIzraz();

  private BinIIzraz() {
    super(new NonTerminalSymbol("<bin_i_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<jednakosni_izraz>"))) {
      SNode jednakosni_izraz = node.getChildren().get(0);
      jednakosni_izraz.visit(scope);
      node.setType(jednakosni_izraz.getType());
      node.setlValue(jednakosni_izraz.islValue());
    } else if (children.equals(Arrays.asList("<bin_i_izraz>", "OP_BIN_I", "<jednakosni_izraz>"))) {
      SNode bin_i_izraz = node.getChildren().get(0);
      SNode jednakosni_izraz = node.getChildren().get(2);
      bin_i_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(bin_i_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      jednakosni_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(jednakosni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      
      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
