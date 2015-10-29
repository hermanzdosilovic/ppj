package hr.fer.zemris.ppj.analyzer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hr.fer.zemris.ppj.LexicalAnalyzerState;
import hr.fer.zemris.ppj.analyizer.AnalyzerInputDefinition;

public class AnalyzerInputDefinitionTest {

  @Test
  public void readInitialAnalyzerStateTest() {
    LexicalAnalyzerState initialLexicalAnalyzerState = new LexicalAnalyzerState("S_initial");
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%s " + initialLexicalAnalyzerState.getName());

    AnalyzerInputDefinition analyzerInputDefinition = new AnalyzerInputDefinition(inputLines);
    assertEquals(initialLexicalAnalyzerState.getName(),
        analyzerInputDefinition.readInitialLexicalAnalyzerState().getName());
  }
  
  @Test
  public void readLexicalAnalyzerStateDefinitionsTest() {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("S_1");
    inputLines.add("a|b|c");
    inputLines.add(" ");
    inputLines.add("S_1");
    inputLines.add("abc|(123)*");
    inputLines.add(" ");
    inputLines.add("S_2");
    inputLines.add("ab|c");
    inputLines.add(" ");
    
    AnalyzerInputDefinition analyzerInputDefinition = new AnalyzerInputDefinition(inputLines);
    Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable = analyzerInputDefinition.readLexicalAnalyzerStateDefinitions();
    
    assertEquals(2, lexicalAnalyzerStateTable.keySet().size());
    assertEquals(2, lexicalAnalyzerStateTable.get("S_1").getAutomatons().size());
    assertEquals(1, lexicalAnalyzerStateTable.get("S_2").getAutomatons().size());
  }
}
