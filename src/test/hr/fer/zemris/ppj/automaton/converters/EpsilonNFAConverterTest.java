package hr.fer.zemris.ppj.automaton.converters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

/**
 * These tests will fail due to change of logics in <code>EpsilonNFAConverter</code> class.
 * 
 * @author Ivan Trubic
 */
public class EpsilonNFAConverterTest {

  @Test
  public void basicTest() {
    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();
    transitionFunction.addEpsilonTransition("1", "2");
    transitionFunction.addTransition("2", "a", "3");

    Automaton<String, String> enka = new Automaton<String, String>(Arrays.asList("1", "2", "3"),
        Arrays.asList("a"), transitionFunction, "1", Arrays.asList("2"));
    Automaton<Set<String>, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    TransitionFunction<String, String> expectedTransitionFunction =
        new TransitionFunction<String, String>();
    expectedTransitionFunction.addTransition("1", "a", "3");
    expectedTransitionFunction.addTransition("2", "a", "3");

    assertEquals(expectedTransitionFunction, nka.getTransitionFunction());
    assertEquals(Arrays.asList("1", "2"), new ArrayList<>(nka.getAcceptableStates()));
    assertEquals(Arrays.asList("1", "2", "3"), new ArrayList<>(nka.getStates()));
  }

  @Test
  public void bookExample() {
    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();
    transitionFunction.addTransition("q0", "0", "q0");
    transitionFunction.addTransition("q1", "1", "q1");
    transitionFunction.addTransition("q2", "2", "q2");
    transitionFunction.addEpsilonTransition("q0", "q1");
    transitionFunction.addEpsilonTransition("q1", "q2");

    Automaton<String, String> enka = new Automaton<String, String>(Arrays.asList("q0", "q1", "q2"),
        Arrays.asList("0", "1", "2"), transitionFunction, "q0", Arrays.asList("q2"));
    Automaton<Set<String>, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    TransitionFunction<String, String> expectedTransitionFunction =
        new TransitionFunction<String, String>();
    expectedTransitionFunction.addTransition("q0", "0", "q0");
    expectedTransitionFunction.addTransition("q0", "0", "q1");
    expectedTransitionFunction.addTransition("q0", "0", "q2");
    expectedTransitionFunction.addTransition("q0", "1", "q1");
    expectedTransitionFunction.addTransition("q0", "1", "q2");
    expectedTransitionFunction.addTransition("q0", "2", "q2");
    expectedTransitionFunction.addTransition("q1", "1", "q1");
    expectedTransitionFunction.addTransition("q1", "1", "q2");
    expectedTransitionFunction.addTransition("q1", "2", "q2");
    expectedTransitionFunction.addTransition("q2", "2", "q2");

    assertEquals(new HashSet<>(Arrays.asList("q0", "q1", "q2")), nka.getStates());
    assertEquals("q0", nka.getInitialState());
    assertEquals(new HashSet<>(Arrays.asList("q0", "q2")), nka.getAcceptableStates());
    assertEquals(expectedTransitionFunction, nka.getTransitionFunction());
  }

  @Test
  public void oneState() {
    Automaton<String, String> enka = new Automaton<String, String>(Arrays.asList("q0"),
        new HashSet<String>(), new TransitionFunction<>(), "q0", Arrays.asList("q0"));
    Automaton<Set<String>, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    assertEquals(Arrays.asList("q0"), new ArrayList<>(nka.getStates()));
    assertEquals(Arrays.asList("q0"), new ArrayList<>(nka.getAcceptableStates()));
  }

  @Test
  public void noStates() {
    Automaton<String, String> enka = new Automaton<String, String>(new HashSet<>(), new HashSet<>(),
        new TransitionFunction<>(), "q0", new HashSet<>());
    Automaton<Set<String>, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    assertEquals(Arrays.asList(), new ArrayList<>(nka.getStates()));
  }

  @Test
  public void auditorne() {
    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();
    transitionFunction.addEpsilonTransition("q0", "q1");
    transitionFunction.addEpsilonTransition("q0", "q3");
    transitionFunction.addEpsilonTransition("q1", "q2");
    transitionFunction.addTransition("q0", "a", "q1");
    transitionFunction.addTransition("q0", "b", "q2");
    transitionFunction.addTransition("q0", "c", "q1");
    transitionFunction.addTransition("q1", "a", "q1");
    transitionFunction.addTransition("q1", "a", "q2");
    transitionFunction.addTransition("q1", "b", "q1");
    transitionFunction.addTransition("q1", "c", "q3");
    transitionFunction.addTransition("q2", "a", "q2");
    transitionFunction.addTransition("q2", "b", "q1");
    transitionFunction.addTransition("q2", "c", "q3");
    transitionFunction.addTransition("q3", "a", "q2");


    Automaton<String, String> enka =
        new Automaton<String, String>(Arrays.asList("q0", "q1", "q2", "q3"),
            Arrays.asList("a", "b", "c"), transitionFunction, "q0", Arrays.asList("q2"));
    Automaton<Set<String>, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    TransitionFunction<String, String> expectedTransitionFunction =
        new TransitionFunction<String, String>();
    expectedTransitionFunction.addTransition("q0", "a", "q1");
    expectedTransitionFunction.addTransition("q0", "a", "q2");
    expectedTransitionFunction.addTransition("q0", "b", "q1");
    expectedTransitionFunction.addTransition("q0", "b", "q2");
    expectedTransitionFunction.addTransition("q0", "c", "q1");
    expectedTransitionFunction.addTransition("q0", "c", "q2");
    expectedTransitionFunction.addTransition("q0", "c", "q3");
    expectedTransitionFunction.addTransition("q1", "a", "q1");
    expectedTransitionFunction.addTransition("q1", "a", "q2");
    expectedTransitionFunction.addTransition("q1", "b", "q1");
    expectedTransitionFunction.addTransition("q1", "b", "q2");
    expectedTransitionFunction.addTransition("q1", "c", "q3");
    expectedTransitionFunction.addTransition("q2", "a", "q2");
    expectedTransitionFunction.addTransition("q2", "b", "q1");
    expectedTransitionFunction.addTransition("q2", "b", "q2");
    expectedTransitionFunction.addTransition("q2", "c", "q3");
    expectedTransitionFunction.addTransition("q3", "a", "q2");

    assertEquals(new HashSet<>(Arrays.asList("q1", "q2", "q3", "q0")), nka.getStates());
    assertEquals(new HashSet<>(Arrays.asList("a", "b", "c")), nka.getAlphabet());
    assertEquals("q0", nka.getInitialState());
    assertEquals(expectedTransitionFunction, nka.getTransitionFunction());
  }
}
