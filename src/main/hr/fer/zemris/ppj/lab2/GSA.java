package hr.fer.zemris.ppj.lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.converters.EpsilonNFAConverter;
import hr.fer.zemris.ppj.automaton.converters.NFAConverter;
import hr.fer.zemris.ppj.automaton.minimizers.DFAMinimizer;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.grammar.converters.GrammarEpsilonNFAConverter;
import hr.fer.zemris.ppj.helpers.Stopwatch;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public final class GSA {

  private GeneratorInputDefinition generatorInputDefinition;

  public static void main(String[] args) throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("simplePpjLang.san")));
    // GSA gsa = new GSA(new FileInputStream(new File("example.san")));

    Stopwatch.start();
    gsa.start();
    System.err.println("\n - Total Time: " + Stopwatch.end());
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
    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();

    List<Production> productions = generatorInputDefinition.getProductions();
    NonTerminalSymbol initialNonTerminalSymbol =
        generatorInputDefinition.getInitialNonTerminalSymbol();
    NonTerminalSymbol newInitialNonTerminalSymbol =
        new NonTerminalSymbol(initialNonTerminalSymbol + "'");
    productions.add(new Production(newInitialNonTerminalSymbol, initialNonTerminalSymbol));

    Grammar grammar = new Grammar(productions, newInitialNonTerminalSymbol);

    String time;
    Stopwatch.start();
    Automaton<LRItem, Symbol> eNFA =
        GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol("<posljednji_znakic>"));
    time = Stopwatch.end();
    System.err.println("eNFA:\n states: " + eNFA.getNumberOfStates() + "\n transitions: "
        + eNFA.getNumberOfTransitions() + "\n time: " + time);

    Stopwatch.start();
    Automaton<LRItem, Symbol> NFA = EpsilonNFAConverter.convertToNFA(eNFA);
    time = Stopwatch.end();
    System.err.println("\nNFA:\n states: " + NFA.getNumberOfStates() + "\n transitions: "
        + NFA.getNumberOfTransitions() + "\n time: " + time);

    Stopwatch.start();
    Automaton<Set<LRItem>, Symbol> DFA = NFAConverter.convertToDFA(NFA);
    time = Stopwatch.end();
    System.err.println("\nDFA:\n states: " + DFA.getNumberOfStates() + "\n transitions: "
        + DFA.getNumberOfTransitions() + "\n time: " + time);

    Stopwatch.start();
    Automaton<Set<Set<LRItem>>, Symbol> minDFA = DFAMinimizer.minimize(DFA);
    time = Stopwatch.end();
    System.err.println("\nminDFA:\n states: " + minDFA.getNumberOfStates() + "\n transitions: "
        + minDFA.getNumberOfTransitions() + "\n time: " + time);
  }
}