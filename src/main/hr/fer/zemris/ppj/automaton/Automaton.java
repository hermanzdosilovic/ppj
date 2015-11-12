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
  private TransitionFunction<S, C> transitionFunction;
  private S initialState;
  private Set<S> currentStates;
  private Set<S> acceptableStates;

  public Automaton(final Collection<S> states, final TransitionFunction<S, C> transitionFunction,
      final S initialState, final Collection<S> acceptableStates) {
    this.states = new HashSet<>(states);
    this.transitionFunction = transitionFunction;
    this.initialState = initialState;
    this.acceptableStates = new HashSet<>(states);

    this.currentStates = new HashSet<>(epsilonClosure(initialState));
  }

  public Collection<S> reload() {
    currentStates = new HashSet<>(epsilonClosure(initialState));
    return currentStates;
  }

  public Collection<S> getCurrentStates() {
    return currentStates;
  }

  public Collection<S> read(final Collection<C> symbols) {
    for (C symbol : symbols) {
      read(symbol);
    }
    return currentStates;
  }

  public Collection<S> read(final C symbol) {
    Set<S> newStates = new HashSet<>();
    for (S currentState : currentStates) {
      newStates
          .addAll(epsilonClosure(transitionFunction.getTransitionResult(currentState, symbol)));
    }
    currentStates = new HashSet<>(newStates);
    return currentStates;
  }

  public Collection<S> epsilonClosure(final S state) {
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

    return epsilonClosure;
  }

  public Collection<S> epsilonClosure(final Collection<S> states) {
    Set<S> epsilonClosure = new HashSet<>();
    for (S state : states) {
      epsilonClosure.addAll(epsilonClosure(state));
    }
    return epsilonClosure;
  }
}
