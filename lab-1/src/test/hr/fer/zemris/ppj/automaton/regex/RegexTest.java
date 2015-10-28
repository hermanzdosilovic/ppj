package hr.fer.zemris.ppj.automaton.regex;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import hr.fer.zemris.ppj.regex.Regex;

public class RegexTest {
  
  @Test
  public void isOperatorTest() {
    //                       012-3-456-7-8-901-2-3-4-56
    Regex regex = new Regex("a|b\\\\cd\\\\\\ef\\\\\\\\|"); // a|b\\cd\\\|f\\\\|
    assertFalse(regex.escapedCharacterAt(0)); // a
    assertFalse(regex.escapedCharacterAt(1)); // |
    assertFalse(regex.escapedCharacterAt(2)); // b
    assertTrue(regex.escapedCharacterAt(4)); // \
    assertFalse(regex.escapedCharacterAt(5)); // c
    assertTrue(regex.escapedCharacterAt(10)); // |
    assertFalse(regex.escapedCharacterAt(11)); // f
    assertFalse(regex.escapedCharacterAt(16)); // |
  }
  
  @Test
  public void getSubExpressionsTest() {
    Regex regex = new Regex("a|b");
    List<String> subExpressions = regex.getSubExpressions();
    assertEquals(2, subExpressions.size());
    assertEquals("a", subExpressions.get(0));
    assertEquals("b", subExpressions.get(1));
    
    regex = new Regex("a");
    subExpressions = regex.getSubExpressions();
    assertEquals(1, subExpressions.size());
    assertEquals("a", subExpressions.get(0));
    
    regex = new Regex("(a|b)|(cd)|ef");
    subExpressions = regex.getSubExpressions();
    assertEquals(3, subExpressions.size());
    assertEquals("(a|b)", subExpressions.get(0));
    assertEquals("(cd)", subExpressions.get(1));
    assertEquals("ef", subExpressions.get(2));
    
    regex = new Regex("(a|b(cdef|))");
    subExpressions = regex.getSubExpressions();
    assertEquals(1, subExpressions.size());
    assertEquals("(a|b(cdef|))", subExpressions.get(0));
    
    regex = new Regex("a||b");
    subExpressions = regex.getSubExpressions();
    assertEquals(3, subExpressions.size());
    assertEquals("a", subExpressions.get(0));
    assertEquals("", subExpressions.get(1));
    assertEquals("b", subExpressions.get(2));
    
    regex = new Regex("a\\|b");
    subExpressions = regex.getSubExpressions();
    assertEquals(1, subExpressions.size());
    assertEquals("a\\|b", subExpressions.get(0));
    
    regex = new Regex("a\\|b\\\\|c");
    subExpressions = regex.getSubExpressions();
    assertEquals(2, subExpressions.size());
    assertEquals("a\\|b\\\\", subExpressions.get(0));
    assertEquals("c", subExpressions.get(1));
  }
}
