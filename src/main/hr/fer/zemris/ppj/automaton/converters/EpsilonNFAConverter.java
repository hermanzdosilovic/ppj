package hr.fer.zemris.ppj.automaton.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    for (S state : automata.getStates()) {
      newStates.add(automata.epsilonClosure(state));
      groupStateTable.put(state, automata.epsilonClosure(state));
    }

    Set<S> newInitialState = new HashSet<>(automata.epsilonClosure(automata.getInitialState()));

    Set<Set<S>> newAcceptableStates = new HashSet<>();
    for (S state : automata.getAcceptableStates()) {
      newAcceptableStates.add(automata.epsilonClosure(state));
      if (newInitialState.contains(state)) {
        newAcceptableStates.add(newInitialState);
      }
    }

    TransitionFunction<Set<S>, C> newTransitionFunction = new TransitionFunction<>();
    for (S state : states) {
      for (S epsilonState : automata.epsilonClosure(state)) {
        for (C symbol : alphabet) {
          for (S destination : transitionFunction.getTransitionResult(epsilonState, symbol)) {
            for (S epsilonDestination : automata.epsilonClosure(destination)) {
              newTransitionFunction.addTransition(groupStateTable.get(state), symbol,
                  groupStateTable.get(epsilonDestination));
            }
          }
        }
      }
    }

    return new Automaton<>(newStates, alphabet, newTransitionFunction, newInitialState,
        newAcceptableStates);
  }
}
