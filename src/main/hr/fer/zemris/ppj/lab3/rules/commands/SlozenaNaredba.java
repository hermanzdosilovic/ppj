package hr.fer.zemris.ppj.lab3.rules.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class SlozenaNaredba extends Rule {

  public static SlozenaNaredba SLOZENA_NAREDBA = new SlozenaNaredba();
  
  private SlozenaNaredba() {
    super(new NonTerminalSymbol("<slozena_naredba>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> childrenValues = node.getValuesOfChildren();
    if (childrenValues.equals(Arrays.asList("L_VIT_ZAGRADA", "<lista_naredbi>", "D_VIT_ZAGRADA"))) {
      node.getChildren().get(1).visit(scope);
    } else if (childrenValues.equals(Arrays.asList("L_VIT_ZAGRADA", "<lista_deklaracija>",
        "<lista_naredbi>", "D_VIT_ZAGRADA"))) {
      node.getChildren().get(1).visit(scope);
      node.getChildren().get(2).visit(scope);
    }
  }

}
