package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.*;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class GeneratorInputDefinitionTest {

  @Test
  public void parseNonterminalSymbolsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%V <jedan> <Drugi> <Treci_nezavrsni>");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseNonterminalSymbols();

    assertEquals(inputDefinition.getNonterminalSymbols(), Arrays.asList(
        new NonTerminalSymbol<String>("<jedan>"), new NonTerminalSymbol<String>("<Drugi>"),
        new NonTerminalSymbol<String>("<Treci_nezavrsni>")));
  }

  @Test
  public void parseTerminalSymbolsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%T jedan dva tri");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseTerminalSymbols();

    assertEquals(inputDefinition.getTerminalSymbols(), Arrays.asList(new TerminalSymbol<String>(
        "jedan"), new TerminalSymbol<String>("dva"), new TerminalSymbol<String>("tri")));
  }

  @Test
  public void parseSynchronousTerminalSymbolsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%Syn tu sad neki znakovi");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseSynchronousTerminalSymbols();

    assertEquals(inputDefinition.getSynchronousTerminalSymbols(), Arrays.asList(
        new TerminalSymbol<String>("tu"), new TerminalSymbol<String>("sad"),
        new TerminalSymbol<String>("neki"), new TerminalSymbol<String>("znakovi")));
  }

  @Test
  public void parseProductionsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("<znak>");
    inputLines.add(" $");
    inputLines.add(" <neki> znakovi");
    inputLines.add("<drugi_znak>");
    inputLines.add(" jos <malo> znakova");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseProductions();

    List<Production> expectedProductions = new ArrayList<Production>();
    Collection<Symbol<?>> rightSide = new ArrayList<Symbol<?>>();
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<znak>")));
    rightSide.add(new NonTerminalSymbol<String>("<neki>"));
    rightSide.add(new TerminalSymbol<String>("znakovi"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<znak>"), rightSide));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("jos"));
    rightSide.add(new NonTerminalSymbol<String>("<malo>"));
    rightSide.add(new TerminalSymbol<String>("znakova"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<drugi_znak>"), rightSide));
  }

  @Test
  public void basicInputTest() {
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

    inputDefinition.runGenerator();

    assertEquals(inputDefinition.getNonterminalSymbols(), Arrays.asList(
        new NonTerminalSymbol<String>("<A>"), new NonTerminalSymbol<String>("<B>"),
        new NonTerminalSymbol<String>("<C>"), new NonTerminalSymbol<String>("<D>"),
        new NonTerminalSymbol<String>("<E>")));
    assertEquals(inputDefinition.getTerminalSymbols(), Arrays.asList(
        new TerminalSymbol<String>("a"), new TerminalSymbol<String>("b"),
        new TerminalSymbol<String>("c"), new TerminalSymbol<String>("d"),
        new TerminalSymbol<String>("e"), new TerminalSymbol<String>("f")));
    assertEquals(inputDefinition.getSynchronousTerminalSymbols(), Arrays.asList(new TerminalSymbol<String>("a")));

//    assertEquals(inputDefinition.getProductions().,
//        Arrays.asList("<B> <C> c", "e <D> <B>"));
//    assertEquals(inputDefinition.getProductions().get("<B>"), Arrays.asList("$", "b <C> <D> <E>"));
//    assertEquals(inputDefinition.getProductions().get("<C>"), Arrays.asList("<D> a <B>", "c a"));
//    assertEquals(inputDefinition.getProductions().get("<D>"), Arrays.asList("$", "d <D>"));
//    assertEquals(inputDefinition.getProductions().get("<E>"), Arrays.asList("e <A> f", "c"));
    
    List<Production> expectedProductions = new ArrayList<Production>();
    Collection<Symbol<?>> rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new NonTerminalSymbol<String>("<B>"));
    rightSide.add(new NonTerminalSymbol<String>("<C>"));
    rightSide.add(new TerminalSymbol<String>("c"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<A>"), rightSide));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<B>")));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("b"));
    rightSide.add(new NonTerminalSymbol<String>("<C>"));
    rightSide.add(new NonTerminalSymbol<String>("<D>"));
    rightSide.add(new NonTerminalSymbol<String>("<E>"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<B>"), rightSide));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("e"));
    rightSide.add(new NonTerminalSymbol<String>("<D>"));
    rightSide.add(new NonTerminalSymbol<String>("<B>"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<A>"), rightSide));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new NonTerminalSymbol<String>("<D>"));
    rightSide.add(new TerminalSymbol<String>("a"));
    rightSide.add(new NonTerminalSymbol<String>("<B>"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<C>"), rightSide));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("c"));
    rightSide.add(new TerminalSymbol<String>("a"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<C>"), rightSide));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<D>")));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("d"));
    rightSide.add(new NonTerminalSymbol<String>("<D>"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<D>"), rightSide));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("e"));
    rightSide.add(new NonTerminalSymbol<String>("<A>"));
    rightSide.add(new TerminalSymbol<String>("f"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<E>"), rightSide));
    rightSide = new ArrayList<Symbol<?>>();
    rightSide.add(new TerminalSymbol<String>("c"));
    expectedProductions.add(new Production(new NonTerminalSymbol<String>("<E>"), rightSide));
    
    assertEquals(expectedProductions, inputDefinition.getProductions());
  }
  
  public void getInitialNonTerminalSymbolTest(){
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%V <ja_sam_pocetni> <ostatak_ekipe>");
    
    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);
    
    inputDefinition.parseNonterminalSymbols();
    
    assertEquals(new NonTerminalSymbol<String>("<ja_sam_pocetni>"), inputDefinition.getInitialNonTerminalSymbol());
  }
}
