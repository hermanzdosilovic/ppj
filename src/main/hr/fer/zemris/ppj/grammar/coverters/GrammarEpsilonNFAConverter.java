package hr.fer.zemris.ppj.grammar.coverters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class GrammarEpsilonNFAConverter {

  public static Automaton<LRItem, Symbol<?>> convert(Grammar grammar, TerminalSymbol<?> endSymbol) {
    LRItem initialState = new LRItem(new Production(null, new ArrayList<>()), null, null);
    LRItem firstLRState = new LRItem(grammar.getInitialProduction(), 0, Arrays.asList(endSymbol));
    
    TransitionFunction<LRItem, Symbol<?>> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(initialState, firstLRState);
    
    Set<LRItem> visited = new HashSet<>();
    visited.add(firstLRState);
    buildTransitions(firstLRState, transitionFunction, grammar, visited);
    
    return null;
  }
  
  public static void buildTransitions(LRItem item, TransitionFunction<LRItem, Symbol<?>> transitionFunction, Grammar grammar, Set<LRItem> visited) {
    if (item.isComplete()) {
      return;
    }
    
    visited.add(item);
    transitionFunction.addTransition(item, item.getDotSymbol(), item.getNextLRItem());
    
    if (!visited.contains(item.getNextLRItem())) {
      buildTransitions(item.getNextLRItem(), transitionFunction, grammar, visited);
    }
    
    if (item.getDotSymbol() instanceof NonTerminalSymbol<?>) {
      NonTerminalSymbol<?> symbol = (NonTerminalSymbol<?>) item.getDotSymbol();
      List<Symbol<?>> nextSymbolSequence = item.getSymbolsAfterDotSymbol();
      Set<Symbol<?>> nextTerminalSymbols = grammar.beginsWith(nextSymbolSequence);
      if (grammar.isEmptySequence(nextSymbolSequence)) {
        nextTerminalSymbols.addAll(item.getTerminalSymbols());
      }
      for (Production production : grammar.getSymbolProductions(symbol)) {
        LRItem nextLRItem = new LRItem(production, 0, nextTerminalSymbols);
        transitionFunction.addEpsilonTransition(item, nextLRItem);
        if (!visited.contains(nextLRItem)) {
          buildTransitions(nextLRItem, transitionFunction, grammar, visited);
        }
      }
    }
  }
}
