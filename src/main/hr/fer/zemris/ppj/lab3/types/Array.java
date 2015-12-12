package hr.fer.zemris.ppj.lab3.types;

import static hr.fer.zemris.ppj.lab3.types.Char.CHAR;
import static hr.fer.zemris.ppj.lab3.types.Int.INT;
import static hr.fer.zemris.ppj.lab3.types.ConstChar.CONST_CHAR;
import static hr.fer.zemris.ppj.lab3.types.ConstInt.CONST_INT;


public class Array implements Type {

  public static Array INT_ARRAY = new Array(INT);
  public static Array CHAR_ARRAY = new Array(CHAR);
  public static Array CONST_INT_ARRAY = new Array(CONST_INT);
  public static Array CONST_CHAR_ARRAY = new Array(CONST_CHAR); 
  
  private NumericType numericType;
  
  private Array(NumericType numericType) {
    super();
    this.numericType = numericType;
  }

  public NumericType getNumericType() {
    return numericType;
  }
  
  public Array getInstance(NumericType numericType) {
    if (numericType == INT) {
      return INT_ARRAY;
    }
    if (numericType == CHAR) {
      return CHAR_ARRAY;
    }
    if (numericType == CONST_INT) {
      return CONST_INT_ARRAY;
    }
    if (numericType == CONST_CHAR) {
      return CONST_CHAR_ARRAY;
    }
    return null;
  }
}
