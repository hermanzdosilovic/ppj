package hr.fer.zemris.ppj.grammar;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class GrammarTest {
  private Grammar grammar;
  private static NonTerminalSymbol A, B, C, D, E;
  private static TerminalSymbol a, b, c, d, e, f;
  private static List<Production> productions;
  
  @BeforeClass
  public static void createProduction() {
    createSymbols();
    productions = new ArrayList<>();
    productions.add(new Production(A, B, C, c));
    productions.add(new Production(A, e, D, B));
    productions.add(new Production(B));
    productions.add(new Production(B, b, C, D, E));
    productions.add(new Production(C, D, a, B));
    productions.add(new Production(C, c, a));
    productions.add(new Production(D));
    productions.add(new Production(D, d, D));
    productions.add(new Production(E, e, A, f));
    productions.add(new Production(E, c));
  }
  
  public static void createSymbols() {
    A = new NonTerminalSymbol('A');
    B = new NonTerminalSymbol('B');
    C = new NonTerminalSymbol('C');
    D = new NonTerminalSymbol('D');
    E = new NonTerminalSymbol('E');
    a = new TerminalSymbol('a');
    b = new TerminalSymbol('b');
    c = new TerminalSymbol('c');
    d = new TerminalSymbol('d');
    e = new TerminalSymbol('e');
    f = new TerminalSymbol('f');
  }
  
  @Before
  public void createGrammar() {
    grammar = new Grammar(productions, A);
  }
  
  @Test
  public void getEmptySymbolsTest() {
    assertEquals(new HashSet<>(Arrays.asList(B, D)), grammar.getEmptySymbols());
  }
  
  @Test
  public void beginsDirectlyWithSymbolTest() {
    Map<NonTerminalSymbol, Set<Symbol>> expectedBeginsDirectlyWithSymbolTable = new HashMap<>();
    expectedBeginsDirectlyWithSymbolTable.put(A, new HashSet<>(Arrays.asList(B, C, e)));
    expectedBeginsDirectlyWithSymbolTable.put(B, new HashSet<>(Arrays.asList(b)));
    expectedBeginsDirectlyWithSymbolTable.put(C, new HashSet<>(Arrays.asList(D, a, c)));
    expectedBeginsDirectlyWithSymbolTable.put(D, new HashSet<>(Arrays.asList(d)));
    expectedBeginsDirectlyWithSymbolTable.put(E, new HashSet<>(Arrays.asList(c, e)));
    
    assertEquals(expectedBeginsDirectlyWithSymbolTable, grammar.getBeginsDirectlyWithSymbolTable());
  }
  
  @Test
  public void beginsWithSymbolTest() {
    Map<NonTerminalSymbol, Set<Symbol>> expectedBeginsWithSymbolTable = new HashMap<>();
    expectedBeginsWithSymbolTable.put(A, new HashSet<>(Arrays.asList(A, B, C, D, a, b, c, d, e)));
    expectedBeginsWithSymbolTable.put(B, new HashSet<>(Arrays.asList(B, b)));
    expectedBeginsWithSymbolTable.put(C, new HashSet<>(Arrays.asList(C, D, a, c, d)));
    expectedBeginsWithSymbolTable.put(D, new HashSet<>(Arrays.asList(D, d)));
    expectedBeginsWithSymbolTable.put(E, new HashSet<>(Arrays.asList(E, c, e)));
    
    assertEquals(expectedBeginsWithSymbolTable, grammar.getBeginsWithSymbolTable());
  }
  
  @Test
  public void beginsWithTest() {
    Map<NonTerminalSymbol, Set<Symbol>> expectedBeginsWithTable = new HashMap<>();
    expectedBeginsWithTable.put(A, new HashSet<>(Arrays.asList(a, b, c, d, e)));
    expectedBeginsWithTable.put(B, new HashSet<>(Arrays.asList(b)));
    expectedBeginsWithTable.put(C, new HashSet<>(Arrays.asList(a, c, d)));
    expectedBeginsWithTable.put(D, new HashSet<>(Arrays.asList(d)));
    expectedBeginsWithTable.put(E, new HashSet<>(Arrays.asList(c, e)));
    
    assertEquals(expectedBeginsWithTable, grammar.getBeginsWithTable());
  }
  
  @Test
  public void beginsWithOnSequenceTest() {
    assertEquals(new HashSet<>(Arrays.asList(a, b, c, d)), grammar.beginsWith(B, C, c));
    assertEquals(new HashSet<>(Arrays.asList(e)), grammar.beginsWith(e, D, B));
    assertEquals(new HashSet<>(Arrays.asList()), grammar.beginsWith());
    assertEquals(new HashSet<>(Arrays.asList(b)), grammar.beginsWith(b, C, D, E));
    assertEquals(new HashSet<>(Arrays.asList(a, d)), grammar.beginsWith(D, a, B));
    assertEquals(new HashSet<>(Arrays.asList(c)), grammar.beginsWith(c, a));
    assertEquals(new HashSet<>(Arrays.asList(d)), grammar.beginsWith(d, D));
    assertEquals(new HashSet<>(Arrays.asList(e)), grammar.beginsWith(e, A, f));
    assertEquals(new HashSet<>(Arrays.asList(c)), grammar.beginsWith(c));
  }
  
  @Test
  public void grammarCycleTest() {
    List<Production> productions = new ArrayList<>();
    productions.add(new Production(A, B));
    productions.add(new Production(B, b, B, B));
    productions.add(new Production(B, d));
    
    Grammar grammar = new Grammar(productions, A);
    
    Map<NonTerminalSymbol, Set<Symbol>> expectedBeginsWithSymbolTable = new HashMap<>();
    expectedBeginsWithSymbolTable.put(A, new HashSet<>(Arrays.asList(A, B, b, d)));
    expectedBeginsWithSymbolTable.put(B, new HashSet<>(Arrays.asList(B, b, d)));
    
    assertEquals(expectedBeginsWithSymbolTable, grammar.getBeginsWithSymbolTable());
  }
}
