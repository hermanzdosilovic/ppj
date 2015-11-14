package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class GeneratorInputDefinitionTest {

  @Test
  public void parseNonterminalSymbolsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%V <jedan> <Drugi> <Treci_nezavrsni>");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseNonterminalSymbols();

    assertEquals(inputDefinition.getNonterminalSymbols(),
        Arrays.asList("<jedan>", "<Drugi>", "<Treci_nezavrsni>"));
  }

  @Test
  public void parseTerminalSymbolsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%T jedan dva tri");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseTerminalSymbols();

    assertEquals(inputDefinition.getTerminalSymbols(), Arrays.asList("jedan", "dva", "tri"));
  }

  @Test
  public void parseSynchronousTerminalSymbolsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%Syn tu sad neki znakovi");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseSynchronousTerminalSymbols();

    assertEquals(inputDefinition.getSynchronousTerminalSymbols(),
        Arrays.asList("tu", "sad", "neki", "znakovi"));
  }

  @Test
  public void parseProductionsTest() {
    List<String> inputLines = new ArrayList<String>();
    inputLines.add("%V <znak> <drugi_znak>");
    inputLines.add("<znak>");
    inputLines.add(" $");
    inputLines.add(" neki znakovi");
    inputLines.add("<drugi_znak>");
    inputLines.add(" jos malo znakova");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition(inputLines);

    inputDefinition.parseNonterminalSymbols();
    inputDefinition.parseProductions();

    assertEquals(inputDefinition.getProductions().get("<znak>"), Arrays.asList("$", "neki znakovi"));
    assertEquals(inputDefinition.getProductions().get("<drugi_znak>"),
        Arrays.asList("jos malo znakova"));
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

    assertEquals(inputDefinition.getNonterminalSymbols(),
        Arrays.asList("<A>", "<B>", "<C>", "<D>", "<E>"));
    assertEquals(inputDefinition.getTerminalSymbols(), Arrays.asList("a", "b", "c", "d", "e", "f"));
    assertEquals(inputDefinition.getSynchronousTerminalSymbols(), Arrays.asList("a"));

    assertEquals(inputDefinition.getProductions().get("<A>"),
        Arrays.asList("<B> <C> c", "e <D> <B>"));
    assertEquals(inputDefinition.getProductions().get("<B>"), Arrays.asList("$", "b <C> <D> <E>"));
    assertEquals(inputDefinition.getProductions().get("<C>"), Arrays.asList("<D> a <B>", "c a"));
    assertEquals(inputDefinition.getProductions().get("<D>"), Arrays.asList("$", "d <D>"));
    assertEquals(inputDefinition.getProductions().get("<E>"), Arrays.asList("e <A> f", "c"));
  }
}
