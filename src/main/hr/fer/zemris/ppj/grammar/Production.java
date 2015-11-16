package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <T> type of symbols
 */
public class Production<T> {
  private NonTerminalSymbol<T> leftSide;
  private List<Symbol<T>> rightSide;

  public Production(NonTerminalSymbol<T> leftSide, Collection<Symbol<T>> rightSide) {
    this.leftSide = leftSide;
    this.rightSide = new ArrayList<>(rightSide);
  }

  public NonTerminalSymbol<T> getLeftSide() {
    return leftSide;
  }

  public List<Symbol<T>> getRightSide() {
    return rightSide;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((leftSide == null) ? 0 : leftSide.hashCode());
    result = prime * result + ((rightSide == null) ? 0 : rightSide.hashCode());
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
    Production<?> other = (Production<?>) obj;
    if (leftSide == null) {
      if (other.leftSide != null)
        return false;
    } else if (!leftSide.equals(other.leftSide))
      return false;
    if (rightSide == null) {
      if (other.rightSide != null)
        return false;
    } else if (!rightSide.equals(other.rightSide))
      return false;
    return true;
  }
}
