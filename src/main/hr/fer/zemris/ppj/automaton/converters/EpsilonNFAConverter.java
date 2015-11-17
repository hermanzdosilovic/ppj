package hr.fer.zemris.ppj.automaton.converters;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

import java.util.Set;

/**
 * @author Ivan Trubic
 */
public class EpsilonNFAConverter {

  public static <S, C> Automaton<S, C> convertToNFA(Automaton<S, C> automata) {
    Set<S> states = automata.getStates();
    Set<C> alphabet = automata.getAlphabet();
    TransitionFunction<S, C> oldTransitionFunction =
        new TransitionFunction<S, C>(automata.getTransitionFunction());
    S initialState = automata.getInitialState();
    Set<S> acceptableStates = automata.getAcceptableStates();

    for (S state : automata.epsilonClosure(initialState)) {
      if (automata.hasAcceptableState(state)) {
        acceptableStates.add(initialState);
        break;
      }
    }

    TransitionFunction<S, C> newTransitionFunction = new TransitionFunction<S, C>();
    for (S state : states) {
      for (C symbol : alphabet) {
        for (S epsilonTransition : automata.epsilonClosure(state)) {
          for (S nextState : oldTransitionFunction.getTransitionResult(epsilonTransition, symbol)) {
            for (S nextEpsilonClosureState : automata.epsilonClosure(nextState)) {
              newTransitionFunction.addTransition(state, symbol, nextEpsilonClosureState);
            }
          }
        }
      }
    }

    Automaton<S, C> nfaAutomata =
        new Automaton<S, C>(states, alphabet, newTransitionFunction, initialState, acceptableStates);

    return nfaAutomata;

  }
}
