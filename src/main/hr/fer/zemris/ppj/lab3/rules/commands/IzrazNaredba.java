package hr.fer.zemris.ppj.lab3.rules.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class IzrazNaredba extends Rule {

  public static IzrazNaredba IZRAZ_NAREDBA = new IzrazNaredba();
  
  private IzrazNaredba() {
    super(new NonTerminalSymbol("<izraz_naredba>"));
  }
  
  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> childrenValues = node.getValuesOfChildren();
    if (childrenValues.equals(Arrays.asList("TOCKAZAREZ"))) {
      node.setType(Int.INT); 
    } else if (childrenValues.equals(Arrays.asList("<izraz>", "TOCKAZAREZ"))) {
      node.getChildren().get(0).visit(scope);
      node.setType(node.getChildren().get(0).getType());
    }
  }

}
