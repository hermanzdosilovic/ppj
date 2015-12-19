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
public class BinIliIzraz extends Rule {
  public static BinIliIzraz BIN_ILI_IZRAZ = new BinIliIzraz();
  
  private BinIliIzraz() {
    super(new NonTerminalSymbol("<bin_ili_izraz>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<bin_xili_izraz>"))) {
      SNode bin_xili_izraz = node.getChildren().get(0);
      bin_xili_izraz.visit(scope);
      node.setType(bin_xili_izraz.getType());
      node.setlValue(bin_xili_izraz.islValue());
    } else if (children.equals(Arrays.asList("<bin_ili_izraz>", "OP_BIN_ILI", "<bin_xili_izraz>"))) {
      SNode bin_ili_izraz = node.getChildren().get(0);
      SNode bin_xili_izraz = node.getChildren().get(2);
      
      bin_ili_izraz.visit(scope);
      if(!TypesHelper.canImplicitlyCast(bin_ili_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      bin_xili_izraz.visit(scope);
      if(!TypesHelper.canImplicitlyCast(bin_xili_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      
      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
