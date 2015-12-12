package hr.fer.zemris.ppj.lab3.types;

import java.util.List;

public class FunctionType implements Type {

  private boolean isVoid;
  private List<NumericType> params;
  private NumericType returnType;
  
  public FunctionType() {}

  public FunctionType(boolean isVoid, List<NumericType> params, NumericType returnType) {
    super();
    this.isVoid = isVoid;
    this.params = params;
    this.returnType = returnType;
  }
  
  public boolean isVoid() {
    return isVoid;
  }

  public void setVoid(boolean isVoid) {
    this.isVoid = isVoid;
  }

  public List<NumericType> getParams() {
    return params;
  }

  public void setParams(List<NumericType> params) {
    this.params = params;
  }

  public void setReturnType(NumericType returnType) {
    this.returnType = returnType;
  }

  public Type getReturnType() {
    if (isVoid) {
      return Void.VOID;
    }
    return returnType;
  }
  
}
