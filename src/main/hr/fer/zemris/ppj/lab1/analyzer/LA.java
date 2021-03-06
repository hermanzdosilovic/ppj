package hr.fer.zemris.ppj.lab1.analyzer;

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

import hr.fer.zemris.ppj.lab1.LexicalAnalyzerState;

public final class LA {
  private LexicalAnalyzerState initialLexicalAnalyzerState;
  private LexicalAnalyzerState currentLexicalAnalyzerState;
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;

  private String sourceCode;
  private int lineIndex = 1;
  private int left, right;
  private int lastGood;
  private Integer lastGoodAutomatonInd;

  private String lexicalUnitName;
  private int groupFrom;
  private int groupTo;

  private List<String> output = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LA lexicalAnalyzer =
        new LA(new FileInputStream(new File("analyzer_definition.ser")), System.in);
    lexicalAnalyzer.analyzeSourceCode();
    lexicalAnalyzer.printOutput();
  }

  public void analyzeSourceCode() {
    while (left < sourceCode.length()) {
      currentLexicalAnalyzerState.prepareForRun();
      lastGood = left - 1;
      right = left;
      lastGoodAutomatonInd = null;
      while (right < sourceCode.length()
          && currentLexicalAnalyzerState.readCharacter(sourceCode.charAt(right))) {
        Integer possibleAccept = currentLexicalAnalyzerState.getAutomatonIndex();
        if (possibleAccept != null) {
          lastGoodAutomatonInd = possibleAccept;
          lastGood = right;
        }
        right++;
      }
      if (lastGood >= left) {
        groupFrom = left;
        groupTo = lastGood + 1;
        AnalyzerAction.performAction(this);
        groupIntoLexicalUnit();
      } else {
        error();
      }
    }
  }

  private void error() {
    System.err.println("Error in line: " + lineIndex);
    left++;
  }

  private void groupIntoLexicalUnit() {
    if (lexicalUnitName != null) {
      output
          .add(lexicalUnitName + " " + lineIndex + " " + sourceCode.substring(groupFrom, groupTo));
    }
    left = groupTo;
  }

  public void newLine() {
    lineIndex++;
  }

  public void reject() {
    lexicalUnitName = null;
  }

  public void returnTo(int index) {
    groupTo = left + index;
  }

  public void setState(String stateName) {
    currentLexicalAnalyzerState = lexicalAnalyzerStateTable.get(stateName);
  }

  public LexicalAnalyzerState getState() {
    return currentLexicalAnalyzerState;
  }

  public Integer getAutomatonIndex() {
    return lastGoodAutomatonInd;
  }

  public void setLexicalUnit(String lexicalUnitName) {
    this.lexicalUnitName = lexicalUnitName;
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
    LexicalAnalyzerInputDefinition analyzerInputDefinition =
        new LexicalAnalyzerInputDefinition(definitionInputStream);
    setDefinitionObjects(analyzerInputDefinition);
  }

  public void readAnalyzerDefinition(List<String> definitionLines) {
    LexicalAnalyzerInputDefinition analyzerInputDefinition =
        new LexicalAnalyzerInputDefinition(definitionLines);
    setDefinitionObjects(analyzerInputDefinition);
  }

  private void setDefinitionObjects(LexicalAnalyzerInputDefinition analyzerInputDefinition) {
    initialLexicalAnalyzerState = analyzerInputDefinition.readInitialLexicalAnalyzerState();
    currentLexicalAnalyzerState = initialLexicalAnalyzerState;
    lexicalAnalyzerStateTable = analyzerInputDefinition.readLexicalAnalyzerStateDefinitions();
  }
}
