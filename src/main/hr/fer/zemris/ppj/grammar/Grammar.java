package hr.fer.zemris.ppj.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <T> type of productions
 */
public class Grammar {
  private List<Production> productions;
  private Map<NonTerminalSymbol<?>, List<Production>> productionsTable;
  private NonTerminalSymbol<?> initialNonTerminalSymbol;

  private Set<Symbol<?>> emptySymbols;
  private Map<Symbol<?>, Set<Symbol<?>>> beginsDirectlyWithSymbolTable;
  private Map<Symbol<?>, Set<Symbol<?>>> beginsWithSymbolTable;
  private Map<Symbol<?>, Set<Symbol<?>>> beginsWithTable;

  public Grammar(Collection<Production> productions,
      NonTerminalSymbol<?> initialNonTerminalSymbol) {
    this.productions = new ArrayList<>(productions);
    this.productionsTable = new HashMap<>();
    for (Production production : productions) {
      if (!productionsTable.containsKey(production.getLeftSide())) {
        productionsTable.put(production.getLeftSide(), new ArrayList<>());
      }
      productionsTable.get(production.getLeftSide()).add(production);
    }
    this.initialNonTerminalSymbol = initialNonTerminalSymbol;
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

  public NonTerminalSymbol<?> getInitialNonTerminalSymbol() {
    return initialNonTerminalSymbol;
  }

  public Production getInitialProduction() {
    return productionsTable.get(initialNonTerminalSymbol).get(0);
  }

  public boolean isEmptySymbol(Symbol<?> symbol) {
    if (symbol instanceof TerminalSymbol<?>) {
      return false;
    }
    return getEmptySymbols().contains(symbol);
  }

  public boolean isEmptySequence(Collection<Symbol<?>> sequence) {
    for (Symbol<?> symbol : sequence) {
      if (!isEmptySymbol(symbol)) {
        return false;
      }
    }
    return true;
  }

  public Set<Symbol<?>> getEmptySymbols() {
    if (emptySymbols != null) {
      return emptySymbols;
    }

    emptySymbols = new HashSet<>();
    for (Production production : productions) {
      if (production.getRightSide().isEmpty()) {
        emptySymbols.add(production.getLeftSide());
      }
    }

    Set<NonTerminalSymbol<?>> newEmptySymbols;
    do {
      newEmptySymbols = new HashSet<>();
      for (Production production : productions) {
        if (emptySymbols.equals(new HashSet<>(production.getRightSide()))) {
          newEmptySymbols.add(production.getLeftSide());
        }
      }
      emptySymbols.addAll(newEmptySymbols);
    } while (!newEmptySymbols.isEmpty());

    return emptySymbols;
  }

  public Map<Symbol<?>, Set<Symbol<?>>> getBeginsDirectlyWithSymbolTable() {
    if (beginsDirectlyWithSymbolTable != null) {
      return beginsDirectlyWithSymbolTable;
    }

    beginsDirectlyWithSymbolTable = new HashMap<>();
    for (Production production : productions) {
      NonTerminalSymbol<?> leftSide = production.getLeftSide();
      List<Symbol<?>> rightSide = production.getRightSide();

      if (!beginsDirectlyWithSymbolTable.containsKey(leftSide)) {
        beginsDirectlyWithSymbolTable.put(leftSide, new HashSet<>());
      }

      for (Symbol<?> symbol : rightSide) {
        beginsDirectlyWithSymbolTable.get(leftSide).add(symbol);
        if (!isEmptySymbol(symbol)) {
          break;
        }
      }
    }

    return beginsDirectlyWithSymbolTable;
  }

  public Map<Symbol<?>, Set<Symbol<?>>> getBeginsWithSymbolTable() {
    if (beginsWithSymbolTable != null) {
      return beginsWithSymbolTable;
    }

    beginsWithSymbolTable = new HashMap<>();
    Map<Symbol<?>, Set<Symbol<?>>> beginsDirectlyWithSymbolTable =
        getBeginsDirectlyWithSymbolTable();

    for (Symbol<?> symbol : beginsDirectlyWithSymbolTable.keySet()) {
      beginsWithSymbolTable.put(symbol, new HashSet<>());
      beginsWithSymbolTable.get(symbol).add(symbol);

      for (Symbol<?> beginSymbol : beginsDirectlyWithSymbolTable.get(symbol)) {
        beginsWithSymbolTable.get(symbol).addAll(transitiveClosure(beginSymbol));
      }
    }

    return beginsWithSymbolTable;
  }

  private Set<Symbol<?>> transitiveClosure(Symbol<?> symbol) {
    HashSet<Symbol<?>> transitiveClosure = new HashSet<>();
    transitiveClosure.add(symbol);
    if (beginsDirectlyWithSymbolTable.containsKey(symbol)) {
      for (Symbol<?> neighbour : beginsDirectlyWithSymbolTable.get(symbol)) {
        transitiveClosure.addAll(transitiveClosure(neighbour));
      }
    }
    return transitiveClosure;
  }

  public Map<Symbol<?>, Set<Symbol<?>>> getBeginsWithTable() {
    if (beginsWithTable != null) {
      return beginsWithTable;
    }

    beginsWithTable = new HashMap<>();
    Map<Symbol<?>, Set<Symbol<?>>> beginsWithSymbolTable = getBeginsWithSymbolTable();

    for (Symbol<?> symbol : beginsWithSymbolTable.keySet()) {
      beginsWithTable.put(symbol, new HashSet<>());
      for (Symbol<?> neighbourSymbol : beginsWithSymbolTable.get(symbol)) {
        if (neighbourSymbol instanceof TerminalSymbol<?>) {
          beginsWithTable.get(symbol).add((TerminalSymbol<?>) neighbourSymbol);
        }
      }
    }

    return beginsWithTable;
  }

  public Set<Symbol<?>> beginsWith(Symbol<?> symbol) {
    if (!getBeginsWithTable().containsKey(symbol)) {
      return new HashSet<>(Arrays.asList(symbol));
    }
    return getBeginsWithTable().get(symbol);
  }

  public Set<Symbol<?>> beginsWith(Collection<Symbol<?>> symbols) {
    Set<Symbol<?>> beginsWith = new HashSet<>();
    for (Symbol<?> symbol : symbols) {
      beginsWith.addAll(beginsWith(symbol));
      if (!isEmptySymbol(symbol)) {
        break;
      }
    }
    return beginsWith;
  }

  public Set<Symbol<?>> beginsWith(Production production) {
    return beginsWith(production.getRightSide());
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
