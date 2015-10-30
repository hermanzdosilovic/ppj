package hr.fer.zemris.ppj.analyizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.LexicalAnalyzerState;

public final class LA {
  private LexicalAnalyzerState initialLexicalAnalyzerState;
  private LexicalAnalyzerState currentLexicalAnalyzerState;
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;

  private String sourceCode;
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
    while (right < sourceCode.length()) {
      char character = sourceCode.charAt(right);
      if (currentLexicalAnalyzerState.readCharacter(character)) {
        right++;
      } else {
        currentLexicalAnalyzerState.reloadAndReadSequence(sourceCode.substring(left, right));
//        Action.actions(this); // call action resolver implemented by Ivan Trubić
      }
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
        .reloadAndReadSequence(sourceCode.substring(left, right));
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
    output.add(lexicalUnitName + " " + lineIndex + " " + sourceCode.charAt(right));
  }

  public void printOutput() {
    for (String line : output) {
      System.out.println(line);
    }
  }

  public List<String> getOutput() {
    return output;
  }
  
  public String getSourceCode() {
    return sourceCode;
  }
  
  public LA(InputStream definitionInputStream, InputStream sourceCodeInputStream)
      throws IOException {
    readAnalyzerDefinition(definitionInputStream);

    StringBuilder sourceCodeBuilder = new StringBuilder();
    Reader reader = new BufferedReader(new InputStreamReader(sourceCodeInputStream));
    int character;
    while ((character = reader.read()) != -1) {
      sourceCodeBuilder.append((char) character);
    }
    this.sourceCode = sourceCodeBuilder.toString();
  }

  public LA(List<String> definitionLines, String sourceCode) {
    readAnalyzerDefinition(definitionLines);
    this.sourceCode = sourceCode;
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
