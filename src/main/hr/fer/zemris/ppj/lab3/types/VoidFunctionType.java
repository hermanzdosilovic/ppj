package hr.fer.zemris.ppj.lab3.types;

import java.util.ArrayList;

/**
 * Represents void function. Function which does not have any arguments.
 * @author Herman Zvonimir Dosilovic
 */
public class VoidFunctionType extends FunctionType {
  /**
   * Create new function with <code>void</code> as return type.
   */
  public VoidFunctionType() {
    super(Void.VOID);
  }
  
  /**
   * Create new function with specified return type.
   * @param returnType specified return type
   */
  public VoidFunctionType(ReturnType returnType) {
    super(new ArrayList<Type>(), returnType);
  }
}
