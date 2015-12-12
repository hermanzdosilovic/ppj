package hr.fer.zemris.ppj.lab3.types;

import static hr.fer.zemris.ppj.lab3.types.Char.CHAR;
import static hr.fer.zemris.ppj.lab3.types.Int.INT;
import static hr.fer.zemris.ppj.lab3.types.ConstChar.CONST_CHAR;
import static hr.fer.zemris.ppj.lab3.types.ConstInt.CONST_INT;
import static hr.fer.zemris.ppj.lab3.types.Array.CHAR_ARRAY;
import static hr.fer.zemris.ppj.lab3.types.Array.INT_ARRAY;
import static hr.fer.zemris.ppj.lab3.types.Array.CONST_CHAR_ARRAY;
import static hr.fer.zemris.ppj.lab3.types.Array.CONST_INT_ARRAY;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class TypesHelper {
  
  private static Map<Type, List<Type>> transitions = new HashMap<Type, List<Type>>();
  
  static {
    transitions.put(CONST_INT, Arrays.asList(INT));
    transitions.put(CONST_CHAR, Arrays.asList(CHAR));
    transitions.put(INT, Arrays.asList(CONST_INT));
    transitions.put(CHAR, Arrays.asList(CONST_CHAR, INT));
    transitions.put(INT_ARRAY, Arrays.asList(CONST_INT_ARRAY));
    transitions.put(CHAR_ARRAY, Arrays.asList(CONST_CHAR_ARRAY));
  }
  
  public static boolean canImplicitlyConvert(Type source, Type target) {
    Queue<Type> q = new LinkedList<>();
    Set<Type> visited = new HashSet<>();
    q.add(source);
    while(!q.isEmpty()) {
      Type current = q.remove();
      if (current == target) {
        return true;
      }
      if (transitions.containsKey(current)) {
        for(Type next : transitions.get(current)) {
          if (!visited.contains(next)) {
            visited.add(next);
            q.add(next);
          }
        }
      }
    }
    return false;
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
}
