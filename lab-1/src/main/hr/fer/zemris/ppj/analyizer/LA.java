package hr.fer.zemris.ppj.analyizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
  private int lineIndex;
  private int left, right;

  private List<String> output = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LA lexicalAnalyzer =
        new LA(new FileInputStream(new File("analyzer_definition.txt")), System.in);
    lexicalAnalyzer.analyzeSourceCode();
    lexicalAnalyzer.printOutput();
  }

  public void analyzeSourceCode() {
    currentLexicalAnalyzerState.prepareForRun();
    lineIndex = 0;
    while (lineIndex < sourceCode.size()) {
      String line = sourceCode.get(lineIndex);
      while (right < line.length()) {
        char character = line.charAt(right);
        if (currentLexicalAnalyzerState.readCharacter(character)) {
          right++;
        } else {
          currentLexicalAnalyzerState.reloadAndReadSequence(line.substring(left, right));
          /*
           * call action resolver implemented by Ivan Trubić
           */
        }
      }
      lineIndex++;
    }
  }

  public void newLine() {
    lineIndex++;
  }

  public void reject() {
    right++;
    left = right;
  }

  public void returnTo(int index) {
    right = index;
    currentLexicalAnalyzerState
        .reloadAndReadSequence(sourceCode.get(lineIndex).substring(left, right));
  }

  public LexicalAnalyzerState setState(String stateName) {
    currentLexicalAnalyzerState = lexicalAnalyzerStateTable.get(stateName);
    currentLexicalAnalyzerState.prepareForRun();
    return currentLexicalAnalyzerState;
  }

  public LexicalAnalyzerState getState() {
    return currentLexicalAnalyzerState;
  }

  public int getAutomatonIndex() {
    return currentLexicalAnalyzerState.getAutomatonIndex();
  }

  public void setLexicalUnit(String lexicalUnitName) {
    output.add(lexicalUnitName + " " + lineIndex + " " + sourceCode.get(lineIndex).charAt(right));
  }

  public void printOutput() {
    for (String line : output) {
      System.out.println(line);
    }
  }

  public List<String> getOutput() {
    return output;
  }

  public LA(InputStream definitionInputStream, InputStream sourceCodeInputStream)
      throws IOException {
    readAnalyzerDefinition(definitionInputStream);
    BufferedReader reader = new BufferedReader(new InputStreamReader(sourceCodeInputStream));
    String line;
    while ((line = reader.readLine()) != null) {
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
    AnalyzerInputDefinition analyzerInputDefinition = new AnalyzerInputDefinition(definitionLines);
    setDefinitionObjects(analyzerInputDefinition);
  }

  private void setDefinitionObjects(AnalyzerInputDefinition analyzerInputDefinition) {
    initialLexicalAnalyzerState = analyzerInputDefinition.readInitialLexicalAnalyzerState();
    currentLexicalAnalyzerState = initialLexicalAnalyzerState;
    lexicalAnalyzerStateTable = analyzerInputDefinition.readLexicalAnalyzerStateDefinitions();
  }
}
