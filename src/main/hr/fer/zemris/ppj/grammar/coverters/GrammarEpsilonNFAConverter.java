package hr.fer.zemris.ppj.grammar.coverters;

import java.util.Arrays;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;

public class GrammarEpsilonNFAConverter {

  public static Automaton<LRItem, Symbol<?>> convert(Grammar grammar) {
    LRItem initialState = new LRItem(new Production(null, null), null, null);
    LRItem firstLRState = new LRItem(grammar.getInitialProduction(), 0, Arrays.asList());
    
    TransitionFunction<LRItem, Symbol<?>> transitionFunction = new TransitionFunction<>();
    transitionFunction.addEpsilonTransition(initialState, firstLRState);
    buildTransitions(firstLRState, transitionFunction, grammar);
    
    return null;
  }
  
  public static void buildTransitions(LRItem item, TransitionFunction<LRItem, Symbol<?>> transitionFunction, Grammar grammar) {
    if (item.isComplete()) {
      return;
    }
    
    LRItem nextItem = item.createNextItem();
    transitionFunction.addTransition(item, item.getDotSymbol(), nextItem);
    buildTransitions(nextItem, transitionFunction, grammar);
    
    if (item.getDotSymbol() instanceof NonTerminalSymbol<?>) {
      NonTerminalSymbol<?> symbol = (NonTerminalSymbol<?>) item.getDotSymbol();
    }
  }
}
