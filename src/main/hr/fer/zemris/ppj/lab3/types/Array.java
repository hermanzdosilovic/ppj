package hr.fer.zemris.ppj.lab3.types;

public class Array implements Type {
  
  private NumericType numericType;
  
  public Array(NumericType numericType) {
    super();
    this.numericType = numericType;
  }

  public NumericType getNumericType() {
    return numericType;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((numericType == null) ? 0 : numericType.hashCode());
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
    Array other = (Array) obj;
    if (numericType == null) {
      if (other.numericType != null)
        return false;
    } else if (!numericType.equals(other.numericType))
      return false;
    return true;
  }
  
}
