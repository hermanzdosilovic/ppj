package hr.fer.zemris.ppj.lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.PutAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class TableBuilder {
  public static Map<Pair<Set<Set<LRItem>>, TerminalSymbol>, Action> buildActionTable(
      Automaton<Set<Set<LRItem>>, Symbol> automaton, LRItem initialCompleteLRItem) {
    TransitionFunction<Set<Set<LRItem>>, Symbol> transitionFunction =
        automaton.getTransitionFunction();

    Map<Pair<Set<Set<LRItem>>, TerminalSymbol>, Action> actionTable = new HashMap<>();
    for (Set<Set<LRItem>> state : automaton.getStates()) {
      for (Set<LRItem> stateComponent : state) {
        if (stateComponent.contains(initialCompleteLRItem)) {
          actionTable.put(new Pair<>(state,
              (TerminalSymbol) new ArrayList<>(initialCompleteLRItem.getTerminalSymbols()).get(0)),
              new AcceptAction());
          continue;
        }

        for (LRItem item : stateComponent) {
          Symbol symbol = item.getDotSymbol();

          if (!item.isComplete() && transitionFunction.existsTransition(state, symbol)
              && (symbol instanceof TerminalSymbol)) {
            Set<Set<LRItem>> destination =
                new ArrayList<>(transitionFunction.getTransitionResult(state, symbol)).get(0);
            actionTable.put(new Pair<>(state, (TerminalSymbol) symbol),
                new MoveAction<>(destination));
          } else if (item.isComplete()) {
            for (Symbol itemSymbol : item.getTerminalSymbols()) {
              actionTable.put(new Pair<>(state, (TerminalSymbol) itemSymbol),
                  new ReduceAction(item.getProduction()));
            }
          }
        }
      }
    }

    return actionTable;
  }

  public static Map<Pair<Set<Set<LRItem>>, NonTerminalSymbol>, Action> buildNewStateTable(
      Automaton<Set<Set<LRItem>>, Symbol> automaton) {
    TransitionFunction<Set<Set<LRItem>>, Symbol> transitionFunction =
        automaton.getTransitionFunction();
    Set<Set<Set<LRItem>>> states = automaton.getStates();
    Set<Symbol> alphabet = automaton.getAlphabet();

    Map<Pair<Set<Set<LRItem>>, NonTerminalSymbol>, Action> newStateTable = new HashMap<>();
    for (Set<Set<LRItem>> state : states) {
      for (Symbol symbol : alphabet) {
        if (transitionFunction.existsTransition(state, symbol)
            && (symbol instanceof NonTerminalSymbol)) {
          Set<Set<LRItem>> destination =
              new ArrayList<>(transitionFunction.getTransitionResult(state, symbol)).get(0);
          newStateTable.put(new Pair<>(state, (NonTerminalSymbol) symbol),
              new PutAction<>(destination));
        }
      }
    }

    return newStateTable;
  }
}
