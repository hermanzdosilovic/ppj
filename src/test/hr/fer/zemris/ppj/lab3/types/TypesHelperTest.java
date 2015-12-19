package hr.fer.zemris.ppj.lab3.types;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TypesHelperTest {

  @Test
  public void implicitCharToChar() {
    assertEquals(true, TypesHelper.canImplicitlyCast(Char.CHAR, Char.CHAR));
  }

  @Test
  public void implicitCharToInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(Char.CHAR, Int.INT));
  }

  @Test
  public void implicitCharToConstChar() {
    assertEquals(true, TypesHelper.canImplicitlyCast(Char.CHAR, ConstChar.CONST_CHAR));
  }

  @Test
  public void implicitCharToConstInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(Char.CHAR, ConstInt.CONST_INT));
  }

  @Test
  public void implicitCharToArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Char.CHAR, new Array(Char.CHAR)));
  }

  @Test
  public void implicitCharToVoidFunctionType() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Char.CHAR, new VoidFunctionType()));
  }
<<<<<<< HEAD

  @Test
  public void implicitCharToNonVoidFunctionType() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(Char.CHAR, new NonVoidFunctionType(Arrays.asList(Char.CHAR))));
  }

=======
  
//  @Test
//  public void implicitCharToNonVoidFunctionType() {
//    assertEquals(false, TypesHelper.canImplicitlyCast(Char.CHAR, new NonVoidFunctionType()));
//  }
  
>>>>>>> develop
  @Test
  public void implicitCharToVoid() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Char.CHAR, Void.VOID));
  }

  @Test
  public void implicitIntToChar() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, Char.CHAR));
  }

  @Test
  public void implicitIntToInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(Int.INT, Int.INT));
  }

  @Test
  public void implicitIntToConstChar() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, ConstChar.CONST_CHAR));
  }

  @Test
  public void implicitIntToConstInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(Int.INT, ConstInt.CONST_INT));
  }

  @Test
  public void implicitIntToArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, new Array(Char.CHAR)));
  }

  @Test
  public void implicitIntToVoidFunctionType() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, new VoidFunctionType()));
  }
<<<<<<< HEAD

  @Test
  public void implicitIntToNonVoidFunctionType() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(Int.INT, new NonVoidFunctionType(Arrays.asList(Int.INT))));
  }

=======
  
//  @Test
//  public void implicitIntToNonVoidFunctionType() {
//    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, new NonVoidFunctionType()));
//  }
  
>>>>>>> develop
  @Test
  public void implicitIntToVoid() {
    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, Void.VOID));
  }

  @Test
  public void implicitConstCharToChar() {
    assertEquals(true, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, Char.CHAR));
  }

  @Test
  public void implicitConstCharToInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, Int.INT));
  }

  @Test
  public void implicitConstCharToConstChar() {
    assertEquals(true, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, ConstChar.CONST_CHAR));
  }

  @Test
  public void implicitConstCharToConstInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, ConstInt.CONST_INT));
  }

  @Test
  public void implicitConstCharToArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, new Array(Char.CHAR)));
  }

  @Test
  public void implicitConstCharToVoidFunctionType() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, new VoidFunctionType()));
  }
<<<<<<< HEAD

  @Test
  public void implicitConstCharToNonVoidFunctionType() {
    assertEquals(
        false,
        TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR,
            new NonVoidFunctionType(Arrays.asList(ConstChar.CONST_CHAR))));
  }

=======
  
//  @Test
//  public void implicitConstCharToNonVoidFunctionType() {
//    assertEquals(false, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, new NonVoidFunctionType()));
//  }
  
>>>>>>> develop
  @Test
  public void implicitConstCharToVoid() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstChar.CONST_CHAR, Void.VOID));
  }

  @Test
  public void implicitConstIntToChar() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, Char.CHAR));
  }

  @Test
  public void implicitConstIntToInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, Int.INT));
  }

  @Test
  public void implicitConstIntToConstChar() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, ConstChar.CONST_CHAR));
  }

  @Test
  public void implicitConstIntToConstInt() {
    assertEquals(true, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, ConstInt.CONST_INT));
  }

  @Test
  public void implicitConstIntToArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, new Array(Char.CHAR)));
  }

  @Test
  public void implicitConstIntToVoidFunctionType() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, new VoidFunctionType()));
  }
<<<<<<< HEAD

  @Test
  public void implicitConstIntToNonVoidFunctionType() {
    assertEquals(
        false,
        TypesHelper.canImplicitlyCast(ConstInt.CONST_INT,
            new NonVoidFunctionType(Arrays.asList(ConstInt.CONST_INT))));
  }

=======
  
//  @Test
//  public void implicitConstIntToNonVoidFunctionType() {
//    assertEquals(false, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, new NonVoidFunctionType()));
//  }
//  
>>>>>>> develop
  @Test
  public void implicitConstIntToVoid() {
    assertEquals(false, TypesHelper.canImplicitlyCast(ConstInt.CONST_INT, Void.VOID));
  }

  @Test
  public void implicitCharArrayToConstCharArray() {
    assertEquals(true,
        TypesHelper.canImplicitlyCast(new Array(Char.CHAR), new Array(ConstChar.CONST_CHAR)));
  }

  @Test
  public void implicitCharArrayToCharArray() {
    assertEquals(true, TypesHelper.canImplicitlyCast(new Array(Char.CHAR), new Array(Char.CHAR)));
  }

  @Test
  public void implicitCharArrayToConstIntArray() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(new Array(Char.CHAR), new Array(ConstInt.CONST_INT)));
  }

  @Test
  public void implicitCharArrayToIntArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(new Array(Char.CHAR), new Array(Int.INT)));
  }

  @Test
  public void implicitIntArrayToConstCharArray() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(new Array(Int.INT), new Array(ConstChar.CONST_CHAR)));
  }

  @Test
  public void implicitIntArrayToCharArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(new Array(Int.INT), new Array(Char.CHAR)));
  }

  @Test
  public void implicitIntArrayToConstIntArray() {
    assertEquals(true,
        TypesHelper.canImplicitlyCast(new Array(Int.INT), new Array(ConstInt.CONST_INT)));
  }

  @Test
  public void implicitIntArrayToIntArray() {
    assertEquals(true, TypesHelper.canImplicitlyCast(new Array(Int.INT), new Array(Int.INT)));
  }

  @Test
  public void implicitConstIntArrayToConstCharArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(new Array(ConstInt.CONST_INT), new Array(
        ConstChar.CONST_CHAR)));
  }

  @Test
  public void implicitConstIntArrayToCharArray() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(new Array(ConstInt.CONST_INT), new Array(Char.CHAR)));
  }

  @Test
  public void implicitConstIntArrayToConstIntArray() {
    assertEquals(true,
        TypesHelper.canImplicitlyCast(new Array(ConstInt.CONST_INT), new Array(ConstInt.CONST_INT)));
  }

  @Test
  public void implicitConstIntArrayToIntArray() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(new Array(ConstInt.CONST_INT), new Array(Int.INT)));
  }

  @Test
  public void implicitConstCharArrayToConstCharArray() {
    assertEquals(true, TypesHelper.canImplicitlyCast(new Array(ConstChar.CONST_CHAR), new Array(
        ConstChar.CONST_CHAR)));
  }

  @Test
  public void implicitConstCharArrayToCharArray() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(new Array(ConstChar.CONST_CHAR), new Array(Char.CHAR)));
  }

  @Test
  public void implicitConstCharArrayToConstIntArray() {
    assertEquals(false, TypesHelper.canImplicitlyCast(new Array(ConstChar.CONST_CHAR), new Array(
        ConstInt.CONST_INT)));
  }

  @Test
  public void implicitConstCharArrayToIntArray() {
    assertEquals(false,
        TypesHelper.canImplicitlyCast(new Array(ConstChar.CONST_CHAR), new Array(Int.INT)));
  }
  
  @Test
  public void implicitCastNull() {
    Type type1 = null;
    Type type2 = null;
    assertEquals(false, TypesHelper.canImplicitlyCast(type1, type2));
    assertEquals(false, TypesHelper.canImplicitlyCast(Int.INT, null));
    assertEquals(false, TypesHelper.canImplicitlyCast(null, Int.INT));
    assertEquals(false, TypesHelper.canImplicitlyCast(new Array(Int.INT), null));
    assertEquals(false, TypesHelper.canImplicitlyCast(null, new Array(Int.INT)));
  }
}
