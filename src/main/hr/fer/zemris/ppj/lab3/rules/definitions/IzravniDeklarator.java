package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Array;
import hr.fer.zemris.ppj.lab3.types.NonVoidFunctionType;
import hr.fer.zemris.ppj.lab3.types.NumericType;
import hr.fer.zemris.ppj.lab3.types.ReturnType;
import hr.fer.zemris.ppj.lab3.types.Void;
import hr.fer.zemris.ppj.lab3.types.VoidFunctionType;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class IzravniDeklarator extends Rule {
  public static IzravniDeklarator IZRAVNI_DEKLARATOR = new IzravniDeklarator();

  private IzravniDeklarator() {
    super(new NonTerminalSymbol("<izravni_deklarator>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("IDN"))) {
      SNode idn = node.getChildren().get(0);

      // 1
      if (node.getnType().equals(Void.VOID)) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 2
      if (scope.hasDeclared(idn.getName())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      scope.insert(idn.getName(), idn.getType(), true);

      node.setType(node.getnType());
    } else if (children.equals(Arrays.asList("IDN", "L_UGL_ZAGRADA", "BROJ", "D_UGL_ZAGRADA"))) {

      // 1
      if (node.getnType().equals(Void.VOID)) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 2
      SNode idn = node.getChildren().get(0);
      if (scope.hasDeclared(idn.getName())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      int brojValue = (int) Integer.parseInt(node.getChildren().get(2).getValue());
      if (brojValue > 1024 || brojValue <= 0) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 4
      scope.insert(idn.getName(), idn.getType(), true);

      node.setType(new Array((NumericType) node.getnType()));
      node.setElemCount(brojValue);
    } else if (children.equals(Arrays.asList("IDN", "L_ZAGRADA", "KR_VOID", "D_ZAGRADA"))) {

      // 1
      SNode idn = node.getChildren().get(0);
      VoidFunctionType functionType = new VoidFunctionType((ReturnType) node.getnType());

      if (scope.hasDeclared(idn.getName())) {
        if (!scope.getType(idn.getName()).equals(functionType)) {
          throw new SemanticException(getErrorMessage(node));
        }
      } else {
        scope.insert(idn.getName(), functionType, false);
      }
      node.setType(functionType);
    } else if (children
        .equals(Arrays.asList("IDN", "L_ZAGRADA", "<lista_parametara>", "D_ZAGRADA"))) {
      SNode lista_parametara = node.getChildren().get(2);

      // 1
      lista_parametara.visit(scope);

      // 2
      SNode idn = node.getChildren().get(0);
      NonVoidFunctionType functionType =
          new NonVoidFunctionType(lista_parametara.getTypes(), (ReturnType) node.getnType());

      if (scope.hasDeclared(idn.getName())) {
        if(!scope.getType(idn.getName()).equals(functionType)){
          throw new SemanticException(getErrorMessage(node));
        }
      } else {
        scope.insert(idn.getName(), functionType, false);
      }
    }
  }
}
