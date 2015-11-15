package hr.fer.zemris.ppj.automaton;

import static org.junit.Assert.*;

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

   TransitionFunction<String, String> transitionFunction = new TransitionFunction<String, String>();
   
   transitionFunction.addEpsilonTransition("1", "2");
   transitionFunction.addTransition("2", "a", "3");
   

   Automaton<String, String> enka = new Automaton<String, String>(states, alphabet, transitionFunction, initialState, acceptableStates);
   Automaton<String, String> nka = EpsilonNFAConverter.convertToNFA(enka);

   Set<String> newAcceptableStates = new HashSet<String>();
   newAcceptableStates.add("1");
   newAcceptableStates.add("2");

   assertEquals(nka.getAcceptableStates(), newAcceptableStates);
   assertEquals(nka.getStates(), states);
   
   assertTrue(nka.getTransitionFunction().existsTransition("1", "a"));
   assertTrue(nka.getTransitionFunction().existsTransition("2", "a"));
  }

}
