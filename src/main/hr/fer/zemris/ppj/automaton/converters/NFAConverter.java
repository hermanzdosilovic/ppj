package hr.fer.zemris.ppj.automaton.converters;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

public class NFAConverter {

  public static <S, C> Automaton<Set<S>, C> convertToDFA(Automaton<S, C> automaton) {
    Set<Set<S>> initialState = findInitialState(automaton.getInitialState());
    Set<C> alphabet = automaton.getAlphabet();

    Set<Set<S>> states = findStates(alphabet, initialState, automaton.getTransitionFunction());
    Set<Set<S>> newAcceptibleStates = findAcceptableStates(states, automaton.getAcceptableStates());
    TransitionFunction<Set<S>, C> newTransitionFunction =
        findTransitions(automaton.getTransitionFunction(), states, alphabet);

    return new Automaton<Set<S>, C>(newTransitionFunction.getAllSources(), alphabet,
        newTransitionFunction, initialState, newAcceptibleStates);
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

  static <S> Set<Set<S>> findInitialState(Set<S> initalState) {
    Set<Set<S>> newInitialState = new HashSet<>();
    newInitialState.add(initalState);
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
        if (!newTransition.isEmpty()) {
          newTransitionFunction.addTransition(state, symbol, newTransition);
        }
      }
    }
    return newTransitionFunction;
  }

  static <S, C> Set<Set<S>> findStates(Set<C> alphabet, Set<Set<S>> initialState,
      TransitionFunction<S, C> transitionFunction) {
    Set<Set<S>> newStates = new HashSet<Set<S>>();
    Queue<Set<S>> queue = new LinkedList<Set<S>>();
    queue.addAll(initialState);

    TransitionFunction<Set<S>, C> newTransitionFunction = new TransitionFunction<>();
    while (!queue.isEmpty()) {
      Set<S> currentState = queue.remove();
      newStates.add(currentState);

      for (C symbol : alphabet) {
        Set<S> newState = new HashSet<S>();
        if (!newTransitionFunction.existsTransition(currentState, symbol)) {
          for (S state : currentState) {
            newState.addAll(transitionFunction.getTransitionResult(state, symbol));
          }
          newTransitionFunction.addTransition(currentState, symbol, newState);
          if (!newStates.contains(newState) && !newState.isEmpty()) {
            queue.add(newState);
          }
        }
      }
    }

    return newStates;
  }

}
