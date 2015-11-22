package hr.fer.zemris.ppj.automaton.converters;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

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

    for (S state : automata.getStates()) {
      Set<S> epsilonClosure = automata.epsilonClosure(state);
      epsilonClosureTable.put(state, epsilonClosure);
      newStates.add(epsilonClosure);
      groupStateTable.put(state, epsilonClosure);
    }

    Set<S> newInitialState = new HashSet<>(epsilonClosureTable.get(automata.getInitialState()));

    Set<Set<S>> newAcceptableStates = new HashSet<>();
    for (S state : automata.getAcceptableStates()) {
      newAcceptableStates.add(epsilonClosureTable.get(state));
      if (newInitialState.contains(state)) {
        newAcceptableStates.add(newInitialState);
      }
    }

    TransitionFunction<S, C> helperTransitionFunction = new TransitionFunction<>();
    for (S state : states) {
      for (S epsilonState : epsilonClosureTable.get(state)) {
        for (C symbol : alphabet) {
          for (S destination : transitionFunction.getTransitionResult(epsilonState, symbol)) {
            for (S epsilonDestination : epsilonClosureTable.get(destination)) {
              helperTransitionFunction.addTransition(state, symbol, epsilonDestination);
            }
          }
        }
      }
    }

    TransitionFunction<Set<S>, C> newTransitionFunction = new TransitionFunction<>();
    Map<Pair<S, C>, Collection<S>> transitionTable = helperTransitionFunction.getTransitionTable();
    for (Pair<S, C> pair : transitionTable.keySet()) {
      for (S destination : transitionTable.get(pair)) {
        newTransitionFunction.addTransition(groupStateTable.get(pair.getFirst()), pair.getSecond(), groupStateTable.get(destination));
      }
    }

    return new Automaton<>(newStates, alphabet, newTransitionFunction, newInitialState,
        newAcceptableStates);
  }
}
