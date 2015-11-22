package hr.fer.zemris.ppj.automaton.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.helpers.Stopwatch;

/**
 * @author Ivan Trubic
 */
public class EpsilonNFAConverter {

  public static <S, C> Automaton<Set<S>, C> convertToNFA(Automaton<S, C> automata) {
    Set<S> states = automata.getStates();
    Set<C> alphabet = automata.getAlphabet();
    TransitionFunction<S, C> transitionFunction = automata.getTransitionFunction();
    
    Map<S, Set<S>> groupStateTable = new HashMap<>();
    Set<Set<S>> newStates = new HashSet<>();
    Map<S, Set<S>> epsilonClosureTable = new HashMap<>();
    
    Stopwatch.start();
    for (S state : automata.getStates()) {
      Set<S> epsilonClosure = automata.epsilonClosure(state);
      epsilonClosureTable.put(state, epsilonClosure);
      newStates.add(epsilonClosure);
      groupStateTable.put(state, epsilonClosure);
    }
    System.err.println("\nepsilonClosure: " + Stopwatch.end());

    Set<S> newInitialState = new HashSet<>(epsilonClosureTable.get(automata.getInitialState()));
    
    Stopwatch.start();
    Set<Set<S>> newAcceptableStates = new HashSet<>();
    for (S state : automata.getAcceptableStates()) {
      newAcceptableStates.add(epsilonClosureTable.get(state));
      if (newInitialState.contains(state)) {
        newAcceptableStates.add(newInitialState);
      }
    }
    System.err.println("\nacceptableStates: " + Stopwatch.end());
    
    Stopwatch.start();
    TransitionFunction<Set<S>, C> newTransitionFunction = new TransitionFunction<>();
    for (S state : states) {
      for (S epsilonState : epsilonClosureTable.get(state)) {
        for (C symbol : alphabet) {
          for (S destination : transitionFunction.getTransitionResult(epsilonState, symbol)) {
            for (S epsilonDestination : epsilonClosureTable.get(destination)) {
              newTransitionFunction.addTransition(groupStateTable.get(state), symbol,
                  groupStateTable.get(epsilonDestination));
            }
          }
        }
      }
    }
    System.err.println("\ntransitionFunction: " + Stopwatch.end());

    return new Automaton<>(newStates, alphabet, newTransitionFunction, newInitialState,
        newAcceptableStates);
  }
}
