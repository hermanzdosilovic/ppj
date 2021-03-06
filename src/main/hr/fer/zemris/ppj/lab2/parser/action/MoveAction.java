package hr.fer.zemris.ppj.lab2.parser.action;

/**
 * Represents a move action for LR parser. It signals the LR parser that it should move it's state
 * to the state that's held by this class.
 * 
 * @author Ivan Krpelnik
 */
public final class MoveAction<E> implements Action {

  private static final long serialVersionUID = 5192614674806549599L;
  private E state;

  public MoveAction(E state) {
    this.state = state;
  }

  public E getState() {
    return state;
  }

  @Override
  public String toString() {
    return "move(" + state + ")";
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
    MoveAction<?> other = (MoveAction<?>) obj;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    return true;
  }
}
