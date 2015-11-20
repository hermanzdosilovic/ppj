package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class Production {
  private NonTerminalSymbol leftSide;
  private List<Symbol> rightSide;

  public Production(NonTerminalSymbol leftSide, Collection<Symbol> rightSide) {
    this.leftSide = leftSide;
    this.rightSide = new ArrayList<>(rightSide);
  }

  @SafeVarargs
  public Production(NonTerminalSymbol leftSide, Symbol... rightSide) {
    this(leftSide, Arrays.asList(rightSide));
  }

  /**
   * Creates new epsilon production
   * 
   * @param leftSide left side of epsilon production
   */
  public Production(NonTerminalSymbol leftSide) {
    this(leftSide, new ArrayList<>());
  }

  public NonTerminalSymbol getLeftSide() {
    return leftSide;
  }

  public List<Symbol> getRightSide() {
    return rightSide;
  }

  public boolean isEpsilonProduction() {
    return rightSide.isEmpty();
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
    Production other = (Production) obj;
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
  
  @Override
  public String toString() {
    return leftSide + "->" + rightSide;
  }
}
