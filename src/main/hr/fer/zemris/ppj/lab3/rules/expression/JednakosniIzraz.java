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
public class JednakosniIzraz extends Rule {
  public static JednakosniIzraz JEDNAKOSNI_IZRAZ = new JednakosniIzraz();

  private JednakosniIzraz() {
    super(new NonTerminalSymbol("<jednakosni_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<odnosni_izraz>"))) {
      SNode odnosni_izraz = node.getChildren().get(0);
      odnosni_izraz.visit(scope);
      node.setType(odnosni_izraz.getType());
      node.setlValue(odnosni_izraz.islValue());
    } else if (children.equals(Arrays.asList("<jednakosni_izraz>", "OP_EQ", "<odnosni_izraz>"))
        || children.equals(Arrays.asList("<jednakosni_izraz>", "OP_NEQ", "<odnosni_izraz>"))) {
      SNode jednakosni_izraz = node.getChildren().get(0);
      SNode odnosni_izraz = node.getChildren().get(2);
      jednakosni_izraz.visit(scope);

      if (!TypesHelper.canImplicitlyCast(jednakosni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      odnosni_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(odnosni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.writeln("\tPOP R1");
      if (odnosni_izraz.islValue()) {
        GeneratorKoda.writeln("\tLOAD R1, (R1)");
      }
      GeneratorKoda.writeln("\tPOP R0");
      if (jednakosni_izraz.islValue()) {
        GeneratorKoda.writeln("\tLOAD R0, (R0)");
      }

      GeneratorKoda.writeln("\tMOVE 1, R2");
      GeneratorKoda.writeln("\tCMP R0, R1");
      String labela = GeneratorKoda.getNextLabel();

      if (children.contains("OP_EQ")) {
        GeneratorKoda.writeln("\tJP_EQ " + labela);
      } else {
        GeneratorKoda.writeln("\tJP_NE " + labela);
      }

      GeneratorKoda.writeln("\tMOVE 0, R2");
      GeneratorKoda.writeln(labela + "\tPUSH R2");


      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
