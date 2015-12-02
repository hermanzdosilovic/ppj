package hr.fer.zemris.ppj.lab2;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

  public Automaton<LRState, Symbol> convertToNFA(Automaton<LRItem, Symbol> eNFA) {
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
}
