package hr.fer.zemris.ppj.lab3.types;

import java.util.List;

public class NonVoidFunctionType extends FunctionType {
  public NonVoidFunctionType() {
    super();
  }

  public NonVoidFunctionType(List<Type> params, NumericType returnType) {
    super(params, returnType);
  }

  public NonVoidFunctionType(NumericType returnType) {
    super(returnType);
  }
}
