package hr.fer.zemris.ppj.regex;

public class Regex {
  
  private String value;
  
  public Regex(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
  public boolean isEscapedCharacterAt(int index) {
    boolean isEscaped = false;
    while(index - 1 >= 0 && value.charAt(index - 1) == '\\') {
      isEscaped = !isEscaped;
      index--;
    }
    return isEscaped;
  }
}
