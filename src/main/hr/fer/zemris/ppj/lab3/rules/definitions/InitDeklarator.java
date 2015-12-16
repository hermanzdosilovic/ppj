package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.Arrays;
import java.util.List;

public class InitDeklarator extends Rule {
  public static InitDeklarator INIT_DEKLARATOR = new InitDeklarator();
  
  private InitDeklarator(){
    super(new NonTerminalSymbol("<init_deklarator>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();
    
    if(children.equals(Arrays.asList("<izravni_deklarator>"))){
      SNode izravni_deklarator = node.getChildren().get(0);
      
      // 1
      izravni_deklarator.setnType(node.getnType());
      
      // 2
      if(){
        throw new SemanticException(getErrorMessage(node));
      }
    }
  }

}
