package hr.fer.zemris.ppj.lab2.analyzer;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.GSA;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.PutAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.node.Node;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class SATest {
  private static NonTerminalSymbol S, A, B;
  private static TerminalSymbol a, b;
  private static TerminalSymbol end;
  private Map<Pair<Set<LRItem>, TerminalSymbol>, Action> actionTable;
  private Map<Pair<Set<LRItem>, NonTerminalSymbol>, Action> newStateTable;
  private Set<LRItem> initialState;

  @BeforeClass
  public static void createSymbols() {
    S = new NonTerminalSymbol("S");
    A = new NonTerminalSymbol("A");
    B = new NonTerminalSymbol("B");
    a = new TerminalSymbol("a");
    b = new TerminalSymbol("b");
    end = new TerminalSymbol(SA.END_STRING);
  }

  @Before
  public void buildTables() {
    LRItem i00 = new LRItem(new Production(S, A), 0, Arrays.asList(end));
    LRItem i01 = new LRItem(new Production(A, B, A), 0, Arrays.asList(end));
    LRItem i02 = new LRItem(new Production(A), 0, Arrays.asList(end));
    LRItem i03 = new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end));
    LRItem i04 = new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end));
    Set<LRItem> s0 = new HashSet<>(Arrays.asList(i00, i01, i02, i03, i04));

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

    initialState = s0;

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

  @Test
  public void basicTest() {
    List<String> input = new ArrayList<>();
    input.add("a 1 x x x");
    input.add("b 2 y y");
    input.add("a 3 xx xx");
    input.add("a 4 xx xx xx");
    input.add("b 4 y");
    input.add(SA.END_STRING + " 5 T");

    List<TerminalSymbol> syn = Arrays.asList(b);

    SA sa = new SA(actionTable, newStateTable, syn, initialState);
    StringBuilder expected = new StringBuilder();
    expected.append("A").append(System.lineSeparator()).append(" B").append(System.lineSeparator())
        .append("  a 1 x x x").append(System.lineSeparator()).append("  B")
        .append(System.lineSeparator()).append("   b 2 y y").append(System.lineSeparator())
        .append(" A").append(System.lineSeparator()).append("  B").append(System.lineSeparator())
        .append("   a 3 xx xx").append(System.lineSeparator()).append("   B")
        .append(System.lineSeparator()).append("    a 4 xx xx xx").append(System.lineSeparator())
        .append("    B").append(System.lineSeparator()).append("     b 4 y")
        .append(System.lineSeparator()).append("  A").append(System.lineSeparator()).append("   $")
        .append(System.lineSeparator());
    assertEquals(expected.toString(), Node.printTree(sa.LR(input)));
  }

  @Test
  public void minusLangTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/minusLang.san")));
    gsa.start();
    
    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/minusLang.in")));
    input.add(SA.END_STRING + " kraj T");
    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());
    
    Node node = sa.LR(input);
    
    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/minusLang.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }
    
    assertEquals(expectedOutput, Node.printTree(node));
  }
  
  @Test
  public void kanonGrammarTest() throws Exception {
//    GSA gsa = new GSA(new FileInputStream(new File("langdefs/kanon_gramatika.san")));
//    gsa.start();
    
    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/kanon_gramatika.in")));
    input.add(SA.END_STRING + " kraj T");
    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());
    
    Node node = sa.LR(input);
    
    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/kanon_gramatika.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }
    
    assertEquals(expectedOutput, Node.printTree(node));
  }
}
