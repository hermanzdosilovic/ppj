package hr.fer.zemris.ppj.node;

public class SNode extends Node {

  private boolean lValue;
  
  public SNode(String value) {
    super(value);
  }

  public void setlValue(boolean lValue) {
    this.lValue = lValue;
  }

  public boolean islValue() {
    return lValue;
  }
  
  
}
