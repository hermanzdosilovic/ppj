package hr.fer.zemris.ppj.automaton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class TransitionFunctionTest {

  @Test
  public void addTransitionTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();

    assertTrue(transitionFunction.addTransition(1, 'a', 4));
    assertFalse(transitionFunction.addTransition(1, 'a', 4));

    assertTrue(transitionFunction.addTransition(1, 'a', 5));
    assertFalse(transitionFunction.addTransition(1, 'a', 5));
  }

  @Test
  public void getTransitionStatesTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addTransition(1, 'a', 1);

    assertEquals(Arrays.asList(2, 3, 1), new ArrayList<>(transitionFunction.getTransitionStates(1, 'a')));
    assertNull(transitionFunction.getTransitionStates(1, 'b'));
  }

  @Test
  public void getTransitionSymbolsTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'b', 2);
    transitionFunction.addTransition(1, 'a', 4);
    transitionFunction.addTransition(1, 'c', 3);
    transitionFunction.addTransition(1, 'b', 5);
    transitionFunction.addTransition(1, 'c', 1);
    
    assertEquals(Arrays.asList('a', 'b', 'c'), new ArrayList<>(transitionFunction.getTransitionSymbols(1)));
  }
  
  @Test
  public void existsTransitionTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addTransition(1, 'a', 1);

    assertTrue(transitionFunction.existsTransition(1, 'a'));
    assertFalse(transitionFunction.existsTransition(1, 'b'));
  }
}
