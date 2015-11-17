package hr.fer.zemris.ppj.automaton;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class EpsilonNFAConverterTest {

  @Test
  public void basicTest() {

    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();

    transitionFunction.addEpsilonTransition("1", "2");
    transitionFunction.addTransition("2", "a", "3");


    Automaton<String, String> enka =
        new Automaton<String, String>(Arrays.asList("1", "2", "3"), Arrays.asList("a"),
            transitionFunction, "1", Arrays.asList("2"));
    Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);
    
    TransitionFunction<String, String> expectedTransitionFunction = new TransitionFunction<String, String>();
    
    expectedTransitionFunction.addTransition("1", "a", "3");
    expectedTransitionFunction.addTransition("2", "a", "3");
    
    assertEquals(expectedTransitionFunction, nka.getTransitionFunction());

    assertEquals(Arrays.asList("1", "2"), new ArrayList<>(nka.getAcceptableStates()));
    assertEquals(Arrays.asList("1", "2", "3"), new ArrayList<>(nka.getStates()));

    assertTrue(nka.getTransitionFunction().existsTransition("1", "a"));
    assertTrue(nka.getTransitionFunction().existsTransition("2", "a"));
  }

  @Test
  public void bookExample() {
    Set<String> states = new HashSet<String>();

    states.add("q0");
    states.add("q1");
    states.add("q2");

    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();

    transitionFunction.addTransition("q0", "0", "q0");
    transitionFunction.addTransition("q1", "1", "q1");
    transitionFunction.addTransition("q2", "2", "q2");
    transitionFunction.addEpsilonTransition("q0", "q1");
    transitionFunction.addEpsilonTransition("q1", "q2");

    Automaton<String, String> enka =
        new Automaton<String, String>(Arrays.asList("q0", "q1", "q2"),
            Arrays.asList("0", "1", "2"), transitionFunction, "q0", Arrays.asList("q2"));
    Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);
    
    TransitionFunction<String, String> expectedTransitionFunction = new TransitionFunction<String, String>();
    
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

    assertEquals(nka.getStates(), states);
    assertEquals(nka.getInitialState(), "q0");
    assertEquals(Arrays.asList("q2", "q0"), new ArrayList<>(nka.getAcceptableStates()));
    assertEquals(expectedTransitionFunction, nka.getTransitionFunction());

    Set<String> newTransitionResults = new HashSet<String>();

    assertTrue(nka.getTransitionFunction().existsTransition("q0", "0"));
    newTransitionResults.add("q0");
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q0", "0"));

    assertTrue(nka.getTransitionFunction().existsTransition("q0", "1"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q0", "1"));

    assertTrue(nka.getTransitionFunction().existsTransition("q0", "2"));
    newTransitionResults.clear();
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q0", "2"));

    assertFalse(nka.getTransitionFunction().existsTransition("q1", "0"));
    newTransitionResults.clear();
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q1", "0"));

    assertTrue(nka.getTransitionFunction().existsTransition("q1", "1"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q1", "1"));

    assertTrue(nka.getTransitionFunction().existsTransition("q1", "2"));
    newTransitionResults.clear();
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q1", "2"));


    assertFalse(nka.getTransitionFunction().existsTransition("q2", "0"));
    newTransitionResults.clear();
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q2", "0"));


    assertFalse(nka.getTransitionFunction().existsTransition("q2", "1"));
    newTransitionResults.clear();
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q2", "1"));


    assertTrue(nka.getTransitionFunction().existsTransition("q2", "2"));
    newTransitionResults.clear();
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q2", "2"));

  }

  @Test
  public void oneState() {

    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();

    Automaton<String, String> enka =
        new Automaton<String, String>(Arrays.asList("q0"), new HashSet<String>(),
            transitionFunction, "q0", Arrays.asList("q0"));
    Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    assertEquals(Arrays.asList("q0"), new ArrayList<>(nka.getStates()));
    assertEquals(Arrays.asList("q0"), new ArrayList<>(nka.getAcceptableStates()));
  }

  @Test
  public void noStates() {

    Automaton<String, String> enka =
        new Automaton<String, String>(new HashSet<>(), new HashSet<>(),
            new TransitionFunction<String, String>(), "q0", new HashSet<>());
    Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);

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
        new Automaton<String, String>(Arrays.asList("q0", "q1", "q2", "q3"), Arrays.asList("a",
            "b", "c"), transitionFunction, "q0", Arrays.asList("q2"));
    Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    TransitionFunction<String, String> expectedTransitionFunction = new TransitionFunction<String, String>();
    
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

    assertEquals(Arrays.asList("q1", "q2", "q3", "q0"), new ArrayList<>(nka.getStates()));
    assertEquals(Arrays.asList("a", "b", "c"), new ArrayList<>(nka.getAlphabet()));
    assertEquals("q0", nka.getInitialState());
    TransitionFunction<String, String> newTransitionFunction = nka.getTransitionFunction();
    assertEquals(expectedTransitionFunction, newTransitionFunction);

    Set<String> newTransitionResults = new HashSet<String>();


    assertTrue(nka.getTransitionFunction().existsTransition("q0", "a"));
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q0", "a"));

    assertTrue(nka.getTransitionFunction().existsTransition("q0", "b"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q0", "b"));

    assertTrue(nka.getTransitionFunction().existsTransition("q0", "c"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    newTransitionResults.add("q3");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q0", "c"));

    assertTrue(nka.getTransitionFunction().existsTransition("q1", "a"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q1", "a"));

    assertTrue(nka.getTransitionFunction().existsTransition("q1", "b"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q1", "b"));

    assertTrue(nka.getTransitionFunction().existsTransition("q1", "c"));
    newTransitionResults.clear();
    newTransitionResults.add("q3");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q1", "c"));


    assertTrue(nka.getTransitionFunction().existsTransition("q2", "a"));
    newTransitionResults.clear();
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q2", "a"));


    assertTrue(nka.getTransitionFunction().existsTransition("q2", "b"));
    newTransitionResults.clear();
    newTransitionResults.add("q1");
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q2", "b"));


    assertTrue(nka.getTransitionFunction().existsTransition("q2", "c"));
    newTransitionResults.clear();
    newTransitionResults.add("q3");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q2", "c"));

    assertTrue(nka.getTransitionFunction().existsTransition("q3", "a"));
    newTransitionResults.clear();
    newTransitionResults.add("q2");
    assertEquals(newTransitionResults, nka.getTransitionFunction().getTransitionResult("q3", "a"));
  }

}
