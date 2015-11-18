package hr.fer.zemris.ppj.grammar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class GrammarTest {
  
  @Test
  public void getEmptySymbolsTest() {
    NonTerminalSymbol<Character> A = new NonTerminalSymbol<Character>('A');
    NonTerminalSymbol<Character> B = new NonTerminalSymbol<Character>('B');
    NonTerminalSymbol<Character> C = new NonTerminalSymbol<Character>('C');
    NonTerminalSymbol<Character> D = new NonTerminalSymbol<Character>('D');
    NonTerminalSymbol<Character> E = new NonTerminalSymbol<Character>('E');
    TerminalSymbol<Character> a = new TerminalSymbol<Character>('a');
    TerminalSymbol<Character> b = new TerminalSymbol<Character>('b');
    TerminalSymbol<Character> c = new TerminalSymbol<Character>('c');
    TerminalSymbol<Character> d = new TerminalSymbol<Character>('d');
    TerminalSymbol<Character> e = new TerminalSymbol<Character>('e');
    TerminalSymbol<Character> f = new TerminalSymbol<Character>('f');
    
    List<Production> productions = new ArrayList<>();
    productions.add(new Production(A, Arrays.asList(B, C, c)));
    productions.add(new Production(A, Arrays.asList(e, D, B)));
    productions.add(new Production(B)); // epsilon production
    productions.add(new Production(B, Arrays.asList(b, C, D, E)));
    productions.add(new Production(C, Arrays.asList(D, a, B)));
    productions.add(new Production(C, Arrays.asList(c, a)));
    productions.add(new Production(D));
    productions.add(new Production(D, Arrays.asList(d, D)));
    productions.add(new Production(E, Arrays.asList(e, A, f)));
    productions.add(new Production(E, Arrays.asList(c)));
    
    Grammar grammar = new Grammar(productions, A);
    
    assertEquals(new HashSet<>(Arrays.asList(B, D)), grammar.getEmptySymbols());
  }
  
  @Test
  public void beginsDirectlyWithSymbolTest() {
    NonTerminalSymbol<Character> A = new NonTerminalSymbol<Character>('A');
    NonTerminalSymbol<Character> B = new NonTerminalSymbol<Character>('B');
    NonTerminalSymbol<Character> C = new NonTerminalSymbol<Character>('C');
    NonTerminalSymbol<Character> D = new NonTerminalSymbol<Character>('D');
    NonTerminalSymbol<Character> E = new NonTerminalSymbol<Character>('E');
    TerminalSymbol<Character> a = new TerminalSymbol<Character>('a');
    TerminalSymbol<Character> b = new TerminalSymbol<Character>('b');
    TerminalSymbol<Character> c = new TerminalSymbol<Character>('c');
    TerminalSymbol<Character> d = new TerminalSymbol<Character>('d');
    TerminalSymbol<Character> e = new TerminalSymbol<Character>('e');
    TerminalSymbol<Character> f = new TerminalSymbol<Character>('f');
    
    List<Production> productions = new ArrayList<>();
    productions.add(new Production(A, Arrays.asList(B, C, c)));
    productions.add(new Production(A, Arrays.asList(e, D, B)));
    productions.add(new Production(B)); // epsilon production
    productions.add(new Production(B, Arrays.asList(b, C, D, E)));
    productions.add(new Production(C, Arrays.asList(D, a, B)));
    productions.add(new Production(C, Arrays.asList(c, a)));
    productions.add(new Production(D));
    productions.add(new Production(D, Arrays.asList(d, D)));
    productions.add(new Production(E, Arrays.asList(e, A, f)));
    productions.add(new Production(E, Arrays.asList(c)));
    
    Grammar grammar = new Grammar(productions, A);
    
    Map<NonTerminalSymbol<?>, Set<Symbol<Character>>> expectedBeginsDirectlyWithTable = new HashMap<>();
    expectedBeginsDirectlyWithTable.put(A, new HashSet<>(Arrays.asList(B, C, e)));
    expectedBeginsDirectlyWithTable.put(B, new HashSet<>(Arrays.asList(b)));
    expectedBeginsDirectlyWithTable.put(C, new HashSet<>(Arrays.asList(D, a, c)));
    expectedBeginsDirectlyWithTable.put(D, new HashSet<>(Arrays.asList(d)));
    expectedBeginsDirectlyWithTable.put(E, new HashSet<>(Arrays.asList(c, e)));
    
    assertEquals(expectedBeginsDirectlyWithTable, grammar.getBeginsDirectlyWithSymbolTable());
  }
  
  @Test
  public void beginsWithSymbolTest() {
    NonTerminalSymbol<Character> A = new NonTerminalSymbol<Character>('A');
    NonTerminalSymbol<Character> B = new NonTerminalSymbol<Character>('B');
    NonTerminalSymbol<Character> C = new NonTerminalSymbol<Character>('C');
    NonTerminalSymbol<Character> D = new NonTerminalSymbol<Character>('D');
    NonTerminalSymbol<Character> E = new NonTerminalSymbol<Character>('E');
    TerminalSymbol<Character> a = new TerminalSymbol<Character>('a');
    TerminalSymbol<Character> b = new TerminalSymbol<Character>('b');
    TerminalSymbol<Character> c = new TerminalSymbol<Character>('c');
    TerminalSymbol<Character> d = new TerminalSymbol<Character>('d');
    TerminalSymbol<Character> e = new TerminalSymbol<Character>('e');
    TerminalSymbol<Character> f = new TerminalSymbol<Character>('f');
    
    List<Production> productions = new ArrayList<>();
    productions.add(new Production(A, Arrays.asList(B, C, c)));
    productions.add(new Production(A, Arrays.asList(e, D, B)));
    productions.add(new Production(B)); // epsilon production
    productions.add(new Production(B, Arrays.asList(b, C, D, E)));
    productions.add(new Production(C, Arrays.asList(D, a, B)));
    productions.add(new Production(C, Arrays.asList(c, a)));
    productions.add(new Production(D));
    productions.add(new Production(D, Arrays.asList(d, D)));
    productions.add(new Production(E, Arrays.asList(e, A, f)));
    productions.add(new Production(E, Arrays.asList(c)));
    
    Grammar grammar = new Grammar(productions, A);
    
    Map<NonTerminalSymbol<?>, Set<Symbol<Character>>> expectedBeginsDirectlyWithTable = new HashMap<>();
    expectedBeginsDirectlyWithTable.put(A, new HashSet<>(Arrays.asList(A, B, C, D, a, b, c, d, e)));
    expectedBeginsDirectlyWithTable.put(B, new HashSet<>(Arrays.asList(B, b)));
    expectedBeginsDirectlyWithTable.put(C, new HashSet<>(Arrays.asList(C, D, a, c, d)));
    expectedBeginsDirectlyWithTable.put(D, new HashSet<>(Arrays.asList(D, d)));
    expectedBeginsDirectlyWithTable.put(E, new HashSet<>(Arrays.asList(E, c, e)));
    
    assertEquals(expectedBeginsDirectlyWithTable, grammar.getBeginsWithSymbolTable());
  }
  
  @Test
  public void beginsWithTest() {
    NonTerminalSymbol<Character> A = new NonTerminalSymbol<Character>('A');
    NonTerminalSymbol<Character> B = new NonTerminalSymbol<Character>('B');
    NonTerminalSymbol<Character> C = new NonTerminalSymbol<Character>('C');
    NonTerminalSymbol<Character> D = new NonTerminalSymbol<Character>('D');
    NonTerminalSymbol<Character> E = new NonTerminalSymbol<Character>('E');
    TerminalSymbol<Character> a = new TerminalSymbol<Character>('a');
    TerminalSymbol<Character> b = new TerminalSymbol<Character>('b');
    TerminalSymbol<Character> c = new TerminalSymbol<Character>('c');
    TerminalSymbol<Character> d = new TerminalSymbol<Character>('d');
    TerminalSymbol<Character> e = new TerminalSymbol<Character>('e');
    TerminalSymbol<Character> f = new TerminalSymbol<Character>('f');
    
    List<Production> productions = new ArrayList<>();
    productions.add(new Production(A, Arrays.asList(B, C, c)));
    productions.add(new Production(A, Arrays.asList(e, D, B)));
    productions.add(new Production(B)); // epsilon production
    productions.add(new Production(B, Arrays.asList(b, C, D, E)));
    productions.add(new Production(C, Arrays.asList(D, a, B)));
    productions.add(new Production(C, Arrays.asList(c, a)));
    productions.add(new Production(D));
    productions.add(new Production(D, Arrays.asList(d, D)));
    productions.add(new Production(E, Arrays.asList(e, A, f)));
    productions.add(new Production(E, Arrays.asList(c)));
    
    Grammar grammar = new Grammar(productions, A);
    
    Map<NonTerminalSymbol<?>, Set<Symbol<Character>>> expectedBeginsDirectlyWithTable = new HashMap<>();
    expectedBeginsDirectlyWithTable.put(A, new HashSet<>(Arrays.asList(a, b, c, d, e)));
    expectedBeginsDirectlyWithTable.put(B, new HashSet<>(Arrays.asList(b)));
    expectedBeginsDirectlyWithTable.put(C, new HashSet<>(Arrays.asList(a, c, d)));
    expectedBeginsDirectlyWithTable.put(D, new HashSet<>(Arrays.asList(d)));
    expectedBeginsDirectlyWithTable.put(E, new HashSet<>(Arrays.asList(c, e)));
    
    assertEquals(expectedBeginsDirectlyWithTable, grammar.getBeginsWithTable());
  }
  
  @Test
  public void beginsWithOnSequenceTest() {
    NonTerminalSymbol<Character> A = new NonTerminalSymbol<Character>('A');
    NonTerminalSymbol<Character> B = new NonTerminalSymbol<Character>('B');
    NonTerminalSymbol<Character> C = new NonTerminalSymbol<Character>('C');
    NonTerminalSymbol<Character> D = new NonTerminalSymbol<Character>('D');
    NonTerminalSymbol<Character> E = new NonTerminalSymbol<Character>('E');
    TerminalSymbol<Character> a = new TerminalSymbol<Character>('a');
    TerminalSymbol<Character> b = new TerminalSymbol<Character>('b');
    TerminalSymbol<Character> c = new TerminalSymbol<Character>('c');
    TerminalSymbol<Character> d = new TerminalSymbol<Character>('d');
    TerminalSymbol<Character> e = new TerminalSymbol<Character>('e');
    TerminalSymbol<Character> f = new TerminalSymbol<Character>('f');
    
    List<Production> productions = new ArrayList<>();
    productions.add(new Production(A, Arrays.asList(B, C, c)));
    productions.add(new Production(A, Arrays.asList(e, D, B)));
    productions.add(new Production(B)); // epsilon production
    productions.add(new Production(B, Arrays.asList(b, C, D, E)));
    productions.add(new Production(C, Arrays.asList(D, a, B)));
    productions.add(new Production(C, Arrays.asList(c, a)));
    productions.add(new Production(D));
    productions.add(new Production(D, Arrays.asList(d, D)));
    productions.add(new Production(E, Arrays.asList(e, A, f)));
    productions.add(new Production(E, Arrays.asList(c)));
    
    Grammar grammar = new Grammar(productions, A);
    
    assertEquals(new HashSet<>(Arrays.asList(a, b, c, d)), grammar.beginsWith(Arrays.asList(B, C, c)));
    assertEquals(new HashSet<>(Arrays.asList(e)), grammar.beginsWith(Arrays.asList(e, D, B)));
    assertEquals(new HashSet<>(Arrays.asList()), grammar.beginsWith(Arrays.asList()));
    assertEquals(new HashSet<>(Arrays.asList(b)), grammar.beginsWith(Arrays.asList(b, C, D, E)));
    assertEquals(new HashSet<>(Arrays.asList(a, d)), grammar.beginsWith(Arrays.asList(D, a, B)));
    assertEquals(new HashSet<>(Arrays.asList(c)), grammar.beginsWith(Arrays.asList(c, a)));
    assertEquals(new HashSet<>(Arrays.asList(d)), grammar.beginsWith(Arrays.asList(d, D)));
    assertEquals(new HashSet<>(Arrays.asList(e)), grammar.beginsWith(Arrays.asList(e, A, f)));
    assertEquals(new HashSet<>(Arrays.asList(c)), grammar.beginsWith(Arrays.asList(c)));
    
  }
}
