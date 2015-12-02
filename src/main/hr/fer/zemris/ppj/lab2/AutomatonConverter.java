package hr.fer.zemris.ppj.lab2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.LRState;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class AutomatonConverter {

  public static Automaton<LRState, Symbol> convertToNFA(Automaton<LRItem, Symbol> eNFA) {
    Set<LRItem> oldStates = eNFA.getStates();
    Set<Symbol> alphabet = eNFA.getAlphabet();
    TransitionFunction<LRItem, Symbol> oldTransitionFunction = eNFA.getTransitionFunction();

    Map<LRItem, LRState> itemStateTable = new HashMap<>();
    Set<LRState> newStates = new HashSet<>();
    Map<LRItem, Set<LRItem>> epsilonClosureTable = new HashMap<>();

    int label = 0;
    for (LRItem state : oldStates) {
      Set<LRItem> epsilonClosure = eNFA.epsilonClosure(state);

      LRState newState = new LRState(epsilonClosure, label++);
      newStates.add(newState);

      epsilonClosureTable.put(state, epsilonClosure);
      itemStateTable.put(state, newState);
    }

    LRState newInitialState = itemStateTable.get(eNFA.getInitialState());

    Set<LRState> newAcceptableStates = new HashSet<>();
    for (LRItem state : eNFA.getAcceptableStates()) {
      newAcceptableStates.add(itemStateTable.get(state));
      if (newInitialState.getLRItems().contains(state)) {
        newAcceptableStates.add(newInitialState);
      }
    }

    TransitionFunction<LRItem, Symbol> helperTransitionFunction = new TransitionFunction<>();
    for (LRItem state : oldStates) {
      for (LRItem epsilonState : epsilonClosureTable.get(state)) {
        for (Symbol symbol : alphabet) {
          for (LRItem destination : oldTransitionFunction.getTransitionResult(epsilonState,
              symbol)) {
            for (LRItem epsilonDestination : epsilonClosureTable.get(destination)) {
              helperTransitionFunction.addTransition(state, symbol, epsilonDestination);
            }
          }
        }
      }
    }

    TransitionFunction<LRState, Symbol> newTransitionFunction = new TransitionFunction<>();

    Map<Pair<LRItem, Symbol>, Collection<LRItem>> transitionTable =
        helperTransitionFunction.getTransitionTable();
    for (Map.Entry<Pair<LRItem, Symbol>, Collection<LRItem>> entry : transitionTable.entrySet()) {
      LRState source = itemStateTable.get(entry.getKey().getFirst());
      Symbol symbol = entry.getKey().getSecond();
      for (LRItem destination : entry.getValue()) {
        newTransitionFunction.addTransition(source, symbol, itemStateTable.get(destination));
      }
    }

    return new Automaton<>(newStates, alphabet, newTransitionFunction, newInitialState,
        newAcceptableStates);
  }

  public static Automaton<LRState, Symbol> convertToDFA(Automaton<LRState, Symbol> nfa) {
    Set<Symbol> alphabet = nfa.getAlphabet();
    
    /* old objects from NFA */
    Set<LRState> oldStates = nfa.getStates();
    Set<LRState> oldAcceptableStates = nfa.getAcceptableStates();
    TransitionFunction<LRState, Symbol> oldTransitionFunction = nfa.getTransitionFunction();
    LRState oldInitialState = nfa.getInitialState();
    
    /* mapping old LRStates with their labels */
    Map<Integer, LRState> oldLabelLRStateTable = new HashMap<>();
    for (LRState state : oldStates) {
      oldLabelLRStateTable.put(state.getLabel(), state);
    }
    
    /* finding new groups by grouping labels and not their LRItems */
    Set<Set<Integer>> newLabelStates = new HashSet<>();
    Queue<Set<Integer>> queue = new LinkedList<>();
    queue.add(new HashSet<>(Arrays.asList(oldInitialState.getLabel())));
    while (!queue.isEmpty()) {
      Set<Integer> currentLabelState = queue.remove();
      newLabelStates.add(currentLabelState);

      for (Symbol symbol : alphabet) {
        Set<Integer> newLabelState = new HashSet<>();
        for (Integer labelValue : currentLabelState) {
          for (LRState destination : oldTransitionFunction
              .getTransitionResult(oldLabelLRStateTable.get(labelValue), symbol)) {
            newLabelState.add(destination.getLabel());
          }
        }
        if (!newLabelStates.contains(newLabelState) && !newLabelState.isEmpty()) {
          queue.add(newLabelState);
        }
      }
    }
    
    /* create new LRStates from grouped labels */
    Map<Set<Integer>, LRState> newLabelStatesLRStateTable = new HashMap<>();
    int label = 0; // counter for labels of new LRStates
    for (Set<Integer> newLabelState : newLabelStates) {
      Set<LRItem> mergedItems = new HashSet<>();
      for (Integer labelValue : newLabelState) {
        mergedItems.addAll(oldLabelLRStateTable.get(labelValue).getLRItems());
      }
      newLabelStatesLRStateTable.put(newLabelState, new LRState(mergedItems, label++));
    }
    
    /* building new objects for DFA */
    LRState newInitialState =
        newLabelStatesLRStateTable.get(new HashSet<>(Arrays.asList(oldInitialState.getLabel())));
    Set<LRState> newStates = new HashSet<>(newLabelStatesLRStateTable.values());
    
    /* building new acceptable states */
    Set<LRState> newAcceptableStates = new HashSet<>();
    for (Set<Integer> newLabelState : newLabelStates) {
      for (Integer labelValue : newLabelState) {
        if (oldAcceptableStates.contains(newLabelStatesLRStateTable.get(labelValue))) {
          newAcceptableStates.add(newLabelStatesLRStateTable.get(newLabelState));
          break;
        }
      }
    }
    
    /* building new transition function */
    TransitionFunction<LRState, Symbol> newTransitionFunction = new TransitionFunction<>();
    for (Set<Integer> newLabelState : newLabelStates) {
      for (Symbol symbol : alphabet) {
        Set<Integer> nextLabelState = new HashSet<>();
        for (Integer labelValue : newLabelState) {
          for (LRState destination : oldTransitionFunction
              .getTransitionResult(oldLabelLRStateTable.get(labelValue), symbol)) {
            nextLabelState.add(destination.getLabel());
          }
        }

        newTransitionFunction.addTransition(newLabelStatesLRStateTable.get(newLabelState), symbol,
            newLabelStatesLRStateTable.get(nextLabelState));
      }
    }

    return new Automaton<LRState, Symbol>(newStates, alphabet, newTransitionFunction,
        newInitialState, newAcceptableStates);
  }
}
