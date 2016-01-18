package hr.fer.zemris.ppj.lab3.types;

import java.util.List;

/**
 * Represents function which has arguments.
 * @author Herman Zvonimir Dosilovic
 */
public class NonVoidFunctionType extends FunctionType {
  /**
   * Returns new funciton with no 
   * @param params
   * @param returnType
   */
  public NonVoidFunctionType(List<Type> params, ReturnType returnType) {
    super(params, returnType);
    if (params == null || params.isEmpty()) {
      throw new IllegalArgumentException("params must be given.");
    }
  }
  
  /**
   * Creates new function with given params. This function will return <code>void</code> type.
   * @param params
   */
  public NonVoidFunctionType(List<Type> params) {
    this(params, Void.VOID);
  }
}
