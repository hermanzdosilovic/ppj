package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.NonVoidFunctionType;
import hr.fer.zemris.ppj.lab3.types.ReturnType;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab3.types.VoidFunctionType;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;


/**
 * @author Ivan Trubic
 */
public class DefinicijaFunkcije extends Rule {
  public static DefinicijaFunkcije DEFINICIJA_FUNKCIJE = new DefinicijaFunkcije();

  private DefinicijaFunkcije() {
    super(new NonTerminalSymbol("<definicija_funkcije>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    
//    System.out.print("F_" + node.getChildren().get(1).getName());
    if (children.equals(Arrays.asList("<ime_tipa>", "IDN", "L_ZAGRADA", "KR_VOID", "D_ZAGRADA",
        "<slozena_naredba>"))) {
      SNode ime_tipa = node.getChildren().get(0);
      // 1
      ime_tipa.visit(scope);

      // 2
      if (TypesHelper.isConstT(ime_tipa.getType())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      SNode idn = node.getChildren().get(1);
      if (scope.hasDefined(idn.getName())) { // this is already in global scope, checked by SA
        throw new SemanticException(getErrorMessage(node));
      }

      // 4
      VoidFunctionType functionType = new VoidFunctionType((ReturnType) ime_tipa.getType());
      // this is already in global scope, checked by SA
      if (scope.hasDeclared(idn.getName()) && !scope.getType(idn.getName()).equals(functionType)) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 5
      scope.insert(idn.getName(), functionType, true);
      
      // 6
      Scope scopeSlozenaNaredba = new Scope(scope);
      //scopeSlozenaNaredba.insert(idn.getName(), functionType, true);
      node.getChildren().get(5).visit(scopeSlozenaNaredba);

    } else if (children.equals(Arrays.asList("<ime_tipa>", "IDN", "L_ZAGRADA", "<lista_parametara>",
        "D_ZAGRADA", "<slozena_naredba>"))) {
      SNode ime_tipa = node.getChildren().get(0);
      // 1
      ime_tipa.visit(scope);

      // 2
      if (TypesHelper.isConstT(ime_tipa.getType())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      SNode idn = node.getChildren().get(1);
      if (scope.hasDefined(idn.getName())) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 4
      SNode lista_parametara = node.getChildren().get(3);
      lista_parametara.visit(scope);

      // 5
      NonVoidFunctionType functionType =
          new NonVoidFunctionType(lista_parametara.getTypes(), (ReturnType) ime_tipa.getType());
      if (scope.hasDeclared(idn.getName()) && !scope.getType(idn.getName()).equals(functionType)) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 6
      scope.insert(idn.getName(), functionType, true);

      // 7
      Scope scopeSlozenaNaredba = new Scope(scope);
      //scopeSlozenaNaredba.insert(idn.getName(), functionType, true);
      for (int i = 0; i < lista_parametara.getTypes().size(); i++) {
        scopeSlozenaNaredba.insert(lista_parametara.getNames().get(i),
            lista_parametara.getTypes().get(i), false);
      }
      node.getChildren().get(5).visit(scopeSlozenaNaredba);
    }
  }
}
