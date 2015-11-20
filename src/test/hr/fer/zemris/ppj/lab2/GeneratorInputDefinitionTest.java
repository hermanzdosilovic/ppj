package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class GeneratorInputDefinitionTest {

  @Test
  public void parseNonterminalSymbolsTest() throws Exception {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%V <jedan> <Drugi> <Treci_nezavrsni>");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseNonterminalSymbols();

    assertEquals(inputDefinition.getNonterminalSymbols(),
        Arrays.asList(new NonTerminalSymbol("<jedan>"), new NonTerminalSymbol("<Drugi>"),
            new NonTerminalSymbol("<Treci_nezavrsni>")));
  }

  @Test
  public void parseTerminalSymbolsTest() throws Exception {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%T jedan dva tri");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseTerminalSymbols();

    assertEquals(inputDefinition.getTerminalSymbols(), Arrays.asList(new TerminalSymbol("jedan"),
        new TerminalSymbol("dva"), new TerminalSymbol("tri")));
  }

  @Test
  public void parseSynchronousTerminalSymbolsTest() throws Exception {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%Syn tu sad neki znakovi");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseSynchronousTerminalSymbols();

    assertEquals(inputDefinition.getSynchronousTerminalSymbols(),
        Arrays.asList(new TerminalSymbol("tu"), new TerminalSymbol("sad"),
            new TerminalSymbol("neki"), new TerminalSymbol("znakovi")));
  }

  @Test
  public void parseProductionsTest() throws Exception {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("<znak>");
    inputLines.add(" $");
    inputLines.add(" <neki> znakovi");
    inputLines.add("<drugi_znak>");
    inputLines.add(" jos <malo> znakova");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseProductions();

    List<Production> expectedProductions = new ArrayList<Production>();
    Collection<Symbol> rightSide = new ArrayList<Symbol>();
    expectedProductions.add(new Production(new NonTerminalSymbol("<znak>")));
    rightSide.add(new NonTerminalSymbol("<neki>"));
    rightSide.add(new TerminalSymbol("znakovi"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<znak>"), rightSide));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("jos"));
    rightSide.add(new NonTerminalSymbol("<malo>"));
    rightSide.add(new TerminalSymbol("znakova"));
    expectedProductions
        .add(new Production(new NonTerminalSymbol("<drugi_znak>"), rightSide));
  }

  @Test
  public void basicInputTest() throws Exception {
    List<String> inputLines = new ArrayList<String>();

    inputLines.add("%V <A> <B> <C> <D> <E>");
    inputLines.add("%T a b c d e f");
    inputLines.add("%Syn a");
    inputLines.add("<A>");
    inputLines.add(" <B> <C> c");
    inputLines.add("<B>");
    inputLines.add(" $");
    inputLines.add(" b <C> <D> <E>");
    inputLines.add("<A>");
    inputLines.add(" e <D> <B>");
    inputLines.add("<C>");
    inputLines.add(" <D> a <B>");
    inputLines.add(" c a");
    inputLines.add("<D>");
    inputLines.add(" $");
    inputLines.add(" d <D>");
    inputLines.add("<E>");
    inputLines.add(" e <A> f");
    inputLines.add(" c");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.readDefinition();
    inputDefinition.parseDefinition();

    assertEquals(inputDefinition.getNonterminalSymbols(),
        Arrays.asList(new NonTerminalSymbol("<A>"), new NonTerminalSymbol("<B>"),
            new NonTerminalSymbol("<C>"), new NonTerminalSymbol("<D>"),
            new NonTerminalSymbol("<E>")));
    assertEquals(inputDefinition.getTerminalSymbols(),
        Arrays.asList(new TerminalSymbol("a"), new TerminalSymbol("b"),
            new TerminalSymbol("c"), new TerminalSymbol("d"),
            new TerminalSymbol("e"), new TerminalSymbol("f")));
    assertEquals(inputDefinition.getSynchronousTerminalSymbols(),
        Arrays.asList(new TerminalSymbol("a")));

    // assertEquals(inputDefinition.getProductions().,
    // Arrays.asList("<B> <C> c", "e <D> <B>"));
    // assertEquals(inputDefinition.getProductions().get("<B>"), Arrays.asList("$", "b <C> <D>
    // <E>"));
    // assertEquals(inputDefinition.getProductions().get("<C>"), Arrays.asList("<D> a <B>", "c a"));
    // assertEquals(inputDefinition.getProductions().get("<D>"), Arrays.asList("$", "d <D>"));
    // assertEquals(inputDefinition.getProductions().get("<E>"), Arrays.asList("e <A> f", "c"));

    List<Production> expectedProductions = new ArrayList<Production>();
    Collection<Symbol> rightSide = new ArrayList<Symbol>();
    rightSide.add(new NonTerminalSymbol("<B>"));
    rightSide.add(new NonTerminalSymbol("<C>"));
    rightSide.add(new TerminalSymbol("c"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<A>"), rightSide));
    expectedProductions.add(new Production(new NonTerminalSymbol("<B>")));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("b"));
    rightSide.add(new NonTerminalSymbol("<C>"));
    rightSide.add(new NonTerminalSymbol("<D>"));
    rightSide.add(new NonTerminalSymbol("<E>"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<B>"), rightSide));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("e"));
    rightSide.add(new NonTerminalSymbol("<D>"));
    rightSide.add(new NonTerminalSymbol("<B>"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<A>"), rightSide));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new NonTerminalSymbol("<D>"));
    rightSide.add(new TerminalSymbol("a"));
    rightSide.add(new NonTerminalSymbol("<B>"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<C>"), rightSide));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("c"));
    rightSide.add(new TerminalSymbol("a"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<C>"), rightSide));
    expectedProductions.add(new Production(new NonTerminalSymbol("<D>")));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("d"));
    rightSide.add(new NonTerminalSymbol("<D>"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<D>"), rightSide));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("e"));
    rightSide.add(new NonTerminalSymbol("<A>"));
    rightSide.add(new TerminalSymbol("f"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<E>"), rightSide));
    rightSide = new ArrayList<Symbol>();
    rightSide.add(new TerminalSymbol("c"));
    expectedProductions.add(new Production(new NonTerminalSymbol("<E>"), rightSide));

    assertEquals(expectedProductions, inputDefinition.getProductions());
  }

  public void getInitialNonTerminalSymbolTest() throws Exception {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%V <ja_sam_pocetni> <ostatak_ekipe>");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseNonterminalSymbols();

    assertEquals(new NonTerminalSymbol("<ja_sam_pocetni>"),
        inputDefinition.getInitialNonTerminalSymbol());
  }
}
