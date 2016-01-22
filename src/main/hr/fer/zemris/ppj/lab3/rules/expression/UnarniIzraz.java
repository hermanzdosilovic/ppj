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
public class UnarniIzraz extends Rule {
  public static UnarniIzraz UNARNI_IZRAZ = new UnarniIzraz();

  private UnarniIzraz() {
    super(new NonTerminalSymbol("<unarni_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<postfiks_izraz>"))) {
      SNode postfiks_izraz = node.getChildren().get(0);
      postfiks_izraz.visit(scope);
      node.setType(postfiks_izraz.getType());
      node.setlValue(postfiks_izraz.islValue());
    } else if (children.equals(Arrays.asList("OP_INC", "<unarni_izraz>"))
        || children.equals(Arrays.asList("OP_DEC", "<unarni_izraz>"))) {
      SNode unarni_izraz = node.getChildren().get(1);
      unarni_izraz.visit(scope);
      if (!unarni_izraz.islValue()
          || !TypesHelper.canExplicitlyCast(unarni_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tLOAD R1, (R0)");

      if (children.contains("OP_INC")) {
        GeneratorKoda.writeln("\tADD R1, 1, R1");
      } else {
        GeneratorKoda.writeln("\tSUB R1, 1, R1");
      }

      GeneratorKoda.writeln("\tSTORE R1, (R0)");
      GeneratorKoda.writeln("\tPUSH R1");

      node.setType(Int.INT);
      node.setlValue(false);
    } else if (children.equals(Arrays.asList("<unarni_operator>", "<cast_izraz>"))) {
      SNode cast_izraz = node.getChildren().get(1);
      cast_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(cast_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }


      List<String> unarni_operator = node.getChildren().get(0).getValuesOfChildren();

      GeneratorKoda.writeln("\tPOP R0");
      if (cast_izraz.islValue()) {
        GeneratorKoda.writeln("\tLOAD R0, (R0)");
      }
      String labela1 = GeneratorKoda.getNextLabel();
      String labela2 = GeneratorKoda.getNextLabel();
      
      if (unarni_operator.contains("OP_NEG")) {
        GeneratorKoda.writeln("\tCMP R0, 0");
        GeneratorKoda.writeln("\tJP_NE " + labela1);
        GeneratorKoda.writeln("\tMOVE 1, R0");
        GeneratorKoda.writeln("\tJP " + labela2);
        GeneratorKoda.writeln(labela1 + "\tMOVE 0, R0");

      } else if (unarni_operator.contains("OP_TILDA")) {
        GeneratorKoda.writeln("\tXOR R0, -1, R0");

      } else if (unarni_operator.contains("OP_MINUS")) {
        GeneratorKoda.writeln("\tMOVE 0, R1");
        GeneratorKoda.writeln("\tSUB R1, R0, R0");

      }

      GeneratorKoda.writeln(labela2 + "\tPUSH R0");

      node.setType(Int.INT);
      node.setlValue(false);
    }
  }
}
