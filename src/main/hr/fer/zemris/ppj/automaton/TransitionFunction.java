package hr.fer.zemris.ppj.automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import hr.fer.zemris.ppj.Pair;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <S> source/destination
 * @param <C> input symbol
 */
public class TransitionFunction<S, C> {

  private Map<Pair<S, C>, Collection<S>> transitionTable;
  private Map<S, Collection<C>> sourceSymbols;

  public TransitionFunction() {
    this.transitionTable = new HashMap<>();
    this.sourceSymbols = new HashMap<>();
  }

  public boolean addTransition(S source, C input, S destination) {
    Pair<S, C> pair = new Pair<>(source, input);
    if (!transitionTable.containsKey(pair)) {
      transitionTable.put(pair, new ArrayList<>());
    }
    
    addSymbolForSource(source, input);

    if (transitionTable.get(pair).contains(destination)) {
      return false;
    }
    return transitionTable.get(pair).add(destination);
  }

  public Collection<S> getTransitionStates(S source, C input) {
    Pair<S, C> pair = new Pair<>(source, input);
    return transitionTable.get(pair);
  }

  public Collection<C> getTransitionSymbols(S source) {
    return sourceSymbols.get(source);
  }

  public boolean existsTransition(S source, C input) {
    return getTransitionStates(source, input) != null;
  }
  
  private void addSymbolForSource(S source, C input) {
    if (!sourceSymbols.containsKey(source)) {
      sourceSymbols.put(source, new HashSet<>());
    }
    sourceSymbols.get(source).add(input);
  }
}
