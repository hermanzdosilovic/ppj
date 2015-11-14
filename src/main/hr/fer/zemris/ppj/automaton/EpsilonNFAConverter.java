package hr.fer.zemris.ppj.automaton;

import java.util.Set;

public class EpsilonNFAConverter {

  public static <S, C> Automaton<S, C> convertToNFA(Automaton<S, C> automata){
  Set<S> states = automata.getStates();
  Set<C> alphabet = automata.getAlphabet();
  TransitionFunction<S, C> transitionFunction = automata.getTransitionFunction();
  S initialState = automata.getInitialState();
  Set<S> acceptableStates = automata.getAcceptableStates();
  
   for(S state : automata.epsilonClosure(initialState)){
     if(automata.hasAcceptableState(state)){
       acceptableStates.add(initialState);
     }
   }

   
    
    
    Automaton<S, C> nfaAutomata = new Automaton<S, C>(states, alphabet, transitionFunction, initialState, acceptableStates);
    
    return nfaAutomata;
    
  }
}
