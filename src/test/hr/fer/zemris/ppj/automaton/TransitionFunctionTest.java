package hr.fer.zemris.ppj.automaton;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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
  public void addEpsilonTransitionTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();

    assertTrue(transitionFunction.addEpsilonTransition(1, 2));
    assertFalse(transitionFunction.addEpsilonTransition(1, 2));

    assertTrue(transitionFunction.addEpsilonTransition(1, 3));
    assertFalse(transitionFunction.addEpsilonTransition(1, 3));
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

  @Test
  public void existsEpsilonTransition() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(1, 1);
    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addEpsilonTransition(1, 4);
    transitionFunction.addEpsilonTransition(2, 3);
    transitionFunction.addEpsilonTransition(3, 1);

    assertTrue(transitionFunction.existsEpsilonTransition(1, 1));
    assertTrue(transitionFunction.existsEpsilonTransition(1, 2));
    assertTrue(transitionFunction.existsEpsilonTransition(1, 4));
    assertTrue(transitionFunction.existsEpsilonTransition(2, 3));
    assertTrue(transitionFunction.existsEpsilonTransition(3, 1));

    assertFalse(transitionFunction.existsEpsilonTransition(4, 1));
    assertFalse(transitionFunction.existsEpsilonTransition(2, 1));
    assertFalse(transitionFunction.existsEpsilonTransition(3, 2));
    assertFalse(transitionFunction.existsEpsilonTransition(1, 3));
  }

  public void getTransitionResultTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addEpsilonTransition(1, 4);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addTransition(3, 'c', 1);

    assertEquals(Arrays.asList(3),
        new ArrayList<>(transitionFunction.getTransitionResult(1, 'a')));
    assertEquals(Arrays.asList(3),
        new ArrayList<>(transitionFunction.getTransitionResult(2, 'b')));
    assertEquals(Arrays.asList(1),
        new ArrayList<>(transitionFunction.getTransitionResult(3, 'c')));
    
    transitionFunction.addTransition(1, 'a', 2);
    assertEquals(Arrays.asList(2, 3),
        new ArrayList<>(transitionFunction.getTransitionResult(1, 'a')));
  }

  @Test
  public void getEpsilonTransitionResultTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addEpsilonTransition(1, 4);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addTransition(3, 'c', 1);

    assertEquals(Arrays.asList(2, 4),
        new ArrayList<>(transitionFunction.getEpsilonTransitionResult(1)));
    assertEquals(Arrays.asList(),
        new ArrayList<>(transitionFunction.getEpsilonTransitionResult(2)));
    assertEquals(Arrays.asList(),
        new ArrayList<>(transitionFunction.getEpsilonTransitionResult(3)));
  }
  
  @Test
  public void getDestinationsTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addEpsilonTransition(1, 4);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addTransition(3, 'c', 1);
    
    assertEquals(Arrays.asList(2, 3, 4), new ArrayList<>(transitionFunction.getDestinations(1)));
    assertEquals(Arrays.asList(3), new ArrayList<>(transitionFunction.getDestinations(2)));
    assertEquals(Arrays.asList(1), new ArrayList<>(transitionFunction.getDestinations(3)));
    assertEquals(Arrays.asList(), new ArrayList<>(transitionFunction.getDestinations(4)));
  }
}
