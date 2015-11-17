package hr.fer.zemris.ppj.lab2.parser.action;

/**
 * Represents a move action for LR parser. It signals the LR parser
 * that it should move it's state to the state that's held by this class.
 * @author ikrpelnik
 *
 */
public class MoveAction implements Action {

  private static final long serialVersionUID = 5192614674806549599L;
  
  /**
   * State to which the parser should move the DFA
   */
  private int state;
  
  public MoveAction() {
  }
  
  /**
   * Takes a state in which the LR parser should move it's DFA.
   * @param state
   */
  public MoveAction(int state) {
    this.state = state;
  }
  
  /**
   * Sets the state in which the LR parser should move it's DFA.
   * @param state
   */
  public void setState(int state) {
    this.state = state;
  }
  
  /**
   * Returns the state in which the LR parser should move it's DFA.
   * @return
   */
  public int getState() {
    return state;
  }
  
}
