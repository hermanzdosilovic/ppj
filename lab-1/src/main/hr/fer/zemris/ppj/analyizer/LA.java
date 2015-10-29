package hr.fer.zemris.ppj.analyizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.ppj.LexicalAnalyzerState;

public final class LA {
  private LexicalAnalyzerState initialLexicalAnalyzerState;
  private LexicalAnalyzerState currentLexicalAnalyzerState;
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    LA lexicalAnalyzer = new LA();
    lexicalAnalyzer.readAnalyzerInputDefinition();
  }

  public void readAnalyzerInputDefinition() throws FileNotFoundException, IOException {
    AnalyzerInputDefinition analyzerInputDefinition =
        new AnalyzerInputDefinition(new FileInputStream(new File("analyzer_definition.txt")));
    initialLexicalAnalyzerState = analyzerInputDefinition.readInitialLexicalAnalyzerState();
    lexicalAnalyzerStateTable = analyzerInputDefinition.readLexicalAnalyzerStateDefinitions();
  }
}
