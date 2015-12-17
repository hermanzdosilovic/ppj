package hr.fer.zemris.ppj.lab3.rules.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class NaredbaPetlje extends Rule {

  public static NaredbaPetlje NAREDBA_PETLJE = new NaredbaPetlje();

  private NaredbaPetlje() {
    super(new NonTerminalSymbol("<naredba_petlje>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> childrenValues = node.getValuesOfChildren();
    List<SNode> children = node.getChildren();
    if (childrenValues
        .equals(Arrays.asList("KR_WHILE", "L_ZAGRADA", "<izraz>", "D_ZAGRADA", "<naredba>"))) {
      children.get(2).visit(scope);
      if (!TypesHelper.canImplicitlyCast(children.get(2).getnType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      children.get(4).visit(scope);
    } else if (childrenValues.equals(Arrays.asList("KR_FOR", "L_ZAGRADA", "<izraz_naredba>",
        "<izraz_naredba>", "D_ZAGRADA", "<naredba>"))) {
      children.get(2).visit(scope);
      children.get(3).visit(scope);
      if (!TypesHelper.canImplicitlyCast(children.get(3).getnType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      children.get(5).visit(scope);
    } else if (childrenValues.equals(Arrays.asList("KR_FOR", "L_ZAGRADA", "<izraz_naredba>",
        "<izraz_naredba>", "<izraz>", "D_ZAGRADA", "<naredba>"))) {
      children.get(2).visit(scope);
      children.get(3).visit(scope);
      if (!TypesHelper.canImplicitlyCast(children.get(3).getnType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      children.get(4).visit(scope);
      children.get(6).visit(scope);
    }
  }

}
