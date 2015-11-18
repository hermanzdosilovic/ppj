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
  
  private Set<NonTerminalSymbol<?>> emptySymbols;
  private Map<NonTerminalSymbol<?>, Set<Symbol<?>>> beginsDirectlyWithSymbolTable;
  private Map<NonTerminalSymbol<?>, Set<Symbol<?>>> beginsWithSymbolTable;
  
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
  
  public Set<NonTerminalSymbol<?>> getEmptySymbols() {
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
  
  public Map<NonTerminalSymbol<?>, Set<Symbol<?>>> getBeginsDirectlyWithSymbolTable() {
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
  
  public Map<NonTerminalSymbol<?>, Set<Symbol<?>>> getBeginsWithSymbolTable() {
    if (beginsWithSymbolTable != null) {
      return beginsWithSymbolTable;
    }
    
    beginsWithSymbolTable = new HashMap<>();
    Map<NonTerminalSymbol<?>, Set<Symbol<?>>> beginsDirectlyWithSymbolTable = getBeginsDirectlyWithSymbolTable();
    
    for (NonTerminalSymbol<?> symbol : beginsDirectlyWithSymbolTable.keySet()) {
      beginsWithSymbolTable.put(symbol, new HashSet<>());
      beginsWithSymbolTable.get(symbol).add(symbol);
      
      for (Symbol<?> beginSymbol : beginsDirectlyWithSymbolTable.get(symbol)) {
        beginsWithSymbolTable.get(symbol).addAll(transitiveClosure(symbol, beginSymbol));
      }
    }
    
    return beginsWithSymbolTable;
  }

  private Set<Symbol<?>> transitiveClosure(Symbol<?> lastSymbol,
      Symbol<?> currentSymbol) {
    if (currentSymbol.equals(lastSymbol)) {
      return new HashSet<>(Arrays.asList(lastSymbol));
    }
    
    HashSet<Symbol<?>> symbols = new HashSet<>();
    symbols.add(currentSymbol);
    if (beginsDirectlyWithSymbolTable.containsKey(currentSymbol)) {
      for (Symbol<?> symbol : beginsDirectlyWithSymbolTable.get(currentSymbol)) {
        symbols.addAll(transitiveClosure(currentSymbol, symbol));
      }
    }
    
    return symbols;
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
