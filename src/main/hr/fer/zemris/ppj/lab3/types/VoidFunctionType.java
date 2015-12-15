package hr.fer.zemris.ppj.lab3.types;

import java.util.List;

public class VoidFunctionType extends FunctionType {
  public VoidFunctionType() {
    super(Void.VOID);
  }

  public VoidFunctionType(List<Type> params) {
    super(params, Void.VOID);
  }
}
