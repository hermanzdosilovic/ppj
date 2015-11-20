package hr.fer.zemris.ppj.lab2.parser.action;

import hr.fer.zemris.ppj.grammar.Production;

public final class ReduceAction implements Action {
  private static final long serialVersionUID = -6928861363927054533L;
  private Production production;

  public ReduceAction(Production production) {
    this.production = production;
  }

  public Production getProduction() {
    return production;
  }

  @Override
  public String toString() {
    return "reduce(" + production + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((production == null) ? 0 : production.hashCode());
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
    ReduceAction other = (ReduceAction) obj;
    if (production == null) {
      if (other.production != null)
        return false;
    } else if (!production.equals(other.production))
      return false;
    return true;
  }
}
