package hr.fer.zemris.ppj.lab2.parser.action;

/**
 * A class that serves as a signal to the LR parser that it should accept the given input.
 * 
 * @author Ivan Krpelnik
 */
public final class AcceptAction implements Action {

  private static final long serialVersionUID = 7479073770788071993L;

  @Override
  public String toString() {
    return "accept()";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    return prime;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    return true;
  }
}
