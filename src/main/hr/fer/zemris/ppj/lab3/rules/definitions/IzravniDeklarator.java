package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Array;
import hr.fer.zemris.ppj.lab3.types.NumericType;
import hr.fer.zemris.ppj.lab3.types.ReturnType;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
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
      if (TypesHelper.isVoidFunction(node.getnType())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 2
      if (scope.hasDeclared(idn.getName())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      scope.insert(idn.getName(), idn.getType(), false);
      
      node.setType(node.getnType());
    } else if (children.equals(Arrays.asList("IDN", "L_UGL_ZAGRADA", "BROJ", "D_UGL_ZAGRADA"))) {

      // 1
      if (TypesHelper.isVoidFunction(node.getnType())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 2
      SNode idn = node.getChildren().get(0);
      if (scope.hasDeclared(idn.getName())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      if (Integer.parseInt(node.getChildren().get(2).getValue()) > 1024
          || Integer.parseInt(node.getChildren().get(2).getValue()) < 0) {
        throw new SemanticException(getErrorMessage(node));
      }
      
     // 4 
      scope.insert(idn.getName(), idn.getType(), false);
      
      node.setType(new Array((NumericType) node.getnType()));
    }
    else if(children.equals(Arrays.asList("IDN", "L_ZAGRADA", "KR_VOID", "D_ZAGRADA"))){
      
      // 1
      SNode idn = node.getChildren().get(0);
      if(scope.hasDeclared(idn.getName())){
        Scope globalScope = scope.getGlobalScope();
        globalScope.insert(idn.getName(), new VoidFunctionType((ReturnType) node.getnType()), false);
      }
      else{
        scope.insert(idn.getName(), new VoidFunctionType((ReturnType) node.getnType()), false);
      }
      node.setType(new VoidFunctionType((ReturnType) node.getnType()));
    }
    else if(children.equals(Arrays.asList("IDN", "L_ZAGRADA", "<lista_parametara>", "D_ZAGRADA"))){
      SNode lista_parametara = node.getChildren().get(2); 
      
      // 1
      lista_parametara.visit(scope);
      
      // 2
      SNode idn = node.getChildren().get(0);
      if(scope.hasDeclared(idn.getName())){
        Scope globalScope = scope.getGlobalScope();
        globalScope.insert(idn.getName(), new VoidFunctionType((ReturnType) node.getnType()), false);
      }
      else{
        scope.insert(idn.getName(), new VoidFunctionType((ReturnType) node.getnType()), false);
      }
    }
  }

}
