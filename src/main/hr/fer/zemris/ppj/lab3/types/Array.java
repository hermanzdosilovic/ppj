package hr.fer.zemris.ppj.lab3.types;

public class Array implements Type {
  
  private NumericType numericType;
  
  public Array(NumericType numericType) {
    super();
    this.numericType = numericType;
  }

  public NumericType getNumericType() {
    return numericType;
  }
}
