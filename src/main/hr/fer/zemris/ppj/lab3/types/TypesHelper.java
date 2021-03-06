package hr.fer.zemris.ppj.lab3.types;

import static hr.fer.zemris.ppj.lab3.types.Char.CHAR;
import static hr.fer.zemris.ppj.lab3.types.Int.INT;
import static hr.fer.zemris.ppj.lab3.types.ConstChar.CONST_CHAR;
import static hr.fer.zemris.ppj.lab3.types.ConstInt.CONST_INT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class TypesHelper {
  
  private static Map<NumericType, List<NumericType>> transitions = new HashMap<>();
  
  static {
    transitions.put(CONST_INT, Arrays.asList(INT));
    transitions.put(CONST_CHAR, Arrays.asList(CHAR));
    transitions.put(INT, Arrays.asList(CONST_INT));
    transitions.put(CHAR, Arrays.asList(CONST_CHAR, INT));
  }
  
  public static boolean canImplicitlyCast(Type source, Type target) {
    if (source instanceof NumericType && target instanceof NumericType) {
      return canImplicitlyCast((NumericType)source, (NumericType)target);
    }
    if (source instanceof Array && target instanceof Array) {
      return canImplicitlyCast((Array) source, (Array) target);
    }
    return false;
  }
  
  public static boolean canImplicitlyCast(NumericType source, NumericType target) {
    if (source == null || target == null) {
      return false;
    }
    
    if (source == target) {
      return true;
    }
    
    Queue<Type> q = new LinkedList<>();
    Set<Type> visited = new HashSet<>();
    q.add(source);
    while(!q.isEmpty()) {
      Type current = q.remove();
      if (transitions.containsKey(current)) {
        for(Type next : transitions.get(current)) {
          if (!visited.contains(next)) {
            if (next == target) {
              return true;
            }
            visited.add(next);
            q.add(next);
          }
        }
      }
    }
    return false;
  }
  
  public static boolean canImplicitlyCast(Array source, Array target) {
    if (source == null || target == null)
      return false;
    return 
        source.getNumericType() == target.getNumericType() 
     || !isConstT((Type)source.getNumericType()) && constT((Type)source.getNumericType()) == target.getNumericType();
  }
  
  public static boolean canExplicitlyCast(Type source, Type target) {
    return isX(source) && isX(target);
  }
  
  public static ConstType constT(Type type) {
    if (isConstT(type)) {
      return (ConstType) type;
    }
    if (type == CHAR) {
      return CONST_CHAR;
    }
    if (type == INT) {
      return CONST_INT;
    }
    return null;
  }
  
  public static boolean isLType(Type type) {
    return type == CHAR || type == INT;
  }
  
  public static boolean isArray(Type type) {
    return type instanceof Array;
  }
  
  public static boolean isArrayConstT(Type type) {
    if (type instanceof Array) {
      return isConstT(((Array) type).getNumericType());
    }
    return false;
  }
  
  public static boolean isX(Type type) {
    return type instanceof NumericType;
  }
  
  public static boolean isT(Type type) {
    return type == CHAR || type == INT;
  }
  
  public static boolean isConstT(Type type) {
    return type instanceof ConstType;
  }
  
  public static boolean isFunction(Type type) {
    return type instanceof FunctionType;
  }
  
  public static boolean isVoidFunction(Type type) {
    return type instanceof VoidFunctionType;
  }
  
  public static boolean isNonVoidFunction(Type type) {
    return type instanceof NonVoidFunctionType;
  }
  
  public static Type getTFromX(NumericType type) {
    if (type == CONST_CHAR) {
      return CHAR;
    }
    if (type == CONST_INT) {
      return INT;
    } 
    return type;
  }
}
