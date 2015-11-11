package hr.fer.zemris.ppj.automaton;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class AutomatonTest {
  
  @Test
  public void epsilonClosureOnOneStateBasicTest() {
    TransitionFunction<Integer, BasicSymbol> transitionFunction = new TransitionFunction<>();
    BasicSymbol epsilonSymbol = new BasicSymbol(BasicSymbol.EPSILON_SYMBOL_VALUE);
    
    transitionFunction.addTransition(1, epsilonSymbol, 2);
    transitionFunction.addTransition(1, new BasicSymbol("a"), 3);
    transitionFunction.addTransition(2, new BasicSymbol("b"), 3);
    transitionFunction.addTransition(2, epsilonSymbol, 5);
    transitionFunction.addTransition(3, epsilonSymbol, 4);
    transitionFunction.addTransition(4, epsilonSymbol, 2);
    
    Automaton<Integer, BasicSymbol> automaton = new Automaton<>(Arrays.asList(1, 2, 3, 4, 5), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1, 2, 5), new ArrayList<>(automaton.epsilonClosure(1)));
    assertEquals(Arrays.asList(2, 5), new ArrayList<>(automaton.epsilonClosure(2)));
    assertEquals(Arrays.asList(2, 3, 4, 5), new ArrayList<>(automaton.epsilonClosure(3)));
    assertEquals(Arrays.asList(2, 4, 5), new ArrayList<>(automaton.epsilonClosure(4)));
    assertEquals(Arrays.asList(5), new ArrayList<>(automaton.epsilonClosure(5)));
  }
  
  @Test
  public void epsilonClosureOnOneStateAdvancedTest() {
    TransitionFunction<Integer, BasicSymbol> transitionFunction = new TransitionFunction<>();
    BasicSymbol epsilonSymbol = new BasicSymbol(BasicSymbol.EPSILON_SYMBOL_VALUE);
    
    transitionFunction.addTransition(1, epsilonSymbol, 1);
    transitionFunction.addTransition(1, new BasicSymbol("b"), 2);
    transitionFunction.addTransition(2, epsilonSymbol, 1);
    transitionFunction.addTransition(2, new BasicSymbol("c"), 3);
    transitionFunction.addTransition(3, epsilonSymbol, 2);
    
    Automaton<Integer, BasicSymbol> automaton = new Automaton<>(Arrays.asList(1, 2, 3), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1), new ArrayList<>(automaton.epsilonClosure(1)));
    assertEquals(Arrays.asList(1, 2), new ArrayList<>(automaton.epsilonClosure(2)));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(automaton.epsilonClosure(3)));
  }
  
  private class BasicSymbol implements ISymbol {

    public static final String EPSILON_SYMBOL_VALUE = "<epsilon>";
    private String value;

    public BasicSymbol(final String value) {
      this.value = value;
    }

    @Override
    public boolean isEpsilonSymbol() {
      return value.equals(EPSILON_SYMBOL_VALUE);
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      BasicSymbol other = (BasicSymbol) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (value == null) {
        if (other.value != null)
          return false;
      } else if (!value.equals(other.value))
        return false;
      return true;
    }

    private AutomatonTest getOuterType() {
      return AutomatonTest.this;
    }
  }
}
