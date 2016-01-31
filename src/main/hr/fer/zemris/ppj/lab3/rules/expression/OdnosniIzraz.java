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
public class OdnosniIzraz extends Rule {
  public static OdnosniIzraz ODNOSNI_IZRAZ = new OdnosniIzraz();

  private OdnosniIzraz() {
    super(new NonTerminalSymbol("<odnosni_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<aditivni_izraz>"))) {
      SNode aditivni_izraz = node.getChildren().get(0);
      aditivni_izraz.visit(scope);
      node.setType(aditivni_izraz.getType());
      node.setlValue(aditivni_izraz.islValue());
    } else if (children.equals(Arrays.asList("<odnosni_izraz>", "OP_LT", "<aditivni_izraz>"))
        || children.equals(Arrays.asList("<odnosni_izraz>", "OP_GT", "<aditivni_izraz>"))
        || children.equals(Arrays.asList("<odnosni_izraz>", "OP_LTE", "<aditivni_izraz>"))
        || children.equals(Arrays.asList("<odnosni_izraz>", "OP_GTE", "<aditivni_izraz>"))) {
      SNode odnosni_izraz = node.getChildren().get(0);
      SNode aditivni_izraz = node.getChildren().get(2);
      odnosni_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(odnosni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      aditivni_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(aditivni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.writeln("\tPOP R1");
      if (aditivni_izraz.islValue()) {
        GeneratorKoda.writeln("\tLOAD R1, (R1)");
      }
      GeneratorKoda.writeln("\tPOP R0");
      if (odnosni_izraz.islValue()) {
        GeneratorKoda.writeln("\tLOAD R0, (R0)");
      }
        
      GeneratorKoda.writeln("\tMOVE 1, R2");
      GeneratorKoda.writeln("\tCMP R0, R1");
      String labela = GeneratorKoda.getNextLabel();

      if (children.contains("OP_LT")) {
        GeneratorKoda.writeln("\tJP_SLT " + labela);
      } else if (children.contains("OP_GT")) {
        GeneratorKoda.writeln("\tJP_SGT " + labela);
      } else if (children.contains("OP_LTE")) {
        GeneratorKoda.writeln("\tJP_SLE "+labela);
      } else if (children.contains("OP_GTE")) {
        GeneratorKoda.writeln("\tJP_SGE "+labela);
      }
      
      GeneratorKoda.writeln("\tMOVE 0, R2");
      GeneratorKoda.writeln(labela + "\tPUSH R2");

      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
