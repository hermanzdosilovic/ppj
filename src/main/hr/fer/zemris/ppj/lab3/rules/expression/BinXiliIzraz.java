package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab4.GeneratorKoda;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class BinXiliIzraz extends Rule {

  public static BinXiliIzraz BIN_XILI_IZRAZ = new BinXiliIzraz();

  private BinXiliIzraz() {
    super(new NonTerminalSymbol("<bin_xili_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<bin_i_izraz>"))) {
      SNode bin_i_izraz = node.getChildren().get(0);
      bin_i_izraz.visit(scope);
      node.setType(bin_i_izraz.getType());
      node.setlValue(bin_i_izraz.islValue());
    } else if (children.equals(Arrays.asList("<bin_xili_izraz>", "OP_BIN_XILI", "<bin_i_izraz>"))) {
      SNode bin_xili_izraz = node.getChildren().get(0);
      SNode bin_i_izraz = node.getChildren().get(2);

      bin_xili_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(bin_xili_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      bin_i_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(bin_i_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.writeln("\tPOP R1");
      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tXOR R0, R1, R0");
      GeneratorKoda.writeln("\tPUSH R0");

      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
