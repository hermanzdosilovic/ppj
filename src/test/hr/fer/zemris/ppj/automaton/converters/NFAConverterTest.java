package hr.fer.zemris.ppj.automaton.converters;

import static org.junit.Assert.*;

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
  public void findInitialState(){
    Set<Set<Integer>> states = new HashSet<Set<Integer>>();
    
    states.add(new HashSet<Integer>(Arrays.asList(1, 2, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 3, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 4, 5)));
    states.add(new HashSet<Integer>(Arrays.asList(1, 2)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(1)));
    states.add(new HashSet<Integer>(Arrays.asList(2)));
    
    
    assertEquals(new HashSet<Integer>(Arrays.asList(1)), NFAConverter.findInitialState(states, new Integer(1)));

  }
}
