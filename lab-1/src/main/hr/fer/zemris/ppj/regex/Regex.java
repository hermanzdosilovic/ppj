package hr.fer.zemris.ppj.regex;

public class Regex {
  
  private String value;
  
  public Regex(final String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
  
  private boolean isOperator(int index) {
    int countEscape = 0;
    while(index - 1 >= 0 && value.charAt(index) == '\\') {
      countEscape++;
      index--;
    }
    return countEscape%2 == 0;
  }
}
