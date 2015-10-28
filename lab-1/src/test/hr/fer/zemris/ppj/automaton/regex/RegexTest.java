package hr.fer.zemris.ppj.automaton.regex;

import org.junit.Test;
import static org.junit.Assert.*;

import hr.fer.zemris.ppj.regex.Regex;

public class RegexTest {
  
  @Test
  public void isOperatorTest() {
    //                       012-3-456-7-8-901-2-3-4-56
    Regex regex = new Regex("a|b\\\\cd\\\\\\ef\\\\\\\\|"); // a|b\\cd\\\|f\\\\|
    assertEquals(false, regex.isEscapedCharacterAt(0)); // a
    assertEquals(false, regex.isEscapedCharacterAt(1)); // |
    assertEquals(false, regex.isEscapedCharacterAt(2)); // b
    assertEquals(true, regex.isEscapedCharacterAt(4)); // \
    assertEquals(false, regex.isEscapedCharacterAt(5)); // c
    assertEquals(true, regex.isEscapedCharacterAt(10)); // |
    assertEquals(false, regex.isEscapedCharacterAt(11)); // f
    assertEquals(false, regex.isEscapedCharacterAt(16)); // |
  }
}
