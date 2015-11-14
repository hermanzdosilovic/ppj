package hr.fer.zemris.ppj.automaton.minimizers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

public class DFAMinimizerTest {

  @Test
  public void removeUnreachableStatesTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addTransition(4, 'b', 5);

    Automaton<Integer, Character> automaton = new Automaton<>(Arrays.asList(1, 2, 3, 4, 5),
        Arrays.asList('a', 'b'), transitionFunction, 1, Arrays.asList(1));

    Automaton<Integer, Character> expectedAutomaton =
        new Automaton<>(Arrays.asList(1, 2, 3), Arrays.asList('a', 'b'),
            transitionFunction.remove(Arrays.asList(4, 5)), 1, Arrays.asList(1));

    assertEquals(expectedAutomaton, DFAMinimizer.removeUnreachableStates(automaton));
  }
}
