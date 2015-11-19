package hr.fer.zemris.ppj.automaton.converters;

import static org.junit.Assert.*;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class NFAConverterTest {

  @Test
  public void findAcceptableStatesTest() {
    Set<Integer> acceptibleStates = new HashSet<>();
    acceptibleStates.add(1);

    Set<Set<Integer>> states = new HashSet<Set<Integer>>();
    states.add(new HashSet<Integer>(Arrays.asList(0, 3, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 5, 7)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 4, 5)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 1, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 5, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 2, 1)));
    states.add(new HashSet<Integer>(Arrays.asList(4, 6, 5)));

    Set<Set<Integer>> expectedStates = new HashSet<Set<Integer>>();
    expectedStates.add(new HashSet<Integer>(Arrays.asList(2, 1, 3)));
    expectedStates.add(new HashSet<Integer>(Arrays.asList(3, 2, 1)));

    assertEquals(expectedStates, NFAConverter.findAcceptableStates(states, acceptibleStates));
  }

  @Test
  public void findInitialState() {
    Set<Set<Integer>> states = new HashSet<Set<Integer>>();
    states.add(new HashSet<Integer>(Arrays.asList(1, 2, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 3, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 4, 5)));
    states.add(new HashSet<Integer>(Arrays.asList(1, 2)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(1)));
    states.add(new HashSet<Integer>(Arrays.asList(2)));

    assertEquals(new HashSet<Integer>(Arrays.asList(1)),
        NFAConverter.findInitialState(states, new Integer(1)));
  }

  @Test
  public void findTransitionsTest() {
    TransitionFunction<Integer, Integer> nkaTransitionFunction =
        new TransitionFunction<Integer, Integer>();
    nkaTransitionFunction.addTransition(0, 0, 0);
    nkaTransitionFunction.addTransition(0, 0, 1);
    nkaTransitionFunction.addTransition(0, 1, 1);
    nkaTransitionFunction.addTransition(1, 1, 0);
    nkaTransitionFunction.addTransition(1, 1, 1);

    TransitionFunction<Set<Integer>, Integer> dkaTransitionFunction =
        new TransitionFunction<Set<Integer>, Integer>();
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 1,
        new HashSet<Integer>(Arrays.asList(1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(1)), 0,
        new HashSet<Integer>(Arrays.asList()));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList()), 0,
        new HashSet<Integer>(Arrays.asList()));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList()), 1,
        new HashSet<Integer>(Arrays.asList()));

    Set<Set<Integer>> dkaStates = new HashSet<Set<Integer>>();
    dkaStates.add(new HashSet<Integer>(Arrays.asList()));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(1)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0, 1)));

    assertEquals(
        dkaTransitionFunction,
        NFAConverter.findTransitions(nkaTransitionFunction, dkaStates,
            new HashSet<Integer>(Arrays.asList(0, 1))));
  }

  @Test
  public void nfaConverterTest() {
    TransitionFunction<Integer, Integer> nkaTransitionFunction =
        new TransitionFunction<Integer, Integer>();
    nkaTransitionFunction.addTransition(0, 0, 0);
    nkaTransitionFunction.addTransition(0, 0, 1);
    nkaTransitionFunction.addTransition(0, 1, 1);
    nkaTransitionFunction.addTransition(1, 1, 0);
    nkaTransitionFunction.addTransition(1, 1, 1);

    TransitionFunction<Set<Integer>, Integer> dkaTransitionFunction =
        new TransitionFunction<Set<Integer>, Integer>();
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 1,
        new HashSet<Integer>(Arrays.asList(1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(1)), 0,
        new HashSet<Integer>(Arrays.asList()));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList()), 0,
        new HashSet<Integer>(Arrays.asList()));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList()), 1,
        new HashSet<Integer>(Arrays.asList()));

    Set<Set<Integer>> dkaStates = new HashSet<Set<Integer>>();
    dkaStates.add(new HashSet<Integer>(Arrays.asList()));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(1)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0, 1)));

    Set<Set<Integer>> dkaAcceptableStates = new HashSet<Set<Integer>>();
    dkaAcceptableStates.add(new HashSet<Integer>(Arrays.asList(1)));
    dkaAcceptableStates.add(new HashSet<Integer>(Arrays.asList(0, 1)));

    Automaton<Integer, Integer> nka =
        new Automaton<Integer, Integer>(Arrays.asList(0, 1), Arrays.asList(0, 1),
            nkaTransitionFunction, 0, Arrays.asList(1));
    Automaton<Set<Integer>, Integer> dka = NFAConverter.convertToDFA(nka);
    Automaton<Set<Integer>, Integer> expectedDKA =
        new Automaton<Set<Integer>, Integer>(dkaStates, Arrays.asList(0, 1), dkaTransitionFunction,
            new HashSet<Integer>(Arrays.asList(0)), dkaAcceptableStates);
    
    assertEquals(expectedDKA, dka);
  }
}
