package hr.fer.zemris.ppj.automaton.converters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.helpers.SubsetHelper;

public class NFAConverter {

  public static <S, C> Automaton<S, C> convertToDFA(Automaton<S, C> automaton) {
    
    Set<Set<S>> newStates = SubsetHelper.getAllSubsets(automaton.getStates());
    Set<Set<S>> newAcceptibleStates = findAcceptableStates(newStates, automaton.getAcceptableStates());
    Set<S> initialState = findInitialState(newStates, automaton.getInitialState());



    return automaton;

  }
  
  static <S> Set<Set<S>> findAcceptableStates(Set<Set<S>> newStates, Set<S> acceptibleStates){
    Set<Set<S>> newAcceptibleStates = new HashSet<Set<S>>();
    
    for(Set<S> newState : newStates){
     for(S state : newState) {
       if(acceptibleStates.contains(state)){
         newAcceptibleStates.add(newState);
         break;
       }
     }
    }
    
    return newAcceptibleStates;
    
  }
  
  static <S> Set<S> findInitialState(Set<Set<S>> newStates, S initalState){
    Set<S> newInitialState = null;
    
    for(Set<S> newState : newStates) {
      if(newState.equals(new HashSet<S>(Arrays.asList(initalState)))){
       newInitialState = newState; 
       break;
      }
    }
    return newInitialState;
  }

  
}
