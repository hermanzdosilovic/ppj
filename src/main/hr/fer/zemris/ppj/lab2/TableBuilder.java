package hr.fer.zemris.ppj.lab2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class TableBuilder {
  public static Map<Pair<Set<LRItem>, TerminalSymbol<?>>, Action> buildActionTable(Automaton<Set<LRItem>, Symbol<?>> automaton, Production initialProduction) {
    TransitionFunction<Set<LRItem>, Symbol<?>> transitionFunction = automaton.getTransitionFunction();
    
    Map<Pair<Set<LRItem>, TerminalSymbol<?>>, Action> actionTable = new HashMap<>();
    for (Set<LRItem> state : automaton.getStates()) {
      for (LRItem item : state) {
        Symbol<?> symbol = item.getDotSymbol();
        
        if (!item.isComplete() && transitionFunction.existsTransition(state, symbol) && (symbol instanceof TerminalSymbol<?>)) {
          Set<LRItem> destination = new ArrayList<>(transitionFunction.getTransitionResult(state, symbol)).get(0);
          actionTable.put(new Pair<>(state, (TerminalSymbol<?>) symbol), new MoveAction<>(destination));
        } else if (item.isComplete() && !item.getProduction().equals(initialProduction)) {
          for (Symbol<?> itemSymbol : item.getTerminalSymbols()) {
            actionTable.put(new Pair<>(state, (TerminalSymbol<?>) itemSymbol), new ReduceAction(item.getProduction()));
          }
        } else if (item.getProduction().equals(initialProduction)) {
          actionTable.put(new Pair<>(state, (TerminalSymbol<?>) new ArrayList<>(item.getTerminalSymbols()).get(0)), new AcceptAction());
        }
      }
    }
    
    return actionTable;
  }
}
