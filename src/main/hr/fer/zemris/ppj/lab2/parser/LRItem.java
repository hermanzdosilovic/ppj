package hr.fer.zemris.ppj.lab2.parser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class LRItem {
  private Production production;
  private Integer dotIndex;
  private Set<TerminalSymbol<?>> terminalSymbols;

  private LRItem nextLRItem;
  
  public LRItem(Production production, Integer dotIndex,
      Collection<TerminalSymbol<?>> terminalSymbols) {
    this.production = production;
    this.dotIndex = dotIndex;
    this.terminalSymbols = new HashSet<>(terminalSymbols);
    
    if (!isComplete()) {
      nextLRItem = new LRItem(production, dotIndex + 1, terminalSymbols);
    }
  }

  public Production getProduction() {
    return production;
  }

  public Integer getDotIndex() {
    return dotIndex;
  }

  public Set<TerminalSymbol<?>> getTerminalSymbols() {
    return new HashSet<>(terminalSymbols);
  }
  
  public boolean isComplete() {
    return dotIndex == production.getRightSide().size();
  }
  
  public Symbol<?> getDotSymbol() {
    if (isComplete()) {
      return null;
    }
    return production.getRightSide().get(dotIndex);
  }
  
  public LRItem getNextLRItem() {
    return nextLRItem;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dotIndex == null) ? 0 : dotIndex.hashCode());
    result = prime * result + ((terminalSymbols == null) ? 0 : terminalSymbols.hashCode());
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
    LRItem other = (LRItem) obj;
    if (dotIndex == null) {
      if (other.dotIndex != null)
        return false;
    } else if (!dotIndex.equals(other.dotIndex))
      return false;
    if (terminalSymbols == null) {
      if (other.terminalSymbols != null)
        return false;
    } else if (!terminalSymbols.equals(other.terminalSymbols))
      return false;
    if (production == null) {
      if (other.production != null)
        return false;
    } else if (!production.equals(other.production))
      return false;
    return true;
  }
}
