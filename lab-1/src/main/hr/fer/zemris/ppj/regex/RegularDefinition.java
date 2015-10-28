package hr.fer.zemris.ppj.regex;

/**
 * A class that represents a regular definition. Regular definition is defined by a name and an expression.
 * Expression is regular expression which can contain other regular definitions written inside of an curly brackets e.g. {name}.
 * 
 * @author Josipa Kelava
 *
 */
public class RegularDefinition {

  private String name;
  private String value;

  /**
   * Constructor takes name and expression.
   * 
   * @param name - name of regular definition
   * @param value - expression
   */
  public RegularDefinition(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    RegularDefinition other = (RegularDefinition) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    return true;
  }

}
