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
public class Automaton<S, C extends ISymbol> {
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

  public Collection<S> epsilonClosure(final S state) {
    Set<S> epsilonClosure = new HashSet<>();
    Queue<S> queue = new LinkedList<>();
    
    epsilonClosure.add(state);
    queue.add(state);
    while (!queue.isEmpty()) {
      S head = queue.poll();
      if (transitionFunction.getTransitionSymbols(head) == null) {
        continue;
      }
      for (C symbol : transitionFunction.getTransitionSymbols(head)) {
        if (!symbol.isEpsilonSymbol()) {
          continue;
        }
        for (S neighbour : transitionFunction.getTransitionStates(head, symbol)) {
          if (!epsilonClosure.contains(neighbour)) {
            queue.add(neighbour);
            epsilonClosure.add(neighbour);
          }
        }
      }
    }

    return epsilonClosure;
  }
}
