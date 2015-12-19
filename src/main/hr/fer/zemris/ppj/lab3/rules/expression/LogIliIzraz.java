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
public class LogIliIzraz extends Rule {
  public static LogIliIzraz LOG_ILI_IZRAZ = new LogIliIzraz();
  
  private LogIliIzraz() {
    super(new NonTerminalSymbol("<log_ili_izraz>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<log_i_izraz>"))) {
     SNode log_i_izraz = node.getChildren().get(0);
     log_i_izraz.visit(scope);
     node.setType(log_i_izraz.getType());
     node.setlValue(log_i_izraz.islValue());
    } else if (children.equals(Arrays.asList("<log_ili_izraz>", "OP_ILI", "<log_i_izraz>"))){
      SNode log_ili_izraz = node.getChildren().get(0);
      SNode log_i_izraz = node.getChildren().get(2);
      
      log_ili_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(log_ili_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      log_i_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(log_i_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      
      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
