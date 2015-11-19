package hr.fer.zemris.ppj.lab2.parser.action;

public class RejectAction implements Action {

  private static final long serialVersionUID = 2290785534871385334L;

  @Override
  public String toString() {
    return "reject()";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result;
    return result;
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
