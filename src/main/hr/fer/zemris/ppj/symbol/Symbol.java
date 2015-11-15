package hr.fer.zemris.ppj.symbol;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <T> type of symbol
 */
public abstract class Symbol<T> {
  private T value;
  
  public Symbol(T value) {
    this.value = value;
  }
  
  public T getValue() {
    return this.value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    Symbol<?> other = (Symbol<?>) obj;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return value.toString();
  }
}
