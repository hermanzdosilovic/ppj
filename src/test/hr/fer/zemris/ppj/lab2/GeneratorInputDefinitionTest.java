package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class GeneratorInputDefinitionTest {
  @Test
  public void basicInputTest(){
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
    
    assertEquals(inputDefinition.getNonterminalSymbols(), Arrays.asList("<A>", "<B>", "<C>", "<D>", "<E>"));
    assertEquals(inputDefinition.getTerminalSymbols(), Arrays.asList("a", "b", "c", "d", "e", "f"));
    assertEquals(inputDefinition.getNonterminalSymbols(), Arrays.asList("a"));

    assertEquals(inputDefinition.getProductions().get("<A>"), Arrays.asList("<B> <C> c", "e <D> <B>"));
    assertEquals(inputDefinition.getProductions().get("<B>"), Arrays.asList("$", "b <C> <D> <E>"));
    assertEquals(inputDefinition.getProductions().get("<C>"), Arrays.asList("<D> a <B>", "c a"));
    assertEquals(inputDefinition.getProductions().get("<D>"), Arrays.asList("$", "d <D>"));
    assertEquals(inputDefinition.getProductions().get("<E>"), Arrays.asList("e <A> f", "c"));


    
  }
}
