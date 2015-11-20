package hr.fer.zemris.ppj.automaton;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <S> source/destination
 * @param <C> input symbol
 */
public class TransitionFunction<S, C> {
  private Map<Pair<S, C>, Collection<S>> transitionTable;
  private Map<S, Collection<S>> epsilonTransitionTable;
  private Map<S, Collection<S>> neighbourTable;

  public TransitionFunction(TransitionFunction<S, C> transitionFunction) {
    TransitionFunction<S, C> copy = transitionFunction.remove(Arrays.asList());
    this.transitionTable = copy.transitionTable;
    this.epsilonTransitionTable = copy.epsilonTransitionTable;
    this.neighbourTable = copy.neighbourTable;
  }

  public TransitionFunction() {
    this.transitionTable = new HashMap<>();
    this.epsilonTransitionTable = new HashMap<>();
    this.neighbourTable = new HashMap<>();
  }

  public boolean addTransition(S source, C input, S destination) {
    Pair<S, C> pair = new Pair<>(source, input);
    if (!transitionTable.containsKey(pair)) {
      transitionTable.put(pair, new HashSet<>());
    }
    addNeighbour(source, destination);
    return transitionTable.get(pair).add(destination);
  }

  public boolean addEpsilonTransition(S source, S destination) {
    if (!epsilonTransitionTable.containsKey(source)) {
      epsilonTransitionTable.put(source, new HashSet<>());
    }
    addNeighbour(source, destination);
    return epsilonTransitionTable.get(source).add(destination);
  }

  public Collection<S> getTransitionResult(S source, C input) {
    Pair<S, C> pair = new Pair<>(source, input);
    if (!transitionTable.containsKey(pair)) {
      return new HashSet<>();
    }
    return transitionTable.get(pair);
  }

  public Collection<S> getEpsilonTransitionResult(S source) {
    if (!epsilonTransitionTable.containsKey(source)) {
      return new HashSet<>();
    }
    return epsilonTransitionTable.get(source);
  }

  public Collection<S> getDestinations(S source) {
    if (!neighbourTable.containsKey(source)) {
      return new HashSet<>();
    }
    return neighbourTable.get(source);
  }

  public boolean existsTransition(S source, C input) {
    return !getTransitionResult(source, input).isEmpty();
  }

  public boolean existsEpsilonTransition(S source, S destination) {
    return getEpsilonTransitionResult(source).contains(destination);
  }

  private void addNeighbour(S source, S destination) {
    if (!neighbourTable.containsKey(source)) {
      neighbourTable.put(source, new HashSet<>());
    }
    neighbourTable.get(source).add(destination);
  }

  public TransitionFunction<S, C> remove(final S source) {
    return remove(Arrays.asList(source));
  }

  public TransitionFunction<S, C> remove(final Collection<S> sources) {
    TransitionFunction<S, C> newTransitionFunction = new TransitionFunction<>();
    for (Pair<S, C> pair : transitionTable.keySet()) {
      S source = pair.getFirst();
      C input = pair.getSecond();
      for (S destination : transitionTable.get(pair)) {
        if (!sources.contains(source) && !sources.contains(destination)) {
          newTransitionFunction.addTransition(source, input, destination);
        }
      }
    }

    for (S source : epsilonTransitionTable.keySet()) {
      for (S destination : epsilonTransitionTable.get(source)) {
        if (!sources.contains(source) && !sources.contains(destination)) {
          newTransitionFunction.addEpsilonTransition(source, destination);
        }
      }
    }
    return newTransitionFunction;
  }

  public Collection<S> getAllSources() {
    Set<S> sources = new HashSet<>();
    for (S source : neighbourTable.keySet()) {
      sources.add(source);
      sources.addAll(neighbourTable.get(source));
    }
    return sources;
  }

  public Collection<C> getAllInputSymbols() {
    Set<C> inputSymbols = new HashSet<>();
    for (Pair<S, C> pair : transitionTable.keySet()) {
      inputSymbols.add(pair.getSecond());
    }
    return inputSymbols;
  }

  public int getNumberOfTransitions() {
    int numberOfTransitions = 0;
    for (Collection<S> transitions : transitionTable.values()) {
      numberOfTransitions += transitions.size();
    }
    for (Collection<S> transitions : epsilonTransitionTable.values()) {
      numberOfTransitions += transitions.size();
    }
    return numberOfTransitions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((epsilonTransitionTable == null) ? 0 : epsilonTransitionTable.hashCode());
    result = prime * result + ((neighbourTable == null) ? 0 : neighbourTable.hashCode());
    result = prime * result + ((transitionTable == null) ? 0 : transitionTable.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    TransitionFunction<?, ?> other = (TransitionFunction<?, ?>) obj;
    if (epsilonTransitionTable == null) {
      if (other.epsilonTransitionTable != null)
        return false;
    } else if (!epsilonTransitionTable.equals(other.epsilonTransitionTable))
      return false;
    if (neighbourTable == null) {
      if (other.neighbourTable != null)
        return false;
    } else if (!neighbourTable.equals(other.neighbourTable))
      return false;
    if (transitionTable == null) {
      if (other.transitionTable != null)
        return false;
    } else if (!transitionTable.equals(other.transitionTable))
      return false;
    return true;
  }
}
