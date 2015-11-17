package hr.fer.zemris.ppj.lab2.parser.action;

/**
 * Represents a reduce action for LR parser. It should be able to answer how
 * many characters should the LR parser take off it's stack and what's the left hand side
 * of the production that is being used for this reduction.
 * @author ikrpelnik
 *
 */
public class ReduceAction implements Action {

  private static final long serialVersionUID = -6928861363927054533L;

  /**
   * How many characters should be taken of the stack of LR parser
   */
  private int howMany;
  
  /**
   * Left hand side of the production that should be used
   */
  private String leftHandSide;
  
  public ReduceAction() {
  }
  
  public ReduceAction(int howMany, String leftHandSide) {
    this.howMany = howMany;
    this.leftHandSide = leftHandSide;
  }
  
  public void setHowMany(int howMany) {
    this.howMany = howMany;
  }
  
  public void setLeftHandSide(String leftHandSide) {
    this.leftHandSide = leftHandSide;
  }
  
  public int getHowMany() {
    return howMany;
  }
  
  public String getLeftHandSide() {
    return leftHandSide;
  }
  
}
