package hr.fer.zemris.ppj.lab3.types;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionType implements Type {
  private Type returnType;
  private List<Type> params;
  
  public FunctionType() {
    this(Void.VOID);
  }
  
  public FunctionType(Type returnType) {
    this(new ArrayList<Type>(), returnType);
  }
  
  public FunctionType(List<Type> params, Type returnType) {
    this.returnType = returnType;
    this.params = params;
  }

  public Type getReturnType() {
    return returnType;
  }

  public void setReturnType(Type returnType) {
    this.returnType = returnType;
  }

  public List<Type> getParams() {
    return params;
  }

  public void setParams(List<Type> params) {
    this.params = params;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((params == null) ? 0 : params.hashCode());
    result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
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
    FunctionType other = (FunctionType) obj;
    if (params == null) {
      if (other.params != null)
        return false;
    } else if (!params.equals(other.params))
      return false;
    if (returnType == null) {
      if (other.returnType != null)
        return false;
    } else if (!returnType.equals(other.returnType))
      return false;
    return true;
  }
}
