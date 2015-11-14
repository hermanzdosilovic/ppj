package hr.fer.zemris.ppj.automaton;

import java.util.HashMap;
import java.util.Set;

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
      }
    }

    TransitionFunction<S, C> newTransitionFunction = new TransitionFunction<S, C>();
    
    for (S state : states) {
      for (C input : alphabet) {
        for(S transition : automata.epsilonClosure(state)){
          newTransitionFunction.addTransition(state, input, transition);
        }
        for(S transition : oldTransitionFunction.getTransitionResult(state, input)){
          for(S closure : automata.epsilonClosure(transition)){
            newTransitionFunction.addTransition(state, input, closure);
          }
         
        }
      }
    }


    Automaton<S, C> nfaAutomata =
        new Automaton<S, C>(states, alphabet, newTransitionFunction, initialState, acceptableStates);

    return nfaAutomata;

  }
}
