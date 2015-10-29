package hr.fer.zemris.ppj.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

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
}
