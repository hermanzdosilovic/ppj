package hr.fer.zemris.ppj.automaton;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <S> the type of states of this automaton
 * @param <C> the type of input symbols which this automaton reads
 */
public class Automaton<S, C> {
  private Set<S> states;
  private Set<C> alphabet;
  private TransitionFunction<S, C> transitionFunction;
  private Set<S> initialState;
  private Set<S> currentStates;
  private Set<S> acceptableStates;
  private Set<S> reachableStates;

  public Automaton(final Collection<S> states, final Collection<C> alphabet,
      final TransitionFunction<S, C> transitionFunction, final Set<S> initialState,
      final Collection<S> acceptableStates) {
    if (states == null || alphabet == null || transitionFunction == null || initialState == null
        || acceptableStates == null) {
      throw new IllegalArgumentException("Invalid automaton definition");
    }
    this.states = new HashSet<>(states);
    this.alphabet = new HashSet<>(alphabet);
    this.transitionFunction = new TransitionFunction<S, C>(transitionFunction);
    this.initialState = new HashSet<>(initialState);
    this.acceptableStates = new HashSet<>(acceptableStates);
    this.currentStates = new HashSet<>(epsilonClosure(initialState));
  }

  public Set<S> reload() {
    currentStates = new HashSet<>(epsilonClosure(initialState));
    return new HashSet<>(currentStates);
  }

  public Set<S> getCurrentStates() {
    return new HashSet<>(currentStates);
  }

  public Set<S> read(final Collection<C> symbols) {
    for (C symbol : symbols) {
      read(symbol);
    }
    return new HashSet<>(currentStates);
  }

  public Set<S> read(final C symbol) {
    Set<S> newStates = new HashSet<>();
    for (S currentState : currentStates) {
      newStates
          .addAll(epsilonClosure(transitionFunction.getTransitionResult(currentState, symbol)));
    }
    currentStates = new HashSet<>(newStates);
    return new HashSet<>(currentStates);
  }

  public Set<S> epsilonClosure(final S state) {
    Set<S> epsilonClosure = new HashSet<>();
    Queue<S> queue = new LinkedList<>();

    epsilonClosure.add(state);
    queue.add(state);
    while (!queue.isEmpty()) {
      S head = queue.poll();
      for (S neighbour : transitionFunction.getEpsilonTransitionResult(head)) {
        if (!epsilonClosure.contains(neighbour)) {
          queue.add(neighbour);
          epsilonClosure.add(neighbour);
        }
      }
    }

    return new HashSet<>(epsilonClosure);
  }

  public Set<S> epsilonClosure(final Collection<S> states) {
    Set<S> epsilonClosure = new HashSet<>();
    for (S state : states) {
      epsilonClosure.addAll(epsilonClosure(state));
    }
    return new HashSet<>(epsilonClosure);
  }

  public TransitionFunction<S, C> getTransitionFunction() {
    return new TransitionFunction<S, C>(transitionFunction);
  }

  public Set<S> getReachableStates() {
    if (reachableStates != null) {
      return reachableStates;
    }

    reachableStates = new HashSet<>();
    Queue<S> queue = new LinkedList<>();
    queue.addAll(initialState);
    reachableStates.addAll(initialState);
    while (!queue.isEmpty()) {
      S head = queue.poll();
      for (S neighbour : transitionFunction.getDestinations(head)) {
        if (!reachableStates.contains(neighbour)) {
          queue.add(neighbour);
          reachableStates.add(neighbour);
        }
      }
    }

    return reachableStates;
  }

  public Set<S> getUnreachableStates() {
    Set<S> unreachableStates = new HashSet<>(states);
    unreachableStates.removeAll(getReachableStates());
    return unreachableStates;
  }

  public Set<S> getStates() {
    return states;
  }

  public Set<S> getAcceptableStates() {
    return acceptableStates;
  }

  public Set<S> getInitialState() {
    return initialState;
  }

  public Set<C> getAlphabet() {
    return alphabet;
  }

  public boolean hasAcceptableState(S state) {
    return acceptableStates.contains(state);
  }

  public int getNumberOfStates() {
    return states.size();
  }

  public int getNumberOfTransitions() {
    return transitionFunction.getNumberOfTransitions();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((acceptableStates == null) ? 0 : acceptableStates.hashCode());
    result = prime * result + ((initialState == null) ? 0 : initialState.hashCode());
    result = prime * result + ((states == null) ? 0 : states.hashCode());
    result = prime * result + ((transitionFunction == null) ? 0 : transitionFunction.hashCode());
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
    Automaton<?, ?> other = (Automaton<?, ?>) obj;
    if (acceptableStates == null) {
      if (other.acceptableStates != null)
        return false;
    } else if (!acceptableStates.equals(other.acceptableStates))
      return false;
    if (initialState == null) {
      if (other.initialState != null)
        return false;
    } else if (!initialState.equals(other.initialState))
      return false;
    if (states == null) {
      if (other.states != null)
        return false;
    } else if (!states.equals(other.states))
      return false;
    if (transitionFunction == null) {
      if (other.transitionFunction != null)
        return false;
    } else if (!transitionFunction.equals(other.transitionFunction))
      return false;
    return true;
  }
}
