package hr.fer.zemris.ppj.lab1;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.ppj.regex.RegularDefinition;

public class GeneratorInputDefinitionTest {
  
  @Test
  public void parseRegularDefinitionsTest() {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("{oktalnaZnamenka} 0|1|2|3|4|5|6|7");
    inputLines.add("{dekadskaZnamenka} {oktalnaZnamenka}|8|9");
    inputLines.add("{hexZnakmenka} a|b|c|d|e|f|{dekadskaZnamenka}|A|B|C|D|E|F");
    
    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.parseRegularDefinitions();
    
    List<RegularDefinition> regularDefinitions = generatorInputDefinition.getRegularDefinitions();
    assertEquals(3, regularDefinitions.size());
    assertEquals("oktalnaZnamenka", regularDefinitions.get(0).getName());
    assertEquals("dekadskaZnamenka", regularDefinitions.get(1).getName());
    assertEquals("hexZnakmenka", regularDefinitions.get(2).getName());
  }
  
  @Test
  public void parseLexicalStatesTest() {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%X State_0 State_1 State_2");
    
    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.parseLexicalStates();
    
    Collection<LexicalAnalyzerState> lexicalAnalyzerStates = generatorInputDefinition.getLexicalAnalyzerStates();
    assertEquals(3, lexicalAnalyzerStates.size());
  }
  
  @Test
  public void parseLexicalUnitsTest() {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%L IDENTIFIKATOR brojcanaKonstanta znakovnaKonstanta OP_PLUS");
    
    
    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.parseLexicalUnits();
    
    List<LexicalUnit> lexicalUnits = generatorInputDefinition.getLexicalUnits();
    assertEquals(4, lexicalUnits.size());
    assertEquals("IDENTIFIKATOR", lexicalUnits.get(0).getName());
    assertEquals("brojcanaKonstanta", lexicalUnits.get(1).getName());
    assertEquals("znakovnaKonstanta", lexicalUnits.get(2).getName());
    assertEquals("OP_PLUS", lexicalUnits.get(3).getName());
  }
}
