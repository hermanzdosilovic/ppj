package hr.fer.zemris.ppj.lab3.rules.commands;

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

public class NaredbaGrananja extends Rule {

  public static NaredbaGrananja NAREDBA_GRANANJA = new NaredbaGrananja();

  private NaredbaGrananja() {
    super(new NonTerminalSymbol("<naredba_grananja>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> childrenValues = node.getValuesOfChildren();
    SNode izraz = node.getChildren().get(2);
    if (childrenValues
        .equals(Arrays.asList("KR_IF", "L_ZAGRADA", "<izraz>", "D_ZAGRADA", "<naredba>"))) {
      izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(izraz.getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      String labela1 = GeneratorKoda.getNextLabel();
      
      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tCMP R0, 0");
      GeneratorKoda.writeln("\tJP_EQ " + labela1);
      
      node.getChildren().get(4).visit(scope);
      GeneratorKoda.writeln(labela1);
    
    } else if (childrenValues.equals(Arrays.asList("KR_IF", "L_ZAGRADA", "<izraz>", "D_ZAGRADA",
        "<naredba>", "KR_ELSE", "<naredba>"))) {
      izraz.visit(scope);
      if (!TypesHelper.canImplicitlyCast(node.getChildren().get(2).getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }
      
      String labela1 = GeneratorKoda.getNextLabel();
      String labela2 = GeneratorKoda.getNextLabel();
      
      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tCMP R0, 0");
      GeneratorKoda.writeln("\tJP_EQ " + labela1);
      
      node.getChildren().get(4).visit(scope);
      GeneratorKoda.writeln("\tJP "+ labela2);
      
      GeneratorKoda.writeln(labela1);
      node.getChildren().get(6).visit(scope);
      GeneratorKoda.writeln(labela2);
    }
  }

}
