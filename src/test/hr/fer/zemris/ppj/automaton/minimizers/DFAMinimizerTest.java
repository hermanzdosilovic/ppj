package hr.fer.zemris.ppj.automaton.minimizers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.Pair;
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

  @Test
  public void getUnequalStatesByMatchingRequirement() {
    Automaton<Integer, Character> automaton = new Automaton<>(Arrays.asList(1, 2, 3),
        Arrays.asList(), new TransitionFunction<>(), 1, Arrays.asList(1, 3));
    Set<Pair<Integer, Integer>> expectedResult = new HashSet<>(
        Arrays.asList(new Pair<>(1, 2), new Pair<>(2, 1), new Pair<>(2, 3), new Pair<>(3, 2)));
    assertEquals(expectedResult, DFAMinimizer.getUnequalStatesByIdentity(automaton));
  }

  @Test
  public void markAsUnequalTest() {
    Set<Pair<Integer, Integer>> unequalStates = new HashSet<>(Arrays.asList(new Pair<>(1, 2)));
    Map<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> dependencyTable = new HashMap<>();
    dependencyTable.put(new Pair<>(1, 2),
        new HashSet<>(Arrays.asList(new Pair<>(3, 4), new Pair<>(1, 3))));
    dependencyTable.put(new Pair<>(3, 4), new HashSet<>(Arrays.asList(new Pair<>(2, 3))));

    Set<Pair<Integer, Integer>> expectedResult = new HashSet<>(
        Arrays.asList(new Pair<>(1, 2), new Pair<>(3, 4), new Pair<>(1, 3), new Pair<>(2, 3)));
    DFAMinimizer.markDependenciesAsUnequal(1, 2, unequalStates, dependencyTable);
    assertEquals(expectedResult, unequalStates);
  }

  @Test
  public void addToDependencyTableTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 1);
    transitionFunction.addTransition(1, 'b', 3);
    transitionFunction.addTransition(2, 'a', 2);
    transitionFunction.addTransition(2, 'b', 1);
    transitionFunction.addTransition(3, 'a', 2);
    transitionFunction.addTransition(3, 'b', 3);

    Map<Pair<Integer, Integer>, Set<Pair<Integer, Integer>>> dependencyTable = new HashMap<>();
    Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
    DFAMinimizer.addToDependencyTable(1, 2, dependencyTable, transitionFunction, alphabet);

    assertTrue(dependencyTable.containsKey(new Pair<>(1, 2)));
    assertEquals(1, dependencyTable.get(new Pair<>(1, 2)).size());
    assertTrue(dependencyTable.get(new Pair<>(1, 2)).contains(new Pair<>(1, 2)));

    assertTrue(dependencyTable.containsKey(new Pair<>(3, 1)));
    assertEquals(1, dependencyTable.get(new Pair<>(3, 1)).size());
    assertTrue(dependencyTable.get(new Pair<>(3, 1)).contains(new Pair<>(1, 2)));
  }

  @Test
  public void getUnequalStatesByAdvancementTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 1);
    transitionFunction.addTransition(1, 'b', 2);
    transitionFunction.addTransition(2, 'a', 2);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addTransition(3, 'a', 1);
    transitionFunction.addTransition(3, 'b', 3);
    transitionFunction.addTransition(4, 'a', 1);
    transitionFunction.addTransition(4, 'b', 3);

    Automaton<Integer, Character> automaton = new Automaton<>(Arrays.asList(1, 2, 3, 4),
        Arrays.asList('a', 'b'), transitionFunction, 1, Arrays.asList(2));
    Set<Pair<Integer, Integer>> expectedResult = new HashSet<>(Arrays.asList(new Pair<>(1, 2),
        new Pair<>(2, 1), new Pair<>(2, 3), new Pair<>(3, 2), new Pair<>(2, 4), new Pair<>(4, 2),
        new Pair<>(1, 3), new Pair<>(3, 1), new Pair<>(1, 4), new Pair<>(4, 1)));
    assertEquals(expectedResult, DFAMinimizer.getUnequalStatesByAdvancement(automaton));
  }
  
  @Test
  public void getGroupedEqualStatesTest() {
    Set<Pair<Integer, Integer>> unequalStates = new HashSet<>(Arrays.asList(new Pair<>(1, 2),
        new Pair<>(2, 1), new Pair<>(2, 3), new Pair<>(3, 2), new Pair<>(2, 4), new Pair<>(4, 2),
        new Pair<>(1, 3), new Pair<>(3, 1), new Pair<>(1, 4), new Pair<>(4, 1)));
    Set<Integer> states = new HashSet<>(Arrays.asList(1, 2, 3, 4));
    Set<Set<Integer>> expectedEqualGroups = new HashSet<>();
    expectedEqualGroups.add(new HashSet<>(Arrays.asList(3, 4)));
    assertEquals(expectedEqualGroups, DFAMinimizer.getGroupedEqualStates(states, unequalStates));
  }
}
