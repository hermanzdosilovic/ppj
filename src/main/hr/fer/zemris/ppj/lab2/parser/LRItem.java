package hr.fer.zemris.ppj.lab2.parser;

import java.util.List;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class LRItem {
  private Production production;
  private Integer dotIndex;
  private List<TerminalSymbol<?>> nextTerminalSymbols;

  public LRItem(Production production, Integer dotIndex,
      List<TerminalSymbol<?>> nextTerminalSymbols) {
    this.production = production;
    this.dotIndex = dotIndex;
    this.nextTerminalSymbols = nextTerminalSymbols;
  }

  public Production getProduction() {
    return production;
  }

  public Integer getDotIndex() {
    return dotIndex;
  }

  public List<TerminalSymbol<?>> getNextTerminalSymbols() {
    return nextTerminalSymbols;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dotIndex == null) ? 0 : dotIndex.hashCode());
    result = prime * result + ((nextTerminalSymbols == null) ? 0 : nextTerminalSymbols.hashCode());
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
    if (nextTerminalSymbols == null) {
      if (other.nextTerminalSymbols != null)
        return false;
    } else if (!nextTerminalSymbols.equals(other.nextTerminalSymbols))
      return false;
    if (production == null) {
      if (other.production != null)
        return false;
    } else if (!production.equals(other.production))
      return false;
    return true;
  }
}
