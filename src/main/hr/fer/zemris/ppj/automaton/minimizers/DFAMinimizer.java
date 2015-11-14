package hr.fer.zemris.ppj.automaton.minimizers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

/**
 * @author Herman Zvonimir Dosilovic
 */
public final class DFAMinimizer {

  private DFAMinimizer() {}

  public static <S, C> Automaton<S, C> minimize(Automaton<S, C> automaton) {
    Automaton<S, C> minAutomaton = removeUnreachableStates(automaton);
    Set<Pair<S, S>> unequalStates = new HashSet<>(getUnequalStates(automaton));

    return minAutomaton;
  }

  static <S, C> Automaton<S, C> removeUnreachableStates(Automaton<S, C> automaton) {
    TransitionFunction<S, C> transitionFunction =
        automaton.getTransitionFunction().remove(automaton.getUnreachableStates());
    Set<S> states = automaton.getReachableStates();
    S initialState = automaton.getInitialState();

    Set<S> acceptableStates = new HashSet<>(automaton.getAcceptableStates());
    acceptableStates.removeAll(automaton.getUnreachableStates());

    return new Automaton<>(states, automaton.getAlphabet(), transitionFunction, initialState,
        acceptableStates);
  }

  static <S, C> Set<Pair<S, S>> getUnequalStates(Automaton<S, C> automaton) {
    Set<Pair<S, S>> unequalStates = getUnequalStatesByMatchingRequirement(automaton);
    return unequalStates;
  }

  static <S, C> Set<Pair<S, S>> getUnequalStatesByMatchingRequirement(
      Automaton<S, C> automaton) {
    Set<Pair<S, S>> unequalByMatchingRequirement = new HashSet<>();
    Set<S> states = automaton.getStates();
    for (S first : states) {
      for (S second : states) {
        if (automaton.hasAcceptableState(first) != automaton.hasAcceptableState(second)) {
          unequalByMatchingRequirement.add(new Pair<>(first, second));
          unequalByMatchingRequirement.add(new Pair<>(second, first));
        }
      }
    }
    return unequalByMatchingRequirement;
  }
}
