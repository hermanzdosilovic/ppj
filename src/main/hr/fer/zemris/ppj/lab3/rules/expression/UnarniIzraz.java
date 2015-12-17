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
      node.setType(Int.INT);
      node.setlValue(false);
    } else if (children.equals(Arrays.asList("<unarni_operator>", "<cast_izraz>"))) {
      SNode cast_izraz = node.getChildren().get(1);
      cast_izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(cast_izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      node.setType(Int.INT);
      node.setlValue(false);
    }
  }

}
