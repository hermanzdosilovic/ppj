package hr.fer.zemris.ppj.lab2.parser;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class LRState implements Serializable {

  private static final long serialVersionUID = 7507596625913427459L;

  private Set<LRItem> LRItems;
  private Integer label;

  public LRState(final Set<LRItem> LRItems, final Integer label) {
    this.LRItems = LRItems;
    this.label = label;
  }

  public Set<LRItem> getLRItems() {
    return LRItems;
  }

  public Integer getLabel() {
    return label;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((label == null) ? 0 : label.hashCode());
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
    LRState other = (LRState) obj;
    if (label == null) {
      if (other.label != null)
        return false;
    } else if (!label.equals(other.label))
      return false;
    return true;
  }
}
