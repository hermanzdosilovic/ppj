package hr.fer.zemris.ppj.grammar.converters;

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

/**
 * @author Herman Zvonimir Dosilovic
 */
public class GrammarEpsilonNFAConverter {
  public static Automaton<LRItem, Symbol> convert(Grammar grammar, TerminalSymbol endSymbol) {
    LRItem firstLRState = new LRItem(grammar.getInitialProduction(), 0, Arrays.asList(endSymbol));

    TransitionFunction<LRItem, Symbol> transitionFunction = new TransitionFunction<>();

    Set<LRItem> visited = new HashSet<>();
    visited.add(firstLRState);
    buildTransitions(firstLRState, transitionFunction, grammar, visited);

    Set<LRItem> states = new HashSet<>(transitionFunction.getAllSources());
    Set<LRItem> acceptableStates = new HashSet<>(states);

    Set<Symbol> alphabet = new HashSet<>(transitionFunction.getAllInputSymbols());

    return new Automaton<>(states, alphabet, transitionFunction, new HashSet<>(Arrays.asList(firstLRState)), acceptableStates);
  }

  static void buildTransitions(LRItem item, TransitionFunction<LRItem, Symbol> transitionFunction,
      Grammar grammar, Set<LRItem> visited) {
    if (item.isComplete()) {
      return;
    }

    visited.add(item);
    transitionFunction.addTransition(item, item.getDotSymbol(), item.getNextLRItem());

    if (!visited.contains(item.getNextLRItem())) {
      buildTransitions(item.getNextLRItem(), transitionFunction, grammar, visited);
    }

    if (item.getDotSymbol() instanceof NonTerminalSymbol) {
      NonTerminalSymbol symbol = (NonTerminalSymbol) item.getDotSymbol();
      List<Symbol> nextSymbolSequence = item.getSymbolsAfterDotSymbol();
      Set<Symbol> nextTerminalSymbols = grammar.beginsWith(nextSymbolSequence);
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
