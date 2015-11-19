package hr.fer.zemris.ppj.lab2.parser.action;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class PutAction<E> implements Action {
  private static final long serialVersionUID = 6227474813983150516L;
  private E state;

  public PutAction(E state) {
    this.state = state;
  }

  public E getState() {
    return state;
  }

  @Override
  public String toString() {
    return "put(" + state + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((state == null) ? 0 : state.hashCode());
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
    PutAction<?> other = (PutAction<?>) obj;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    return true;
  }
}
