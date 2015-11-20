package hr.fer.zemris.ppj.grammar.converters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.automaton.TransitionFunction;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.GeneratorInputDefinition;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class GrammarEpsilonNFAConverterTest {

  @Test
  public void convertTest() {
    NonTerminalSymbol S = new NonTerminalSymbol('S');
    NonTerminalSymbol A = new NonTerminalSymbol('A');
    NonTerminalSymbol B = new NonTerminalSymbol('B');
    TerminalSymbol a = new TerminalSymbol('a');
    TerminalSymbol b = new TerminalSymbol('b');

    TerminalSymbol end = new TerminalSymbol('\\');

    List<Production> productions = new ArrayList<>();
    productions.add(new Production(S, A));
    productions.add(new Production(A, B, A));
    productions.add(new Production(A));
    productions.add(new Production(B, a, B));
    productions.add(new Production(B, b));

    Grammar grammar = new Grammar(productions, S);

    LRItem initialState = new LRItem(new Production(S, A), 0, Arrays.asList(end));
    TransitionFunction<LRItem, Symbol> transitionFunction = new TransitionFunction<>();

    transitionFunction.addTransition(new LRItem(new Production(S, A), 0, Arrays.asList(end)), A,
        new LRItem(new Production(S, A), 1, Arrays.asList(end)));

    transitionFunction.addEpsilonTransition(new LRItem(new Production(S, A), 0, Arrays.asList(end)),
        new LRItem(new Production(A, B, A), 0, Arrays.asList(end)));

    transitionFunction.addEpsilonTransition(new LRItem(new Production(S, A), 0, Arrays.asList(end)),
        new LRItem(new Production(A), 0, Arrays.asList(end)));

    transitionFunction.addTransition(new LRItem(new Production(A, B, A), 0, Arrays.asList(end)), B,
        new LRItem(new Production(A, B, A), 1, Arrays.asList(end)));

    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(A, B, A), 0, Arrays.asList(end)),
        new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end)));

    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(A, B, A), 0, Arrays.asList(end)),
        new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end)));

    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(A, B, A), 1, Arrays.asList(end)),
        new LRItem(new Production(A, B, A), 0, Arrays.asList(end)));

    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(A, B, A), 1, Arrays.asList(end)),
        new LRItem(new Production(A), 0, Arrays.asList(end)));

    transitionFunction.addTransition(new LRItem(new Production(A, B, A), 1, Arrays.asList(end)), A,
        new LRItem(new Production(A, B, A), 2, Arrays.asList(end)));

    transitionFunction.addTransition(
        new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end)), a,
        new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)));

    transitionFunction.addTransition(new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end)),
        b, new LRItem(new Production(B, b), 1, Arrays.asList(a, b, end)));

    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)),
        new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end)));

    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)),
        new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end)));

    transitionFunction.addTransition(
        new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)), B,
        new LRItem(new Production(B, a, B), 2, Arrays.asList(a, b, end)));

    Set<LRItem> acceptableStates = new HashSet<>();
    acceptableStates.add(new LRItem(new Production(S, A), 0, Arrays.asList(end)));
    acceptableStates.add(new LRItem(new Production(S, A), 1, Arrays.asList(end)));
    acceptableStates.add(new LRItem(new Production(A, B, A), 0, Arrays.asList(end)));
    acceptableStates.add(new LRItem(new Production(A), 0, Arrays.asList(end)));
    acceptableStates.add(new LRItem(new Production(A, B, A), 1, Arrays.asList(end)));
    acceptableStates.add(new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end)));
    acceptableStates.add(new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end)));
    acceptableStates.add(new LRItem(new Production(A, B, A), 2, Arrays.asList(end)));
    acceptableStates.add(new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)));
    acceptableStates.add(new LRItem(new Production(B, b), 1, Arrays.asList(a, b, end)));
    acceptableStates.add(new LRItem(new Production(B, a, B), 2, Arrays.asList(a, b, end)));

    Set<LRItem> states = new HashSet<>(acceptableStates);

    List<Symbol> alphabet = Arrays.asList(S, A, B, a, b);

    TransitionFunction<LRItem, Symbol> actualTransitionFunction = new TransitionFunction<>();
    Set<LRItem> visited = new HashSet<>();
    visited.add(new LRItem(new Production(S, A), 0, Arrays.asList(end)));
    GrammarEpsilonNFAConverter.buildTransitions(
        new LRItem(new Production(S, A), 0, Arrays.asList(end)), actualTransitionFunction, grammar,
        visited);
    assertEquals(transitionFunction, actualTransitionFunction);

    Automaton<LRItem, Symbol> expectedAutomaton =
        new Automaton<>(states, alphabet, transitionFunction, initialState, acceptableStates);
    assertEquals(expectedAutomaton, GrammarEpsilonNFAConverter.convert(grammar, end));
  }

  @Test
  public void kanonGrammarTest() throws Exception {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%V <A> <B>");
    inputLines.add("%T a b");
    inputLines.add("%Syn b");
    inputLines.add("<A>");
    inputLines.add(" <B> <A>");
    inputLines.add("<B>");
    inputLines.add(" a <B>");
    inputLines.add(" b");
    inputLines.add("<A>");
    inputLines.add(" $");
    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();
    
    Grammar grammar = Grammar.extendGrammar(generatorInputDefinition.getGrammar(), new NonTerminalSymbol("<%>"));
    Automaton<LRItem, Symbol> eNFA = GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol("END"));
    assertEquals(11, eNFA.getNumberOfStates());
    assertEquals(14, eNFA.getNumberOfTransitions());
  }
  
  @Test
  public void minusLangGrammarTest() throws Exception {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%V <expr> <atom>");
    inputLines.add("%T OPERAND OP_MINUS UMINUS LIJEVA_ZAGRADA DESNA_ZAGRADA");
    inputLines.add("%Syn OPERAND UMINUS LIJEVA_ZAGRADA");
    inputLines.add("<atom>");
    inputLines.add(" OPERAND");
    inputLines.add(" UMINUS <atom>");
    inputLines.add(" LIJEVA_ZAGRADA <expr> DESNA_ZAGRADA");
    inputLines.add("<expr>");
    inputLines.add(" <atom>");
    inputLines.add(" <expr> OP_MINUS <atom>");
    
    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();
    
    Grammar grammar = Grammar.extendGrammar(generatorInputDefinition.getGrammar(), new NonTerminalSymbol("<%>"));
    Automaton<LRItem, Symbol> eNFA = GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol("END"));
    assertEquals(47 , eNFA.getNumberOfStates());
    assertEquals(72, eNFA.getNumberOfTransitions());
  }
}
