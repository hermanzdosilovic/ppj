package hr.fer.zemris.ppj.automaton;

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
    
    // Za svako stanje
    for (S state : states) {

      // Za svaki znak iz abecede
      for (C input : alphabet) {

        // Nadi epsilon prelaze da imamo sva aktivna stanja
        for(S transition : automata.epsilonClosure(state)){
          newTransitionFunction.addTransition(state, input, transition);

          // Napravi prelaz za input i dodaj te prelaze
          for(S nextTransition : oldTransitionFunction.getTransitionResult(transition, input)){
            newTransitionFunction.addTransition(transition, input, nextTransition);

            // Za svaki taj prelaz jos nadi epsilon prelaze
            for(S nextEpsilonTransition : automata.epsilonClosure(nextTransition)){
              newTransitionFunction.addTransition(nextTransition, input, nextEpsilonTransition);
            }
          }
        }
        // Slucaj za stanja u koja neidemo epsilon prelazima
        
        // nadi prelaz za input
        for(S transition : oldTransitionFunction.getTransitionResult(state, input)){
          newTransitionFunction.addTransition(state, input, transition);
          
          // I od njih epsilon prelaze
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
