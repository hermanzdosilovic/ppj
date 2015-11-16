package hr.fer.zemris.ppj.automaton;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class EpsilonNFAConverterTest {

   @Test
   public void basicTest(){
   Set<String> states = new HashSet<String>();
   Set<String> alphabet = new HashSet<String>();
   String initialState = "1";
   Set<String> acceptableStates = new HashSet<String>();
  
   states.add("1");
   states.add("2");
   states.add("3");
  
   alphabet.add("a");
  
   acceptableStates.add("2");
  
   TransitionFunction<String, String> transitionFunction = new TransitionFunction<String,
   String>();
  
   transitionFunction.addEpsilonTransition("1", "2");
   transitionFunction.addTransition("2", "a", "3");
  
  
   Automaton<String, String> enka = new Automaton<String, String>(states, alphabet,
   transitionFunction, initialState, acceptableStates);
   Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);
  
   Set<String> newAcceptableStates = new HashSet<String>();
   newAcceptableStates.add("1");
   newAcceptableStates.add("2");
  
   assertEquals(nka.getAcceptableStates(), newAcceptableStates);
   assertEquals(nka.getStates(), states);
  
   assertTrue(nka.getTransitionFunction().existsTransition("1", "a"));
   assertTrue(nka.getTransitionFunction().existsTransition("2", "a"));
   }

  @Test
  public void bookExample() {
    Set<String> states = new HashSet<String>();
    Set<String> alphabet = new HashSet<String>();
    String initialState = "q0";
    Set<String> acceptableStates = new HashSet<String>();

    states.add("q0");
    states.add("q1");
    states.add("q2");

    alphabet.add("0");
    alphabet.add("1");
    alphabet.add("2");

    acceptableStates.add("q2");

    TransitionFunction<String, String> transitionFunction =
        new TransitionFunction<String, String>();

    transitionFunction.addTransition("q0", "0", "q0");
    transitionFunction.addTransition("q1", "1", "q1");
    transitionFunction.addTransition("q2", "2", "q2");
    transitionFunction.addEpsilonTransition("q0", "q1");
    transitionFunction.addEpsilonTransition("q1", "q2");

    Automaton<String, String> enka =
        new Automaton<String, String>(states, alphabet, transitionFunction, initialState,
            acceptableStates);
    Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);

    assertEquals(nka.getStates(), states);
    assertEquals(nka.getInitialState(), "q0");
    Set<String> newAcceptableStates = new HashSet<String>();
    newAcceptableStates.add("q2");
    newAcceptableStates.add("q0");
    assertEquals(nka.getAcceptableStates(), newAcceptableStates);

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

}
