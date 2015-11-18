package hr.fer.zemris.ppj.lab2.parser.action;

public class MoveAction implements Action {

  private static final long serialVersionUID = 5192614674806549599L;
  
  /**
   * State to which the parser should move the DFA
   */
  private int state;
  
  public MoveAction() {
  }
  
  public MoveAction(int state) {
    this.state = state;
  }
  
  public void setState(int state) {
    this.state = state;
  }
  
  public int getState() {
    return state;
  }
  
}
