package hr.fer.zemris.ppj.automaton.converters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.helpers.SubsetHelper;

public class NFAConverter {

  public static <S, C> Automaton<Set<S>, C> convertToDFA(Automaton<S, C> automaton) {
    Set<Set<S>> newStates = SubsetHelper.getAllSubsets(automaton.getStates());
    Set<Set<S>> newAcceptibleStates =
        findAcceptableStates(newStates, automaton.getAcceptableStates());
    Set<S> initialState = findInitialState(newStates, automaton.getInitialState());
    Set<C> alphabet = automaton.getAlphabet();
    TransitionFunction<Set<S>, C> newTransitionFunction =
        findTransitions(automaton.getTransitionFunction(), newStates, alphabet);

    return new Automaton<Set<S>, C>(newStates, alphabet, newTransitionFunction, initialState,
        newAcceptibleStates);

  }

  static <S> Set<Set<S>> findAcceptableStates(Set<Set<S>> newStates, Set<S> acceptibleStates) {
    Set<Set<S>> newAcceptibleStates = new HashSet<Set<S>>();

    for (Set<S> newState : newStates) {
      for (S state : newState) {
        if (acceptibleStates.contains(state)) {
          newAcceptibleStates.add(newState);
          break;
        }
      }
    }
    return newAcceptibleStates;
  }

  static <S> Set<S> findInitialState(Set<Set<S>> newStates, S initalState) {
    Set<S> newInitialState = new HashSet<S>();

    for (Set<S> newState : newStates) {
      if (newState.equals(new HashSet<S>(Arrays.asList(initalState)))) {
        newInitialState = newState;
        break;
      }
    }
    return newInitialState;
  }

  static <S, C> TransitionFunction<Set<S>, C> findTransitions(
      TransitionFunction<S, C> transitionFunction, Set<Set<S>> states, Set<C> alphabet) {
    TransitionFunction<Set<S>, C> newTransitionFunction = new TransitionFunction<Set<S>, C>();

    for (Set<S> state : states) {
      for (C symbol : alphabet) {
        Set<S> newTransition = new HashSet<S>();
        for (S singleState : state) {
          newTransition.addAll(transitionFunction.getTransitionResult(singleState, symbol));
        }
        newTransitionFunction.addTransition(state, symbol, newTransition);
      }
    }
    return newTransitionFunction;
  }

}
