package hr.fer.zemris.ppj.lab3.rules.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.ReturnType;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab3.types.Void;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class NaredbaSkoka extends Rule {

  public static NaredbaSkoka NAREDBA_SKOKA = new NaredbaSkoka();

  private NaredbaSkoka() {
    super(new NonTerminalSymbol("<naredba_skoka>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> childrenValues = node.getValuesOfChildren();
    if (childrenValues.equals(Arrays.asList("KR_CONTINUE", "TOCKAZAREZ"))
        || childrenValues.equals(Arrays.asList("KR_BREAK", "TOCKAZAREZ"))) {
      if (!isInsideOfLoop(node)) {
        throw new SemanticException(getErrorMessage(node));
      }
    } else if (childrenValues.equals(Arrays.asList("KR_RETURN", "TOCKAZAREZ"))) {
      ReturnType type = functionReturnType(node);
      if (type == null || type != Void.VOID) {
        throw new SemanticException(getErrorMessage(node));
      }
    } else if (childrenValues.equals(Arrays.asList("KR_RETURN", "<izraz>", "TOCKAZAREZ"))) {
      node.getChildren().get(1).visit(scope);
      ReturnType type = functionReturnType(node);
      if (type == null || !TypesHelper.canImplicitlyCast(node.getChildren().get(1).getType(), type)) {
        throw new SemanticException(getErrorMessage(node));
      }
    }
  }

  private ReturnType functionReturnType(SNode node) {
    while (node != null) {
      if (node.getSymbol().getValue().equals("<definicija_funkcije>")) {
        break;
      }
      node = node.getParent();
    }
    if (node == null) {
      return null;
    }
    return (ReturnType) node.getChildren().get(0).getType();
  }

  private boolean isInsideOfLoop(SNode node) {
    while (node != null) {
      if (node.getSymbol().getValue().equals("<naredba_petlje>")) {
        return true;
      }
    }
    return false;
  }

}
