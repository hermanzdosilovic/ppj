package hr.fer.zemris.ppj.automaton.helpers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.helpers.SubsetHelper;

public class SubsetHelperTest {
  
  @Test
  public void getAllSubsetsTest() {
    Set<Set<Integer>> actualSubsets = SubsetHelper.getAllSubsets(Arrays.asList(1, 2, 3));
    Set<Set<Integer>> expectedSubsets = new HashSet<>();
    expectedSubsets.add(new HashSet<>(Arrays.asList()));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(2)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 2)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(3)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 3)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(2, 3)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 2, 3)));
    
    assertEquals(expectedSubsets, actualSubsets);
  }
  
  @Test
  public void getAllSubsetsDuplicateTest() {
    Set<Set<Integer>> actualSubsets = SubsetHelper.getAllSubsets(Arrays.asList(1, 1, 3));
    Set<Set<Integer>> expectedSubsets = new HashSet<>();
    expectedSubsets.add(new HashSet<>(Arrays.asList()));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 1)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(3)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 3)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 3)));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1, 1, 3)));
    
    assertEquals(expectedSubsets, actualSubsets);
  }
  
  @Test
  public void getAllSubsetsEmptySetTest() {
    Set<Set<Integer>> actualSubsets = SubsetHelper.getAllSubsets(Arrays.asList());
    Set<Set<Integer>> expectedSubsets = new HashSet<>();
    expectedSubsets.add(new HashSet<>(Arrays.asList()));
    
    assertEquals(expectedSubsets, actualSubsets);
  }
  
  @Test
  public void getAllSubsetsOneElementSetTest() {
    Set<Set<Integer>> actualSubsets = SubsetHelper.getAllSubsets(Arrays.asList(1));
    Set<Set<Integer>> expectedSubsets = new HashSet<>();
    expectedSubsets.add(new HashSet<>(Arrays.asList()));
    expectedSubsets.add(new HashSet<>(Arrays.asList(1)));
    
    assertEquals(expectedSubsets, actualSubsets);
  }
}
