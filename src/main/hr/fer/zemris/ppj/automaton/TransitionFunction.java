package hr.fer.zemris.ppj.automaton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.ppj.Pair;

/**
 * 
 * @author Herman Zvonimir Dosilovic
 *
 * @param <S> source/destination
 * @param <C> input symbol
 */
public class TransitionFunction<S, C> {

  private Map<Pair<S, C>, Collection<S>> transitionTable;
  
  public TransitionFunction() {
    transitionTable = new HashMap<>();
  }
  
  public boolean addTransition(S source, C input, S destination) {
    Pair<S, C> pair = new Pair<>(source, input);
    if (!transitionTable.containsKey(pair)) {
      transitionTable.put(pair, new ArrayList<>());
    }
    if (transitionTable.get(pair).contains(destination)) {
      return false;
    }
    return transitionTable.get(pair).add(destination);
  }
  
  public Collection<S> getTransitionStates(S source, C input) {
    Pair<S, C> pair = new Pair<>(source, input);
    if (!transitionTable.containsKey(pair)) {
      return null;
    }
    return transitionTable.get(pair);
  }
  
  public boolean existsTransition(S source, C input) {
    return getTransitionStates(source, input) != null;
  }
}