package hr.fer.zemris.ppj.automaton;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

public class AutomatonTest {

  @Test
  public void epsilonClosureOfOneStateBasicTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();

    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addEpsilonTransition(2, 5);
    transitionFunction.addEpsilonTransition(3, 4);
    transitionFunction.addEpsilonTransition(4, 2);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3, 4, 5), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1, 2, 5), new ArrayList<>(automaton.epsilonClosure(1)));
    assertEquals(Arrays.asList(2, 5), new ArrayList<>(automaton.epsilonClosure(2)));
    assertEquals(Arrays.asList(2, 3, 4, 5), new ArrayList<>(automaton.epsilonClosure(3)));
    assertEquals(Arrays.asList(2, 4, 5), new ArrayList<>(automaton.epsilonClosure(4)));
    assertEquals(Arrays.asList(5), new ArrayList<>(automaton.epsilonClosure(5)));
  }

  @Test
  public void epsilonClosureOfOneStateAdvancedTest() {
    TransitionFunction<Integer, String> transitionFunction = new TransitionFunction<>();

    transitionFunction.addEpsilonTransition(1, 1);
    transitionFunction.addTransition(1, "b", 2);
    transitionFunction.addEpsilonTransition(2, 1);
    transitionFunction.addTransition(2, "c", 3);
    transitionFunction.addEpsilonTransition(3, 2);

    Automaton<Integer, String> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1), new ArrayList<>(automaton.epsilonClosure(1)));
    assertEquals(Arrays.asList(1, 2), new ArrayList<>(automaton.epsilonClosure(2)));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(automaton.epsilonClosure(3)));
  }

  @Test
  public void epsilonClosureOfMultipleStatesTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();

    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addTransition(1, 'a', 3);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addEpsilonTransition(2, 5);
    transitionFunction.addEpsilonTransition(3, 4);
    transitionFunction.addEpsilonTransition(4, 2);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3, 4, 5), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1, 2, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(1, 2))));
    assertEquals(Arrays.asList(1, 2, 3, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(1, 3))));
    assertEquals(Arrays.asList(1, 2, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(1, 4))));
    assertEquals(Arrays.asList(1, 2, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(1, 5))));

    assertEquals(Arrays.asList(2, 3, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(2, 3))));
    assertEquals(Arrays.asList(2, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(2, 4))));
    assertEquals(Arrays.asList(2, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(2, 5))));

    assertEquals(Arrays.asList(2, 3, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(3, 4))));
    assertEquals(Arrays.asList(2, 3, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(3, 5))));

    assertEquals(Arrays.asList(2, 4, 5),
        new ArrayList<>(automaton.epsilonClosure(Arrays.asList(4, 5))));
  }

  @Test
  public void epsilonClosureOfOneStateWithNoEpsilonTransitions() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();

    transitionFunction.addTransition(1, 'b', 2);
    transitionFunction.addTransition(1, 'c', 3);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1), new ArrayList<>(automaton.epsilonClosure(1)));
  }

  @Test
  public void epsilonClosureOfCycledGraphWithEpsilonTransitions() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();

    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addEpsilonTransition(2, 3);
    transitionFunction.addEpsilonTransition(3, 1);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(automaton.epsilonClosure(1)));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(automaton.epsilonClosure(2)));
    assertEquals(Arrays.asList(1, 2, 3), new ArrayList<>(automaton.epsilonClosure(3)));
  }

  @Test
  public void getCurrentStatesTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(1, 2);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1, 2), new ArrayList<>(automaton.getCurrentStates()));

    transitionFunction.addTransition(2, 'a', 1);
    automaton = new Automaton<>(Arrays.asList(1, 2), transitionFunction, 2, Arrays.asList(1));
    assertEquals(Arrays.asList(2), new ArrayList<>(automaton.getCurrentStates()));
  }

  @Test
  public void readOneSymbolBasicTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(1, 2);
    transitionFunction.addTransition(1, 'c', 3);
    transitionFunction.addTransition(2, 'c', 4);
    transitionFunction.addTransition(3, 'c', 2);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(3, 4), new ArrayList<>(automaton.read('c')));
    assertEquals(Arrays.asList(2), new ArrayList<>(automaton.read('c')));
  }

  @Test
  public void readOneSymbolCycleTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'c', 1);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1), new ArrayList<>(automaton.read('c')));
  }

  @Test
  public void readOneSymbolNoTransitionTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'c', 1);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(), new ArrayList<>(automaton.read('d')));
  }

  @Test
  public void readOneSymbolCycleEpsilonClosureTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addEpsilonTransition(2, 1);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(1, 2), new ArrayList<>(automaton.read('a')));
  }
  
  @Test
  public void readOneSymbolCycleWithNoEpsilonClosureTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addEpsilonTransition(2, 3);
    transitionFunction.addTransition(3, 'a', 1);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(2, 3), new ArrayList<>(automaton.read('a')));
    assertEquals(Arrays.asList(1), new ArrayList<>(automaton.read('a')));
  }
  
  @Test
  public void readSequenceBasicTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addEpsilonTransition(2, 3);
    transitionFunction.addTransition(2, 'b', 4);
    
    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3, 4), transitionFunction, 1, Arrays.asList(1));
    assertEquals(Arrays.asList(4), new ArrayList<>(automaton.read(Arrays.asList('a', 'b'))));
  }
}
