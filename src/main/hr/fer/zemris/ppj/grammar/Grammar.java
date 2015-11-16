package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <T> type of productions
 */
public class Grammar {
  private List<Production> productions;
  private Map<NonTerminalSymbol<?>, List<Production>> productionsTable;

  public Grammar(Collection<Production> productions) {
    this.productions = new ArrayList<>(productions);
    for (Production production : productions) {
      if (!productionsTable.containsKey(production.getLeftSide())) {
        productionsTable.put(production.getLeftSide(), new ArrayList<>());
      }
      productionsTable.get(production.getLeftSide()).add(production);
    }
  }

  public List<Production> getProductions() {
    return productions;
  }

  public List<Production> getSymbolProductions(NonTerminalSymbol<?> symbol) {
    if (!productionsTable.containsKey(symbol)) {
      return new ArrayList<>();
    }
    return productionsTable.get(symbol);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((productions == null) ? 0 : productions.hashCode());
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
    Grammar other = (Grammar) obj;
    if (productions == null) {
      if (other.productions != null)
        return false;
    } else if (!productions.equals(other.productions))
      return false;
    return true;
  }
}
