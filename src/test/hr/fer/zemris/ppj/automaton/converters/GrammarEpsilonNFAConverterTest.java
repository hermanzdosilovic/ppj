package hr.fer.zemris.ppj.automaton.converters;

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
import hr.fer.zemris.ppj.grammar.coverters.GrammarEpsilonNFAConverter;
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
    NonTerminalSymbol<Character> S = new NonTerminalSymbol<>('S');
    NonTerminalSymbol<Character> A = new NonTerminalSymbol<>('A');
    NonTerminalSymbol<Character> B = new NonTerminalSymbol<>('B');
    TerminalSymbol<Character> a = new TerminalSymbol<>('a');
    TerminalSymbol<Character> b = new TerminalSymbol<>('b');

    TerminalSymbol<Character> end = new TerminalSymbol<>('\\');

    List<Production> productions = new ArrayList<>();
    productions.add(new Production(S, A));
    productions.add(new Production(A, B, A));
    productions.add(new Production(A));
    productions.add(new Production(B, a, B));
    productions.add(new Production(B, b));

    Grammar grammar = new Grammar(productions, S);
    
    LRItem initialState = new LRItem(new Production(null, new ArrayList<>()), 0, Arrays.asList());
    TransitionFunction<LRItem, Symbol<Character>> transitionFunction =
        new TransitionFunction<>();
    
    transitionFunction.addEpsilonTransition(
        initialState,
        new LRItem(new Production(S, A), 0, Arrays.asList(end)));
    
    transitionFunction.addTransition(new LRItem(new Production(S, A), 0, Arrays.asList(end)), A,
        new LRItem(new Production(S, A), 1, Arrays.asList(end)));
    
    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(S, A), 0, Arrays.asList(end)),
        new LRItem(new Production(A, B, A), 0, Arrays.asList(end)));
    
    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(S, A), 0, Arrays.asList(end)),
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
    
    transitionFunction.addTransition(new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end)), b,
        new LRItem(new Production(B, b), 1, Arrays.asList(a, b, end)));
    
    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)),
        new LRItem(new Production(B, a, B), 0, Arrays.asList(a, b, end)));
    
    transitionFunction.addEpsilonTransition(
        new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)),
        new LRItem(new Production(B, b), 0, Arrays.asList(a, b, end)));
    
    transitionFunction.addTransition(new LRItem(new Production(B, a, B), 1, Arrays.asList(a, b, end)), B,
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
    states.add(initialState);
    
    List<Symbol<Character>> alphabet = Arrays.asList(S, A, B, a, b);
    
    TransitionFunction<LRItem, Symbol<?>> actualTransitionFunction = new TransitionFunction<>();
    actualTransitionFunction.addEpsilonTransition(initialState, new LRItem(new Production(S, A), 0, Arrays.asList(end)));
    Set<LRItem> visited = new HashSet<>();
    visited.add(new LRItem(new Production(S, A), 0, Arrays.asList(end)));
    GrammarEpsilonNFAConverter.buildTransitions(new LRItem(new Production(S, A), 0, Arrays.asList(end)), actualTransitionFunction, grammar, visited);
    assertEquals(transitionFunction, actualTransitionFunction);
    
    Automaton<LRItem, Symbol<Character>> expectedAutomaton = new Automaton<>(states, alphabet, transitionFunction, initialState, acceptableStates);
    assertEquals(expectedAutomaton, GrammarEpsilonNFAConverter.convert(grammar, end));
  }
}
