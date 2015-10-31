package hr.fer.zemris.ppj;

/**
 * A class that represents a lexical unit. It holds a name of a lexical unit provided at
 * construction.
 * 
 * @author ikrpelnik
 *
 */
public class LexicalUnit {

  private String name;

  /**
   * Constructor takes a name for this lexical unit
   * 
   * @param name - name for the new lexical unit
   */
  public LexicalUnit(String name) {
    this.name = name;
  }

  /**
   * Returns the name of this regular unit.
   * 
   * @return name of this regular unit
   */
  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    LexicalUnit other = (LexicalUnit) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

}
