import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Implementation of NFA. When constructed, this automaton has 0 states. States are represented by
 * integers and a new state is created when {@link #makeNewState()} is called. Starting state and
 * acceptable state have to be set. Automaton has to be prepared before running by calling
 * {@link #prepareForRun()}. This automaton can read one character at the time when
 * {@link #makeTransitions(char)} is called.
 * 
 * @author ikrpelnik
 *
 */

public class Automaton {

  private Integer numberOfStates;
  private Integer startState;
  private Integer acceptState;
  private Map<TransitionKey, Set<Integer>> transitions;
  private Map<Integer, Set<Integer>> epsilonTransitions;
  private Set<Integer> currentStates;

  public Automaton() {
    this.numberOfStates = 0;
    this.transitions = new HashMap<>();
    this.epsilonTransitions = new HashMap<>();
    this.currentStates = new HashSet<>();
  }

  /**
   * Adds a unidirectional transition between two states for a given character
   * 
   * @param from - state from which the edge should point
   * @param to - state to which the edge should point
   * @param transitionalChar - character for which the new transition should happen
   */
  public void addTransition(int from, int to, char transitionalChar) {
    TransitionKey transitionKey = new TransitionKey(from, transitionalChar);
    if (!transitions.containsKey(transitionKey)) {
      transitions.put(transitionKey, new HashSet<Integer>());
    }
    transitions.get(transitionKey).add(to);
  }

  /**
   * Adds a unidirectional epsilon transition between two states
   * 
   * @param from - state from which the edge should point
   * @param to - state to which the edge should point
   */
  public void addEpsilonTransition(int from, int to) {
    if (!epsilonTransitions.containsKey(from)) {
      epsilonTransitions.put(from, new HashSet<Integer>());
    }
    epsilonTransitions.get(from).add(to);
  }

  /**
   * Increases the number of states in automaton and returns the name of the new state.
   * 
   * @return <code>int</code> Index of the new state
   */
  public int makeNewState() {
    return numberOfStates++;
  }

  /**
   * Should be called before running this Automaton.
   */
  public void prepareForRun() {
    currentStates.clear();
    currentStates.addAll(getEpsilonClosure(startState));
  }

  /**
   * Makes transitions for give character from current state.
   * 
   * @param nextChar - character to be read by this automaton.
   */
  public void makeTransitions(char nextChar) {
    Set<Integer> newStates = new HashSet<>();
    for (int state : currentStates) {
      TransitionKey transitionKey = new TransitionKey(state, nextChar);
      if (transitions.containsKey(transitionKey)) {
        newStates.addAll(getEpsilonClosure(transitions.get(transitionKey)));
      }
    }
    currentStates = newStates;
  }

  private Set<Integer> getEpsilonClosure(Set<Integer> states) {
    Set<Integer> epsilonClosure = new HashSet<Integer>();
    for (int state : states) {
      epsilonClosure.addAll(getEpsilonClosure(state));
    }
    return epsilonClosure;
  }

  private Set<Integer> getEpsilonClosure(int state) {
    Set<Integer> epsilonClosure = new HashSet<Integer>();
    epsilonClosure.add(state);
    Queue<Integer> queue = new LinkedList<>();
    queue.add(state);
    while (!queue.isEmpty()) {
      int currState = queue.remove();
      epsilonClosure.add(currState);
      if (epsilonTransitions.containsKey(currState)) {
        for (int nextState : epsilonTransitions.get(currState)) {
          if (!epsilonClosure.contains(nextState)) {
            epsilonClosure.add(nextState);
            queue.add(nextState);
          }
        }
      }
    }
    return epsilonClosure;
  }

  /**
   * Checks if this automaton is still alive. Automaton is alive if it can still make transitions -
   * it's current state is not an empty set.
   * 
   * @return <b>true</b> - if alive. <br>
   *         <b>false</b> - otherwise.
   */
  public boolean isAlive() {
    return !currentStates.isEmpty();
  }

  /**
   * Checks if this automaton is in acceptable state.
   * 
   * @return <b>true</b> - if this automaton is in acceptable state. <br>
   *         <b>false</b> - otherwise.
   */
  public boolean isInAcceptableState() {
    return currentStates.contains(acceptState);
  }

  /**
   * Set starting state of this Automaton.
   * 
   * @param startState - starting state
   */
  public void setStartState(int startState) {
    this.startState = startState;
  }

  /**
   * Set acceptable state for this Automaton.
   * 
   * @param acceptState - acceptable state
   */
  public void setAcceptState(int acceptState) {
    this.acceptState = acceptState;
  }

  /**
   * Key made of two values used as a key in Automatons transitions map.
   * 
   * @author ikrpelnik
   *
   */
  private class TransitionKey {

    private int state;
    private char transitionalChar;

    public TransitionKey(int state, char transitionalChar) {
      this.state = state;
      this.transitionalChar = transitionalChar;
    }

    public int getState() {
      return state;
    }

    public char getTransitionalChar() {
      return transitionalChar;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + state;
      result = prime * result + transitionalChar;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TransitionKey other = (TransitionKey) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (state != other.state)
        return false;
      if (transitionalChar != other.transitionalChar)
        return false;
      return true;
    }

    private Automaton getOuterType() {
      return Automaton.this;
    }

  }
}
