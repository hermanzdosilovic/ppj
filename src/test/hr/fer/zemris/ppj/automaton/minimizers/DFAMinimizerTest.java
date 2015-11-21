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
  public void minimizeTest() {
    TransitionFunction<Integer, Character> firstTransitionFunction = new TransitionFunction<>();
    firstTransitionFunction.addTransition(1, 'a', 2);
    firstTransitionFunction.addTransition(1, 'b', 6);
    firstTransitionFunction.addTransition(2, 'a', 7);
    firstTransitionFunction.addTransition(2, 'b', 3);
    firstTransitionFunction.addTransition(3, 'a', 1);
    firstTransitionFunction.addTransition(3, 'b', 3);
    firstTransitionFunction.addTransition(4, 'a', 3);
    firstTransitionFunction.addTransition(4, 'b', 7);
    firstTransitionFunction.addTransition(5, 'a', 8);
    firstTransitionFunction.addTransition(5, 'b', 6);
    firstTransitionFunction.addTransition(6, 'a', 3);
    firstTransitionFunction.addTransition(6, 'b', 7);
    firstTransitionFunction.addTransition(7, 'a', 7);
    firstTransitionFunction.addTransition(7, 'b', 5);
    firstTransitionFunction.addTransition(8, 'a', 7);
    firstTransitionFunction.addTransition(8, 'b', 3);

    TransitionFunction<Set<Integer>, Character> secondTransitionFunction =
        new TransitionFunction<>();
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(1, 5)), 'a',
        new HashSet<>(Arrays.asList(2, 8)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(1, 5)), 'b',
        new HashSet<>(Arrays.asList(6)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(2, 8)), 'a',
        new HashSet<>(Arrays.asList(7)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(2, 8)), 'b',
        new HashSet<>(Arrays.asList(3)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(3)), 'a',
        new HashSet<>(Arrays.asList(1, 5)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(3)), 'b',
        new HashSet<>(Arrays.asList(3)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(6)), 'a',
        new HashSet<>(Arrays.asList(3)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(6)), 'b',
        new HashSet<>(Arrays.asList(7)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(7)), 'a',
        new HashSet<>(Arrays.asList(7)));
    secondTransitionFunction.addTransition(new HashSet<>(Arrays.asList(7)), 'b',
        new HashSet<>(Arrays.asList(1, 5)));

    Set<Integer> firstStates = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
    Set<Set<Integer>> secondStates = new HashSet<>();
    secondStates.add(new HashSet<>(Arrays.asList(1, 5)));
    secondStates.add(new HashSet<>(Arrays.asList(2, 8)));
    secondStates.add(new HashSet<>(Arrays.asList(6)));
    secondStates.add(new HashSet<>(Arrays.asList(7)));
    secondStates.add(new HashSet<>(Arrays.asList(3)));

    Set<Set<Integer>> secondAcceptableStates = new HashSet<>();
    secondAcceptableStates.add(new HashSet<>(Arrays.asList(3)));

    Automaton<Integer, Character> automaton = new Automaton<>(firstStates, Arrays.asList('a', 'b'),
        firstTransitionFunction, new HashSet<>(Arrays.asList(1)), Arrays.asList(3));
    Automaton<Set<Integer>, Character> expectedAutomaton = new Automaton<>(secondStates,
        Arrays.asList('a', 'b'), secondTransitionFunction,
        new HashSet<>(Arrays.asList(new HashSet<>(Arrays.asList(1, 5)))), secondAcceptableStates);

    Automaton<Set<Integer>, Character> actualAutomaton = DFAMinimizer.minimize(automaton);
    assertEquals(expectedAutomaton, actualAutomaton);
  }

  @Test
  public void removeUnreachableStatesTest() {
    TransitionFunction<Integer, Character> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(1, 'a', 2);
    transitionFunction.addTransition(2, 'b', 3);
    transitionFunction.addTransition(4, 'b', 5);

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList('a', 'b'), transitionFunction,
            new HashSet<>(Arrays.asList(1)), Arrays.asList(1));

    Automaton<Integer, Character> expectedAutomaton = new Automaton<>(Arrays.asList(1, 2, 3),
        Arrays.asList('a', 'b'), transitionFunction.remove(Arrays.asList(4, 5)),
        new HashSet<>(Arrays.asList(1)), Arrays.asList(1));

    assertEquals(expectedAutomaton, DFAMinimizer.removeUnreachableStates(automaton));
  }

  @Test
  public void getUnequalStatesByMatchingRequirement() {
    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3), Arrays.asList(), new TransitionFunction<>(),
            new HashSet<>(Arrays.asList(1)), Arrays.asList(1, 3));
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

    Automaton<Integer, Character> automaton =
        new Automaton<>(Arrays.asList(1, 2, 3, 4), Arrays.asList('a', 'b'), transitionFunction,
            new HashSet<>(Arrays.asList(1)), Arrays.asList(2));
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

  @Test
  public void getGroupedEqualStatesMergeTest() {
    Set<Pair<Integer, Integer>> unequalStates =
        new HashSet<>(Arrays.asList(new Pair<>(1, 2), new Pair<>(1, 3), new Pair<>(1, 4)));
    Set<Integer> states = new HashSet<>(Arrays.asList(1, 2, 3, 4));
    Set<Set<Integer>> expectedEqualGroups = new HashSet<>();
    expectedEqualGroups.add(new HashSet<>(Arrays.asList(2, 3, 4)));
    assertEquals(expectedEqualGroups, DFAMinimizer.getGroupedEqualStates(states, unequalStates));
  }

  @Test
  public void getGroupedEqualStatesMultiMergeTest() {
    Set<Pair<Integer, Integer>> unequalStates = new HashSet<>(
        Arrays.asList(new Pair<>(1, 4), new Pair<>(1, 5), new Pair<>(1, 6), new Pair<>(1, 7),
            new Pair<>(2, 4), new Pair<>(2, 5), new Pair<>(2, 6), new Pair<>(2, 7),
            new Pair<>(3, 4), new Pair<>(3, 5), new Pair<>(3, 6), new Pair<>(3, 7)));
    Set<Integer> states = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
    Set<Set<Integer>> expectedEqualGroups = new HashSet<>();
    expectedEqualGroups.add(new HashSet<>(Arrays.asList(1, 2, 3)));
    expectedEqualGroups.add(new HashSet<>(Arrays.asList(4, 5, 6, 7)));
    assertEquals(expectedEqualGroups, DFAMinimizer.getGroupedEqualStates(states, unequalStates));
  }

  @Test
  public void getNewGroupedStatesTest() {
    Set<Pair<Integer, Integer>> unequalStates =
        new HashSet<>(Arrays.asList(new Pair<>(1, 2), new Pair<>(1, 3), new Pair<>(1, 4)));
    Set<Integer> states = new HashSet<>(Arrays.asList(1, 2, 3, 4));
    Set<Set<Integer>> expectedGroups = new HashSet<>();
    expectedGroups.add(new HashSet<>(Arrays.asList(2, 3, 4)));
    expectedGroups.add(new HashSet<>(Arrays.asList(1)));
    assertEquals(expectedGroups, DFAMinimizer.getNewGroupedStates(states,
        DFAMinimizer.getGroupedEqualStates(states, unequalStates)));
  }

  @Test
  public void createNewTransitionFunction() {
    TransitionFunction<Integer, Character> oldTransitionFunction = new TransitionFunction<>();
    oldTransitionFunction.addTransition(1, 'a', 1);
    oldTransitionFunction.addTransition(1, 'b', 2);
    oldTransitionFunction.addTransition(2, 'a', 2);
    oldTransitionFunction.addTransition(2, 'b', 3);
    oldTransitionFunction.addTransition(3, 'a', 1);
    oldTransitionFunction.addTransition(3, 'b', 3);
    oldTransitionFunction.addTransition(4, 'a', 1);
    oldTransitionFunction.addTransition(4, 'b', 3);

    TransitionFunction<Set<Integer>, Character> newTransitionFunction = new TransitionFunction<>();
    newTransitionFunction.addTransition(new HashSet<>(Arrays.asList(1)), 'a',
        new HashSet<>(Arrays.asList(1)));
    newTransitionFunction.addTransition(new HashSet<>(Arrays.asList(1)), 'b',
        new HashSet<>(Arrays.asList(2)));
    newTransitionFunction.addTransition(new HashSet<>(Arrays.asList(2)), 'a',
        new HashSet<>(Arrays.asList(2)));
    newTransitionFunction.addTransition(new HashSet<>(Arrays.asList(2)), 'b',
        new HashSet<>(Arrays.asList(3, 4)));
    newTransitionFunction.addTransition(new HashSet<>(Arrays.asList(3, 4)), 'a',
        new HashSet<>(Arrays.asList(1)));
    newTransitionFunction.addTransition(new HashSet<>(Arrays.asList(3, 4)), 'b',
        new HashSet<>(Arrays.asList(3, 4)));

    Set<Set<Integer>> groupedStates = new HashSet<>();
    groupedStates.add(new HashSet<>(Arrays.asList(1)));
    groupedStates.add(new HashSet<>(Arrays.asList(2)));
    groupedStates.add(new HashSet<>(Arrays.asList(3, 4)));

    assertEquals(newTransitionFunction,
        DFAMinimizer.createNewTransitionFunction(groupedStates,
            new HashSet<>(Arrays.asList(1, 2, 3, 4)), new HashSet<>(Arrays.asList('a', 'b')),
            oldTransitionFunction));
  }

  @Test
  public void getNewAcceptableStatesTest() {
    Set<Set<Integer>> groups = new HashSet<>();
    groups.add(new HashSet<>(Arrays.asList(2, 3, 4)));
    groups.add(new HashSet<>(Arrays.asList(1)));
    groups.add(new HashSet<>(Arrays.asList(6, 7)));

    Set<Set<Integer>> expectedAcceptableStates = new HashSet<>();
    expectedAcceptableStates.add(new HashSet<>(Arrays.asList(1)));
    expectedAcceptableStates.add(new HashSet<>(Arrays.asList(6, 7)));

    assertEquals(expectedAcceptableStates,
        DFAMinimizer.getNewAcceptableStates(groups, new HashSet<>(Arrays.asList(1, 6))));
  }

  @Test
  public void getNewInitialStateTest() {
    Set<Set<Integer>> groups = new HashSet<>();
    groups.add(new HashSet<>(Arrays.asList(2, 3, 4)));
    groups.add(new HashSet<>(Arrays.asList(1)));
    groups.add(new HashSet<>(Arrays.asList(6, 7)));

    assertEquals(new HashSet<>(Arrays.asList(new HashSet<>(Arrays.asList(6, 7)))),
        DFAMinimizer.getNewInitialState(groups, new HashSet<>(Arrays.asList(6))));
    assertEquals(new HashSet<>(Arrays.asList(new HashSet<>(Arrays.asList(1)))),
        DFAMinimizer.getNewInitialState(groups, new HashSet<>(Arrays.asList(1))));
  }
}
