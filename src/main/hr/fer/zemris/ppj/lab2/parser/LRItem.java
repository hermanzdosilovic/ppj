package hr.fer.zemris.ppj.lab2.parser;

import java.util.List;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class LRItem {
  private Production production;
  private Integer dotIndex;
  private List<TerminalSymbol<?>> terminalSymbols;

  public LRItem(Production production, Integer dotIndex,
      List<TerminalSymbol<?>> terminalSymbols) {
    this.production = production;
    this.dotIndex = dotIndex;
    this.terminalSymbols = terminalSymbols;
  }

  public Production getProduction() {
    return production;
  }

  public Integer getDotIndex() {
    return dotIndex;
  }

  public List<TerminalSymbol<?>> getTerminalSymbols() {
    return terminalSymbols;
  }
  
  public boolean isComplete() {
    return dotIndex == production.getRightSide().size();
  }
  
  public Symbol<?> getDotSymbol() {
    return production.getRightSide().get(dotIndex);
  }
  
  public LRItem createNextItem() {
    return new LRItem(production, (dotIndex + 1)%(production.getRightSide().size() + 1), terminalSymbols);
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
