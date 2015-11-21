package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.PutAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class TableBuilderTest {
  private static NonTerminalSymbol S, A, B;
  private static TerminalSymbol a, b;
  private static TerminalSymbol end;

  private Automaton<Set<LRItem>, Symbol> automaton;
  private Map<Pair<Set<LRItem>, TerminalSymbol>, Action> actionTable;
  private Map<Pair<Set<LRItem>, NonTerminalSymbol>, Action> newStateTable;

  @BeforeClass
  public static void createSymbols() {
    S = new NonTerminalSymbol("S");
    A = new NonTerminalSymbol("A");
    B = new NonTerminalSymbol("B");
    a = new TerminalSymbol("a");
    b = new TerminalSymbol("b");
    end = new TerminalSymbol("END");
  }

  @Before
  public void buildTables() {
    LRItem i00 = new LRItem(new Production(null, Arrays.asList()), 0, Arrays.asList()); // q0 state
    LRItem i01 = new LRItem(new Production(S, A), 0, Arrays.asList(end));
    LRItem i02 = new LRItem(new Production(A, B, A), 0, Arrays.asList(end));
    LRItem i03 = new LRItem(new Production(A), 0, Arrays.asList(end));
    LRItem i04 = new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end));
    LRItem i05 = new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end));
    Set<LRItem> s0 = new HashSet<>(Arrays.asList(i00, i01, i02, i03, i04, i05));

    LRItem i10 = new LRItem(new Production(S, A), 1, Arrays.asList(end));
    Set<LRItem> s1 = new HashSet<>(Arrays.asList(i10));

    LRItem i20 = new LRItem(new Production(A, B, A), 1, Arrays.asList(end));
    LRItem i21 = new LRItem(new Production(A, B, A), 0, Arrays.asList(end));
    LRItem i22 = new LRItem(new Production(A), 0, Arrays.asList(end));
    LRItem i23 = new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end));
    LRItem i24 = new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end));
    Set<LRItem> s2 = new HashSet<>(Arrays.asList(i20, i21, i22, i23, i24));

    LRItem i30 = new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end));
    LRItem i31 = new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end));
    LRItem i32 = new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end));
    Set<LRItem> s3 = new HashSet<>(Arrays.asList(i30, i31, i32));

    LRItem i40 = new LRItem(new Production(B, b), 1, Arrays.asList(a, b, end));
    Set<LRItem> s4 = new HashSet<>(Arrays.asList(i40));

    LRItem i50 = new LRItem(new Production(A, B, A), 2, Arrays.asList(end));
    Set<LRItem> s5 = new HashSet<>(Arrays.asList(i50));

    LRItem i60 = new LRItem(new Production(B, a, B), 2, Arrays.asList(a, b, end));
    Set<LRItem> s6 = new HashSet<>(Arrays.asList(i60));

    Set<Set<LRItem>> states = new HashSet<>(Arrays.asList(s0, s1, s2, s3, s4, s5, s6));
    Set<Set<LRItem>> acceptableStates = states;
    Set<Symbol> alphabet = new HashSet<>(Arrays.asList(S, A, B, a, b));
    Set<LRItem> initialState = s0;

    TransitionFunction<Set<LRItem>, Symbol> transitionFunction = new TransitionFunction<>();
    transitionFunction.addTransition(s0, A, s1);
    transitionFunction.addTransition(s0, a, s3);
    transitionFunction.addTransition(s0, B, s2);
    transitionFunction.addTransition(s0, b, s4);
    transitionFunction.addTransition(s2, B, s2);
    transitionFunction.addTransition(s2, A, s5);
    transitionFunction.addTransition(s2, b, s4);
    transitionFunction.addTransition(s2, a, s3);
    transitionFunction.addTransition(s3, a, s3);
    transitionFunction.addTransition(s3, b, s4);
    transitionFunction.addTransition(s3, B, s6);

    automaton =
        new Automaton<>(states, alphabet, transitionFunction, initialState, acceptableStates);

    actionTable = new HashMap<>();
    actionTable.put(new Pair<>(s0, a), new MoveAction<>(s3));
    actionTable.put(new Pair<>(s0, b), new MoveAction<>(s4));
    actionTable.put(new Pair<>(s0, end), new ReduceAction(new Production(A)));

    actionTable.put(new Pair<>(s1, end), new AcceptAction());

    actionTable.put(new Pair<>(s2, a), new MoveAction<>(s3));
    actionTable.put(new Pair<>(s2, b), new MoveAction<>(s4));
    actionTable.put(new Pair<>(s2, end), new ReduceAction(new Production(A)));

    actionTable.put(new Pair<>(s3, a), new MoveAction<>(s3));
    actionTable.put(new Pair<>(s3, b), new MoveAction<>(s4));

    actionTable.put(new Pair<>(s4, a), new ReduceAction(new Production(B, b)));
    actionTable.put(new Pair<>(s4, b), new ReduceAction(new Production(B, b)));
    actionTable.put(new Pair<>(s4, end), new ReduceAction(new Production(B, b)));

    actionTable.put(new Pair<>(s5, end), new ReduceAction(new Production(A, B, A)));

    actionTable.put(new Pair<>(s6, a), new ReduceAction(new Production(B, a, B)));
    actionTable.put(new Pair<>(s6, b), new ReduceAction(new Production(B, a, B)));
    actionTable.put(new Pair<>(s6, end), new ReduceAction(new Production(B, a, B)));

    newStateTable = new HashMap<>();
    newStateTable.put(new Pair<>(s0, B), new PutAction<>(s2));
    newStateTable.put(new Pair<>(s0, A), new PutAction<>(s1));
    newStateTable.put(new Pair<>(s2, B), new PutAction<>(s2));
    newStateTable.put(new Pair<>(s2, A), new PutAction<>(s5));
    newStateTable.put(new Pair<>(s3, B), new PutAction<>(s6));
  }

  /*
   * This example tests langdefs/kanon_gramatika.san
   */
  @Test
  public void buildActionTableTest() {
    Map<Pair<Set<LRItem>, TerminalSymbol>, Action> actualActionTable =
        TableBuilder.buildActionTable(automaton, new Production(S, A));
    assertEquals(actionTable, actualActionTable);
  }
  
  /*
   * This example tests langdefs/kanon_gramatika.san
   */
  @Test
  public void buildNewStateTableTest() {
    Map<Pair<Set<LRItem>, NonTerminalSymbol>, Action> actualNewStateTable =
        TableBuilder.buildNewStateTable(automaton);
    assertEquals(newStateTable, actualNewStateTable);
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void actionTableSerializerTest() throws IOException, ClassNotFoundException {
    ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(new FileOutputStream(ParserDeserializer.ACTION_TABLE));
    objectOutputStream.writeObject(actionTable);
    objectOutputStream.close();

    ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(ParserDeserializer.ACTION_TABLE));
    Map<Pair<Set<LRItem>, TerminalSymbol>, Action> actualActionTable =
        (Map<Pair<Set<LRItem>, TerminalSymbol>, Action>) objectInputStream.readObject();
    objectInputStream.close();

    assertEquals(actionTable, actualActionTable);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void newStateTableSerializerTest() throws IOException, ClassNotFoundException {
    ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(new FileOutputStream(ParserDeserializer.NEW_STATE_TABLE));
    objectOutputStream.writeObject(newStateTable);
    objectOutputStream.close();

    ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(ParserDeserializer.NEW_STATE_TABLE));
    Map<Pair<Set<LRItem>, NonTerminalSymbol>, Action> actualNewStateTable =
        (Map<Pair<Set<LRItem>, NonTerminalSymbol>, Action>) objectInputStream.readObject();
    objectInputStream.close();

    assertEquals(newStateTable, actualNewStateTable);
  }
}
