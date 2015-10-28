package hr.fer.zemris.ppj.regex;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.Pair;
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
    Pair<Integer, Integer> statePair = convertExpressionToAutomaton(expression);
    automaton.setStartState(statePair.getFirst());
    automaton.setAcceptState(statePair.getSecond());
    return automaton;
  }
  
  public boolean accepts(String matcher) {
    this.toAutomaton();
    automaton.prepareForRun();
    for(char character : matcher.toCharArray()) {
      automaton.makeTransitions(character);
    }
    return automaton.isInAcceptableState();
  }
  
  private Pair<Integer, Integer> convertExpressionToAutomaton(String expression) {
    List<String> subExpressions = getSubExpressions(expression);

    Integer leftState = automaton.makeNewState();
    Integer rightState = automaton.makeNewState();
    if (subExpressions.size() > 1) {
      for (String subExpression : subExpressions) {
        Pair<Integer, Integer> statePair = convertExpressionToAutomaton(subExpression);
        automaton.addEpsilonTransition(leftState, statePair.getFirst());
        automaton.addEpsilonTransition(statePair.getSecond(), rightState);
      }
    } else {
      boolean prefixed = false;
      Integer lastState = leftState;
      for (int i = 0; i < expression.length(); i++) {
        char character = expression.charAt(i);
        Integer a, b;
        if (prefixed) {
          prefixed = false;
          char transitionalCharacter;
          if (character == 't') {
            transitionalCharacter = '\t';
          } else if (character == 'n') {
            transitionalCharacter = '\n';
          } else if (character == '_') {
            transitionalCharacter = ' ';
          } else {
            transitionalCharacter = character;
          }
          a = automaton.makeNewState();
          b = automaton.makeNewState();
          automaton.addTransition(a, b, transitionalCharacter);
        } else {
          if (character == '\\') {
            prefixed = true;
            continue;
          }
          if (character != '(') {
            a = automaton.makeNewState();
            b = automaton.makeNewState();
            if (character == '$') {
              automaton.addEpsilonTransition(a, b);
            } else {
              automaton.addTransition(a, b, character);
            }
          } else {
            int j = i;
            while (j < expression.length() && expression.charAt(j) != ')') {
              j++;
            }
            Pair<Integer, Integer> statePair =
                convertExpressionToAutomaton(expression.substring(i + 1, j));
            a = statePair.getFirst();
            b = statePair.getSecond();
            i = j;
          }
        }

        if (i + 1 < expression.length() && expression.charAt(i + 1) == '*') {
          Integer x = a;
          Integer y = b;
          a = automaton.makeNewState();
          b = automaton.makeNewState();
          automaton.addEpsilonTransition(a, x);
          automaton.addEpsilonTransition(y, b);
          automaton.addEpsilonTransition(a, b);
          automaton.addEpsilonTransition(y, x);
          i++;
        }

        automaton.addEpsilonTransition(lastState, a);
        lastState = b;
      }
      automaton.addEpsilonTransition(lastState, rightState);
    }

    return new Pair<>(leftState, rightState);
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
      } else
        if (numberOfParenthesis == 0 && character == '|' && !escapedCharacterAt(expression, i)) {
        subExpressions.add(expression.substring(leftIndex, i));
        leftIndex = i + 1;
      }
    }
    subExpressions.add(expression.substring(leftIndex));

    return subExpressions;
  }

  private boolean escapedCharacterAt(String string, int index) {
    boolean isEscaped = false;
    while (index - 1 >= 0 && string.charAt(index - 1) == '\\') {
      isEscaped = !isEscaped;
      index--;
    }
    return isEscaped;
  }

  public boolean escapedCharacterAt(int index) {
    return escapedCharacterAt(expression, index);
  }
}
