package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.ConstType;
import hr.fer.zemris.ppj.lab3.types.NonVoidFunctionType;
import hr.fer.zemris.ppj.lab3.types.NumericType;
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

    if (children.equals(Arrays.asList("<ime_tipa>", "IDN", "L_ZAGRADA", "KR_VOID", "D_ZAGRADA",
        "<slozena_naredba>"))) {
      SNode ime_tipa = node.getChildren().get(0);
      // 1
      ime_tipa.visit(scope);

      // 2
      if (ime_tipa.getType() instanceof ConstType) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 3
      SNode idn = node.getChildren().get(1);
      if (scope.hasDefined(idn.getName())) { // this is already in global scope, checked by SA
        throw new SemanticException(getErrorMessage(node));
      }

      // 4
      VoidFunctionType functionType =
          new VoidFunctionType(Arrays.asList((NumericType) ime_tipa.getType()));
      // this is already in global scope, checked by SA
      if (scope.hasDeclared(idn.getName()) && !scope.getType(idn.getName()).equals(functionType)) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 5
      scope.insert(idn.getName(), functionType, true);

      // 6
      node.getChildren().get(5).visit(new Scope(scope));
    } else if (children.equals(Arrays.asList("<ime_tipa>", "IDN", "L_ZAGRADA",
        "<lista_parametara>", "D_ZAGRADA", "<slozena_naredba>"))) {
      SNode ime_tipa = node.getChildren().get(0);
      // 1
      ime_tipa.visit(scope);

      // 2
      if (ime_tipa.getType() instanceof ConstType) {
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
          new NonVoidFunctionType(lista_parametara.getTypes(), (NumericType) ime_tipa.getType());
      if (scope.hasDeclared(idn.getName()) && !scope.getType(idn.getName()).equals(functionType)) {
        throw new SemanticException(getErrorMessage(node));
      }

      // 6
      scope.insert(idn.getName(), functionType, true);

      // 7
      Scope scopeSlozenaNaredba = new Scope(scope);
      for (int i = 0; i < lista_parametara.getTypes().size(); i++) {
        scopeSlozenaNaredba.insert(lista_parametara.getNames().get(i), lista_parametara.getTypes()
            .get(i), false);
      }
      node.getChildren().get(5).visit(scopeSlozenaNaredba);
    }
  }
}
