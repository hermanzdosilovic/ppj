package hr.fer.zemris.ppj.lab3.rules;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public abstract class Rule {
  private Symbol symbol;

  public Rule(Symbol symbol) {
    this.symbol = symbol;
  }

  public void visit(SNode node, Scope scope) throws SemanticException {
    node.setScope(scope);
    checkRule(node, scope);
  };

  public abstract void checkRule(SNode node, Scope scope) throws SemanticException; 
  
  public Symbol getSymbol() {
    return symbol;
  }
  
  public String getErrorMessage(SNode node) {
    StringBuilder message = new StringBuilder();
    message.append(node.getSymbol());
    message.append(" ::= ");

    for (SNode child : node.getChildren()) {
      if (child.getSymbol() instanceof NonTerminalSymbol) {
        message.append(child.getSymbol());
      } else {
        message.append(child.getSymbol() + "(");
        message.append(node.getLineNumber() + "," + node.getValue() + ")");
      }
      message.append(" ");
    }
 
    return message.toString().trim();
  }
}
