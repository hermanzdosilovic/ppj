
import static org.junit.Assert.*;

import org.junit.Test;

public class AutomatonTest {

  @Test
  public void test() {
    Automaton automaton = new Automaton();
    int[] s = new int[10];
    for(int i = 0; i < 10; i++) {
      s[i] = automaton.makeNewState();
    }
    automaton.setStartState(s[1]);
    automaton.setAcceptState(s[8]);
    automaton.addEpsilonTransition(s[1], s[2]);
    automaton.addEpsilonTransition(s[1], s[3]);
    automaton.addEpsilonTransition(s[2], s[4]);
    automaton.addEpsilonTransition(s[7], s[8]);
    automaton.addEpsilonTransition(s[4], s[1]);
    automaton.addTransition(s[2], s[5], 'a');
    automaton.addTransition(s[2], s[7], 'a');
    automaton.addTransition(s[3], s[7], 'c');
    automaton.addTransition(s[4], s[6], 'b');
    automaton.addTransition(s[5], s[8], 'd');
    automaton.addTransition(s[6], s[8], 'e');
    automaton.addTransition(s[8], s[9], 'm');
    
    automaton.prepareForRun();
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('b');
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('e');
    assertEquals(true, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('m');
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('z');
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(false, automaton.isAlive());
    
    automaton.prepareForRun();
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('a');
    assertEquals(true, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('d');
    assertEquals(true, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('h');
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(false, automaton.isAlive());
    
    automaton.prepareForRun();
    assertEquals(false, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
    automaton.makeTransitions('c');
    assertEquals(true, automaton.isInAcceptableState());
    assertEquals(true, automaton.isAlive());
  }

}
