package hr.fer.zemris.ppj.lab3.types;

import java.util.ArrayList;

public class VoidFunctionType extends FunctionType {
  public VoidFunctionType() {
    super(Void.VOID);
  }

  public VoidFunctionType(NumericType returnType) {
    super(new ArrayList<Type>(), returnType);
  }
}
