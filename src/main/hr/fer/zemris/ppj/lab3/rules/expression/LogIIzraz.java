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
public class LogIIzraz extends Rule {
  public static LogIIzraz LOG_I_IZRAZ = new LogIIzraz();
  
  private LogIIzraz() {
    super(new NonTerminalSymbol("<log_i_izraz>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<bin_ili_izraz>"))) {
      SNode bin_ili_izraz = node.getChildren().get(0);
      bin_ili_izraz.visit(scope);
      node.setType(bin_ili_izraz.getType());
      node.setlValue(bin_ili_izraz.islValue());
    } else if (children.equals(Arrays.asList("<log_i_izraz>", "OP_I", "<bin_ili_izraz>"))) {
      SNode log_i_izraz = node.getChildren().get(0);
      SNode bin_ili_izraz = node.getChildren().get(2);
      
      log_i_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(log_i_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      bin_ili_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(bin_ili_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      
      GeneratorKoda.writeln("\tPOP R1");
      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tAND R0, R1, R0");
      GeneratorKoda.writeln("\tCMP R0, 0");
      String labela = GeneratorKoda.getNextLabel();
      GeneratorKoda.writeln("\tJP_EQ " + labela);
      GeneratorKoda.writeln("\tMOVE 1, R0");
      GeneratorKoda.writeln(labela + "\tPUSH R0");
      
      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
