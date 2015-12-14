package hr.fer.zemris.ppj.lab3.types;

import java.util.List;

public class NonVoidFunctionType extends FunctionType {

  private NumericType returnType;

  public NonVoidFunctionType() {
    super();
  }

  public NonVoidFunctionType(List<NumericType> params) {
    super(params);
  }
  
  public NonVoidFunctionType(List<NumericType> params, NumericType returnType) {
    super(params);
    this.returnType = returnType;
  }

  public NonVoidFunctionType(NumericType returnType) {
    super();
    this.returnType = returnType;
  }

  public NumericType getReturnType() {
    return returnType;
  }

  public void setReturnType(NumericType returnType) {
    this.returnType = returnType;
  }
  
}
