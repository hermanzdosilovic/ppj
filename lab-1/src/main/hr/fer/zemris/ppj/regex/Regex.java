package hr.fer.zemris.ppj.regex;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.automaton.Automaton;

public class Regex {
  
  private String expression;
  private Automaton automaton;
  
  public Regex(final String value) {
    this.expression = value;
  }

  public String getExpression() {
    return expression;
  }
  
  public Automaton toAutomaton() {
    if (automaton != null) {
      return automaton;
    }
    automaton = new Automaton();
//    convertExpressionToAutomaton(expression);
    return automaton;
  }
  
  public List<String> getSubExpressions() {
    return getSubExpressions(expression);
  }
  
  private List<String> getSubExpressions(String expression) {
    List<String> subExpressions = new ArrayList<>();
    int numberOfParenthesis = 0;
    int leftIndex = 0;
    
    for (int i = 0; i < expression.length(); i++) {
      char character = expression.charAt(i);
      if (character == '(' && !escapedCharacterAt(expression, i)) {
        numberOfParenthesis++;
      } else if (character == ')' && !escapedCharacterAt(expression, i)) {
        numberOfParenthesis--;
      } else if (numberOfParenthesis == 0 && character == '|' && !escapedCharacterAt(expression, i)) {
        subExpressions.add(expression.substring(leftIndex, i));
        leftIndex = i + 1;
      }
    }
    subExpressions.add(expression.substring(leftIndex));
    
    return subExpressions;
  }
  
  private boolean escapedCharacterAt(String value, int index) {
    boolean isEscaped = false;
    while(index - 1 >= 0 && value.charAt(index - 1) == '\\') {
      isEscaped = !isEscaped;
      index--;
    }
    return isEscaped;
  }
  
  public boolean escapedCharacterAt(int index) {
    return escapedCharacterAt(expression, index);
  }
}
