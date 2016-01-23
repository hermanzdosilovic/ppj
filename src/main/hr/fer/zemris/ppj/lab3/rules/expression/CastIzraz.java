package hr.fer.zemris.ppj.lab3.rules.expression;

import java.util.Arrays;
import java.util.List;

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
public class CastIzraz extends Rule {
  public static CastIzraz CAST_IZRAZ = new CastIzraz();

  private CastIzraz() {
    super(new NonTerminalSymbol("<cast_izraz>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    if (children.equals(Arrays.asList("<unarni_izraz>"))) {
      SNode unarni_izraz = node.getChildren().get(0);
      unarni_izraz.visit(scope);
      node.setType(unarni_izraz.getType());
      node.setlValue(unarni_izraz.islValue());
    } else if (children.equals(Arrays
        .asList("L_ZAGRADA", "<ime_tipa>", "D_ZAGRADA", "<cast_izraz>"))) {
      SNode ime_tipa = node.getChildren().get(1);
      SNode cast_izraz = node.getChildren().get(3);

      ime_tipa.visit(scope);
      cast_izraz.visit(scope);
      if (!TypesHelper.canExplicitlyCast(cast_izraz.getType(), ime_tipa.getType())) {
        throw new SemanticException(getErrorMessage(node));
      }

      if (cast_izraz.islValue()) {
        GeneratorKoda.writeln("\tPOP R0");
        GeneratorKoda.writeln("\tLOAD R0, (R0)");
        GeneratorKoda.writeln("\tPUSH R0");
      }

      node.setType(ime_tipa.getType());
      node.setlValue(false);
    }
  }
}
