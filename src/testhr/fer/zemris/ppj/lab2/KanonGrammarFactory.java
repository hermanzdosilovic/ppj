package hr.fer.zemris.ppj.lab2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.analyzer.SA;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.PutAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class KanonGrammarFactory {
  public NonTerminalSymbol S, A, B;
  public TerminalSymbol a, b;
  public TerminalSymbol end;
  public List<Symbol> alphabet;
  public LRItem i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
  public Set<LRItem> s0, s1, s2, s3, s4, s5, s6;

  public Automaton<LRItem, Symbol> expectedENFA;
  public Automaton<Set<LRItem>, Symbol> expectedDFA;
  public Map<Pair<Set<LRItem>, TerminalSymbol>, Action> expectedActionTable;
  public Map<Pair<Set<LRItem>, NonTerminalSymbol>, Action> expectedNewStateTable;

  public Grammar grammar;
  public Production initialProduction;
  public LRItem initialCompleteLRItem;

  public KanonGrammarFactory() {
    createGrammarObjects();
  }

  private void createGrammarObjects() {
    createSymbols();
    createGrammar();

    initialProduction = new Production(S, A);
    initialCompleteLRItem = new LRItem(initialProduction, 1, Arrays.asList(end));

    createAlphabet();
    createLRItems();
    createLRStates();
    createENFA();
    createDFA();
    createActionTable();
    createNewStateTable();
  }

  private void createSymbols() {
    S = new NonTerminalSymbol("<A>++");
    A = new NonTerminalSymbol("<A>");
    B = new NonTerminalSymbol("<B>");
    a = new TerminalSymbol("a");
    b = new TerminalSymbol("b");
    end = new TerminalSymbol(SA.END_STRING);
  }

  private void createGrammar() {
    grammar = new Grammar(Arrays.asList(new Production(S, A), new Production(A, B, A),
        new Production(A), new Production(B, a, B), new Production(B, b)), S);
  }

  private void createAlphabet() {
    alphabet = Arrays.asList(S, A, B, a, b); // not sure about SA.END_STRING
  }

  private void createLRItems() {
    // <S> -> * <A>, { # }
    i0 = new LRItem(new Production(S, A), 0, Arrays.asList(end));

    // <S> -> <A> *, { # }
    i1 = new LRItem(new Production(S, A), 1, Arrays.asList(end));

    // <A> -> * <B> <A>, { # }
    i2 = new LRItem(new Production(A, B, A), 0, Arrays.asList(end));

    // <A> -> *, { # }
    i3 = new LRItem(new Production(A), 0, Arrays.asList(end));

    // <A> -> <B> * <A>, { # }
    i4 = new LRItem(new Production(A, B, A), 1, Arrays.asList(end));

    // <B> -> * a <B>, { a b # }
    i5 = new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end));

    // <B> -> * b, { a b # }
    i6 = new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end));

    // <A> -> <B> <A> *, { # }
    i7 = new LRItem(new Production(A, B, A), 2, Arrays.asList(end));

    // <B> -> a * <B>, { a b # }
    i8 = new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end));

    // <B> -> b *, { a b # }
    i9 = new LRItem(new Production(B, b), 1, Arrays.asList(a, b, end));

    // <B> -> a <B> *, { a b # }
    i10 = new LRItem(new Production(B, a, B), 2, Arrays.asList(a, b, end));
  }

  private void createLRStates() {
    s0 = new HashSet<>(Arrays.asList(i0, i2, i3, i5, i6));
    s1 = new HashSet<>(Arrays.asList(i1));
    s2 = new HashSet<>(Arrays.asList(i4, i2, i3, i5, i6));
    s3 = new HashSet<>(Arrays.asList(i8, i5, i6));
    s4 = new HashSet<>(Arrays.asList(i9));
    s5 = new HashSet<>(Arrays.asList(i7));
    s6 = new HashSet<>(Arrays.asList(i10));
  }

  private void createENFA() {
    TransitionFunction<LRItem, Symbol> ENFAtransitionFunction = new TransitionFunction<>();
    ENFAtransitionFunction.addEpsilonTransition(i0, i2);
    ENFAtransitionFunction.addEpsilonTransition(i0, i3);
    ENFAtransitionFunction.addTransition(i0, A, i1);
    ENFAtransitionFunction.addEpsilonTransition(i2, i5);
    ENFAtransitionFunction.addEpsilonTransition(i2, i6);
    ENFAtransitionFunction.addTransition(i2, B, i4);
    ENFAtransitionFunction.addEpsilonTransition(i4, i2);
    ENFAtransitionFunction.addEpsilonTransition(i4, i3);
    ENFAtransitionFunction.addTransition(i4, A, i7);
    ENFAtransitionFunction.addTransition(i5, a, i8);
    ENFAtransitionFunction.addTransition(i6, b, i9);
    ENFAtransitionFunction.addEpsilonTransition(i8, i5);
    ENFAtransitionFunction.addEpsilonTransition(i8, i6);
    ENFAtransitionFunction.addTransition(i8, B, i10);

    Set<LRItem> states = new HashSet<>(Arrays.asList(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10));
    Set<LRItem> acceptableStates = states;
    Set<LRItem> initialState = new HashSet<>(Arrays.asList(i0));

    expectedENFA =
        new Automaton<>(states, alphabet, ENFAtransitionFunction, initialState, acceptableStates);
  }

  private void createDFA() {
    TransitionFunction<Set<LRItem>, Symbol> DFATransitionFunction = new TransitionFunction<>();
    DFATransitionFunction.addTransition(s0, A, s1);
    DFATransitionFunction.addTransition(s0, a, s3);
    DFATransitionFunction.addTransition(s0, B, s2);
    DFATransitionFunction.addTransition(s0, b, s4);
    DFATransitionFunction.addTransition(s2, B, s2);
    DFATransitionFunction.addTransition(s2, A, s5);
    DFATransitionFunction.addTransition(s2, b, s4);
    DFATransitionFunction.addTransition(s2, a, s3);
    DFATransitionFunction.addTransition(s3, a, s3);
    DFATransitionFunction.addTransition(s3, b, s4);
    DFATransitionFunction.addTransition(s3, B, s6);

    Set<Set<LRItem>> states = new HashSet<>(Arrays.asList(s0, s1, s2, s3, s4, s5, s6));
    Set<Set<LRItem>> acceptableStates = states;
    Set<Set<LRItem>> initialState = new HashSet<>(Arrays.asList(s0));

    expectedDFA =
        new Automaton<>(states, alphabet, DFATransitionFunction, initialState, acceptableStates);
  }

  private void createActionTable() {
    expectedActionTable = new HashMap<>();
    expectedActionTable.put(new Pair<>(s0, a), new MoveAction<>(s3));
    expectedActionTable.put(new Pair<>(s0, b), new MoveAction<>(s4));
    expectedActionTable.put(new Pair<>(s0, end), new ReduceAction(new Production(A)));
    expectedActionTable.put(new Pair<>(s1, end), new AcceptAction());
    expectedActionTable.put(new Pair<>(s2, a), new MoveAction<>(s3));
    expectedActionTable.put(new Pair<>(s2, b), new MoveAction<>(s4));
    expectedActionTable.put(new Pair<>(s2, end), new ReduceAction(new Production(A)));
    expectedActionTable.put(new Pair<>(s3, a), new MoveAction<>(s3));
    expectedActionTable.put(new Pair<>(s3, b), new MoveAction<>(s4));
    expectedActionTable.put(new Pair<>(s4, a), new ReduceAction(new Production(B, b)));
    expectedActionTable.put(new Pair<>(s4, b), new ReduceAction(new Production(B, b)));
    expectedActionTable.put(new Pair<>(s4, end), new ReduceAction(new Production(B, b)));
    expectedActionTable.put(new Pair<>(s5, end), new ReduceAction(new Production(A, B, A)));
    expectedActionTable.put(new Pair<>(s6, a), new ReduceAction(new Production(B, a, B)));
    expectedActionTable.put(new Pair<>(s6, b), new ReduceAction(new Production(B, a, B)));
    expectedActionTable.put(new Pair<>(s6, end), new ReduceAction(new Production(B, a, B)));
  }

  private void createNewStateTable() {
    expectedNewStateTable = new HashMap<>();
    expectedNewStateTable.put(new Pair<>(s0, B), new PutAction<>(s2));
    expectedNewStateTable.put(new Pair<>(s0, A), new PutAction<>(s1));
    expectedNewStateTable.put(new Pair<>(s2, B), new PutAction<>(s2));
    expectedNewStateTable.put(new Pair<>(s2, A), new PutAction<>(s5));
    expectedNewStateTable.put(new Pair<>(s3, B), new PutAction<>(s6));
  }
}
