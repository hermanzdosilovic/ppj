package hr.fer.zemris.ppj.lab3.types;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionType implements Type {

  private List<NumericType> params;
  
  public FunctionType() {
    this(new ArrayList<>());
  }

  public FunctionType(List<NumericType> params) {
    super();
    this.params = params;
  }

  public List<NumericType> getParams() {
    return params;
  }

  public void setParams(List<NumericType> params) {
    this.params = params;
  }
  
}
