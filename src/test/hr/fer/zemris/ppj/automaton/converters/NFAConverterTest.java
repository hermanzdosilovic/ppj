package hr.fer.zemris.ppj.automaton.converters;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.converters.GrammarEpsilonNFAConverter;
import hr.fer.zemris.ppj.lab2.GeneratorInputDefinition;
import hr.fer.zemris.ppj.lab2.analyzer.SA;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class NFAConverterTest {

  @Test
  public void findAcceptableStatesTest() {
    Set<Integer> acceptibleStates = new HashSet<>();
    acceptibleStates.add(1);

    Set<Set<Integer>> states = new HashSet<Set<Integer>>();
    states.add(new HashSet<Integer>(Arrays.asList(0, 3, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 5, 7)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 4, 5)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 1, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 5, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 2, 1)));
    states.add(new HashSet<Integer>(Arrays.asList(4, 6, 5)));

    Set<Set<Integer>> expectedStates = new HashSet<Set<Integer>>();
    expectedStates.add(new HashSet<Integer>(Arrays.asList(2, 1, 3)));
    expectedStates.add(new HashSet<Integer>(Arrays.asList(3, 2, 1)));

    assertEquals(expectedStates, NFAConverter.findAcceptableStates(states, acceptibleStates));
  }

  @Test
  public void findInitialState() {
    Set<Set<Integer>> states = new HashSet<Set<Integer>>();
    states.add(new HashSet<Integer>(Arrays.asList(1, 2, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 3, 4)));
    states.add(new HashSet<Integer>(Arrays.asList(3, 4, 5)));
    states.add(new HashSet<Integer>(Arrays.asList(1, 2)));
    states.add(new HashSet<Integer>(Arrays.asList(2, 3)));
    states.add(new HashSet<Integer>(Arrays.asList(1)));
    states.add(new HashSet<Integer>(Arrays.asList(2)));

    assertEquals(new HashSet<>(Arrays.asList(new HashSet<Integer>(Arrays.asList(1)))),
        NFAConverter.findInitialState(new HashSet<>(Arrays.asList(1))));
  }

  @Test
  public void findTransitionsTest() {
    TransitionFunction<Integer, Integer> nkaTransitionFunction =
        new TransitionFunction<Integer, Integer>();
    nkaTransitionFunction.addTransition(0, 0, 0);
    nkaTransitionFunction.addTransition(0, 0, 1);
    nkaTransitionFunction.addTransition(0, 1, 1);
    nkaTransitionFunction.addTransition(1, 1, 0);
    nkaTransitionFunction.addTransition(1, 1, 1);

    TransitionFunction<Set<Integer>, Integer> dkaTransitionFunction =
        new TransitionFunction<Set<Integer>, Integer>();
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 1,
        new HashSet<Integer>(Arrays.asList(1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));

    Set<Set<Integer>> dkaStates = new HashSet<Set<Integer>>();
    dkaStates.add(new HashSet<Integer>(Arrays.asList()));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(1)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0, 1)));

    assertEquals(dkaTransitionFunction, NFAConverter.findTransitions(nkaTransitionFunction,
        dkaStates, new HashSet<Integer>(Arrays.asList(0, 1))));
  }

  @Test
  public void nfaConverterTest() {
    TransitionFunction<Integer, Integer> nkaTransitionFunction =
        new TransitionFunction<Integer, Integer>();
    nkaTransitionFunction.addTransition(0, 0, 0);
    nkaTransitionFunction.addTransition(0, 0, 1);
    nkaTransitionFunction.addTransition(0, 1, 1);
    nkaTransitionFunction.addTransition(1, 1, 0);
    nkaTransitionFunction.addTransition(1, 1, 1);

    TransitionFunction<Set<Integer>, Integer> dkaTransitionFunction =
        new TransitionFunction<Set<Integer>, Integer>();
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0)), 1,
        new HashSet<Integer>(Arrays.asList(1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 0,
        new HashSet<Integer>(Arrays.asList(0, 1)));
    dkaTransitionFunction.addTransition(new HashSet<Integer>(Arrays.asList(0, 1)), 1,
        new HashSet<Integer>(Arrays.asList(0, 1)));

    Set<Set<Integer>> dkaStates = new HashSet<Set<Integer>>();
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(1)));
    dkaStates.add(new HashSet<Integer>(Arrays.asList(0, 1)));

    Set<Set<Integer>> dkaAcceptableStates = new HashSet<Set<Integer>>();
    dkaAcceptableStates.add(new HashSet<Integer>(Arrays.asList(1)));
    dkaAcceptableStates.add(new HashSet<Integer>(Arrays.asList(0, 1)));

    Automaton<Integer, Integer> nka = new Automaton<Integer, Integer>(Arrays.asList(0, 1),
        Arrays.asList(0, 1), nkaTransitionFunction, 0, Arrays.asList(1));
    Automaton<Set<Integer>, Integer> dka = NFAConverter.convertToDFA(nka);
    Automaton<Set<Integer>, Integer> expectedDKA =
        new Automaton<Set<Integer>, Integer>(dkaStates, Arrays.asList(0, 1), dkaTransitionFunction,
            new HashSet<>(Arrays.asList(0)), dkaAcceptableStates);

    assertEquals(expectedDKA, dka);
  }

  @Test
  public void simplePpjGrammarTest() throws Exception {
    GeneratorInputDefinition generatorInputDefinition =
        new GeneratorInputDefinition(new FileInputStream(new File("io/lab-2/simplePpjLang.san")));

    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();

    Grammar grammar = Grammar.extendGrammar(generatorInputDefinition.getGrammar(),
        new NonTerminalSymbol(generatorInputDefinition.getInitialNonTerminalSymbol() + "'"));

    Automaton<LRItem, Symbol> eNFA =
        GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol(SA.END_STRING));
    assertEquals(3115, eNFA.getNumberOfStates());
    assertEquals(6343, eNFA.getNumberOfTransitions());

    Automaton<Set<LRItem>, Symbol> NFA = EpsilonNFAConverter.convertToNFA(eNFA);

    Automaton<Set<Set<LRItem>>, Symbol> DFA = NFAConverter.convertToDFA(NFA);
    assertEquals(691, DFA.getNumberOfStates());
    assertEquals(5404, DFA.getNumberOfTransitions());
  }
}
