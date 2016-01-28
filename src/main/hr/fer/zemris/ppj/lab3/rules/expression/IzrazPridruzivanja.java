package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.List;
import java.util.Arrays;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab4.GeneratorKoda;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class IzrazPridruzivanja extends Rule {
  public static IzrazPridruzivanja IZRAZ_PRIDRUZIVANJA = new IzrazPridruzivanja();

  private IzrazPridruzivanja() {
    super(new NonTerminalSymbol("<izraz_pridruzivanja>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<log_ili_izraz>"))) {
      SNode log_ili_izraz = node.getChildren().get(0);
      log_ili_izraz.visit(scope);
      node.setType(log_ili_izraz.getType());
      node.setlValue(log_ili_izraz.islValue());
    } else if (children.equals(Arrays.asList("<postfiks_izraz>", "OP_PRIDRUZI",
        "<izraz_pridruzivanja>"))) {
      SNode postfiks_izraz = node.getChildren().get(0);
      SNode izraz_pridruzivanja = node.getChildren().get(2);

      postfiks_izraz.visit(scope);
      if (!postfiks_izraz.islValue()) {
        throw new SemanticException(getErrorMessage(node));
      }
      izraz_pridruzivanja.visit(scope);
      if (!TypesHelper.canImplicitlyCast(izraz_pridruzivanja.getType(), postfiks_izraz.getType())) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.writeln("\tPOP R1");
      GeneratorKoda.writeln("\tPOP R0");
      
      if (izraz_pridruzivanja.islValue()) {
        GeneratorKoda.writeln("\tLOAD R1, (R1)");
      }
      GeneratorKoda.writeln("\tPUSH R1");
      
      node.setType(postfiks_izraz.getType());
      node.setlValue(false);
    }
  }
}
