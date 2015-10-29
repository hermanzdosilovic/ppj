package hr.fer.zemris.ppj.analyizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.LexicalAnalyzerState;

public final class LA {
  private LexicalAnalyzerState initialLexicalAnalyzerState;
  private LexicalAnalyzerState currentLexicalAnalyzerState;
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;
  
  private List<String> sourceCode = new ArrayList<>();
  private Integer lineIndex;
  private Integer columnIndex;
  
  public static void main(String[] args) throws IOException {
    LA lexicalAnalyzer = new LA(new FileInputStream(new File("analyzer_definition.txt")), System.in);
  }

  public LA(InputStream definitionInputStream, InputStream sourceCodeInputStream) throws IOException {
    readAnalyzerDefinition(definitionInputStream);
    BufferedReader reader = new BufferedReader(new InputStreamReader(sourceCodeInputStream));
    String line;
    while((line = reader.readLine()) != null) {
      sourceCode.add(line);
    }
  }
  
  public LA(List<String> definitionLines, List<String> sourceCodeLines) {
    readAnalyzerDefinition(definitionLines);
    sourceCode = sourceCodeLines;
  }

  public void readAnalyzerDefinition(InputStream definitionInputStream) throws IOException {
    AnalyzerInputDefinition analyzerInputDefinition =
        new AnalyzerInputDefinition(definitionInputStream);
    setDefinitionObjects(analyzerInputDefinition);
  }
  
  public void readAnalyzerDefinition(List<String> definitionLines) {
    AnalyzerInputDefinition analyzerInputDefinition =
        new AnalyzerInputDefinition(definitionLines);
    setDefinitionObjects(analyzerInputDefinition);
  }
  
  private void setDefinitionObjects(AnalyzerInputDefinition analyzerInputDefinition) {
    initialLexicalAnalyzerState = analyzerInputDefinition.readInitialLexicalAnalyzerState();
    currentLexicalAnalyzerState = initialLexicalAnalyzerState;
    lexicalAnalyzerStateTable = analyzerInputDefinition.readLexicalAnalyzerStateDefinitions();
  }
}
