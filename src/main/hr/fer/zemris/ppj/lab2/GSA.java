package hr.fer.zemris.ppj.lab2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.converters.GrammarEpsilonNFAConverter;
import hr.fer.zemris.ppj.helpers.Stopwatch;
import hr.fer.zemris.ppj.lab2.analyzer.SA;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.LRState;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public final class GSA {
  private GeneratorInputDefinition generatorInputDefinition;
  private Automaton<LRState, Symbol> DFA;
  private Automaton<LRItem, Symbol> eNFA;

  public static void main(String[] args) throws Exception {
    GSA gsa = new GSA();
    gsa.start();
  }

  public GSA() throws IOException {
    this(System.in);
  }

  public GSA(InputStream stream) throws IOException {
    generatorInputDefinition = new GeneratorInputDefinition(stream);
  }

  public GSA(List<String> inputLines) {
    generatorInputDefinition = new GeneratorInputDefinition(inputLines);
  }

  public void start() throws Exception {
    Stopwatch.start();

    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();

    Grammar grammar = Grammar.extendGrammar(generatorInputDefinition.getGrammar(),
        new NonTerminalSymbol(generatorInputDefinition.getInitialNonTerminalSymbol() + "++"));

    String time;
    Stopwatch.start();
    eNFA = GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol(SA.END_STRING));
    time = Stopwatch.end();
    System.err.println("eNFA:\n states: " + eNFA.getNumberOfStates() + "\n transitions: "
        + eNFA.getNumberOfTransitions() + "\n time: " + time);

    Stopwatch.start();
    Automaton<LRState, Symbol> NFA = AutomatonConverter.convertToNFA(eNFA);
    time = Stopwatch.end();
    System.err.println("\nNFA:\n states: " + NFA.getNumberOfStates() + "\n transitions: "
        + NFA.getNumberOfTransitions() + "\n time: " + time);

    Stopwatch.start();
    DFA = AutomatonConverter.convertToDFA(NFA);
    time = Stopwatch.end();
    System.err.println("\nDFA:\n states: " + DFA.getNumberOfStates() + "\n transitions: "
        + DFA.getNumberOfTransitions() + "\n time: " + time);

    LRItem initialCompleteLRItem = createInitialCompleteLRItem(grammar.getInitialProduction());
    Stopwatch.start();
    Map<Pair<LRState, TerminalSymbol>, Action> actionTable =
        TableBuilder.buildActionTable(DFA, initialCompleteLRItem);
    time = Stopwatch.end();
    System.err.println("\nActionTable:\n time: " + time);

    Stopwatch.start();
    Map<Pair<LRState, NonTerminalSymbol>, Action> newStateTable =
        TableBuilder.buildNewStateTable(DFA);
    time = Stopwatch.end();
    System.err.println("\nNewStateTable:\n time: " + time);

    serialize(actionTable, ParserDeserializer.ACTION_TABLE);
    serialize(newStateTable, ParserDeserializer.NEW_STATE_TABLE);
    serialize(DFA.getInitialState(), ParserDeserializer.START_STATE);
    serialize(generatorInputDefinition.getSynchronousTerminalSymbols(),
        ParserDeserializer.SYN_STRINGS);
    System.err.println("\n - GSA: Total Time: " + Stopwatch.end());
  }

  public Automaton<LRItem, Symbol> getENFA() {
    return eNFA;
  }

  public Automaton<LRState, Symbol> getDFA() {
    return DFA;
  }

  void serialize(Object object, String path) throws IOException {
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
    objectOutputStream.writeObject(object);
    objectOutputStream.close();
  }

  private LRItem createInitialCompleteLRItem(Production initialProduction) {
    return new LRItem(initialProduction, initialProduction.getRightSide().size(),
        Arrays.asList((Symbol) new TerminalSymbol(SA.END_STRING)));
  }
}
