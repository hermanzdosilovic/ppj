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


    Set<Set<S>> newPossibleStates = new HashSet<>();
    for (S state : automata.getStates()) {
      newPossibleStates.add(automata.epsilonClosure(state));
    }

    Map<S, List<Set<S>>> groupStateTable = new HashMap<>();
    Set<Set<S>> newStates = new HashSet<>();
    for (Set<S> firstSet : newPossibleStates) {
      boolean isSubset = false;
      for (Set<S> secondSet : newPossibleStates) {
        if (secondSet.containsAll(firstSet) && !secondSet.equals(firstSet)) {
          isSubset = true;
        }
      }
      if (!isSubset) {
        newStates.add(firstSet);
        for (S state : firstSet) {
          if (!groupStateTable.containsKey(state)) {
            groupStateTable.put(state, new ArrayList<>());
          }
          groupStateTable.get(state).add(firstSet);
        }
      }
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
      for (C symbol : alphabet) {
        for (S destination : transitionFunction.getTransitionResult(state, symbol)) {
          for (Set<S> firstGroup : groupStateTable.get(state)) {
            for (Set<S> secondGroup : groupStateTable.get(destination)) {
              newTransitionFunction.addTransition(firstGroup, symbol, secondGroup);
            }
          }
        }
      }
    }

    return new Automaton<>(newStates, alphabet, newTransitionFunction, newInitialState,
        newAcceptableStates);
  }
}
