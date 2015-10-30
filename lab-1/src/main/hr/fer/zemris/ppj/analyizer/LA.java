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
  private int lastGood;
  private Integer lastGoodAutomatonInd;
  
  private String lexicalUnitName;
  private int groupFrom;
  private int groupTo;

  private List<String> output = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    LA lexicalAnalyzer =
        new LA(new FileInputStream(new File("/Users/ikrpelnik/Documents/workspace/ppj/lab-1/analyzer_definition.txt")), 
            new FileInputStream(new File("/Users/ikrpelnik/Documents/workspace/ppj/lab-1/ml.txt")));
    System.out.println(lexicalAnalyzer.sourceCode);
    lexicalAnalyzer.analyzeSourceCode();
    lexicalAnalyzer.printOutput();
  }

  public void analyzeSourceCode() {
    while (left < sourceCode.length()) {
      System.out.println(currentLexicalAnalyzerState.getName() + " " + left);
      System.out.println(currentLexicalAnalyzerState.getAutomatons());
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
        Actions.action(this);
        groupIntoLexicalUnit();
      } else {
        error();
      }
    }
  }

  private void error() {
    System.out.println("Error in line: " + lineIndex);
    left++;
  }
  
  private void groupIntoLexicalUnit() {
    if (lexicalUnitName != null) {
      System.out.println("asdf");
      output.add(lexicalUnitName + " " + lineIndex + " " + sourceCode.substring(groupFrom, groupTo));
      left = groupTo + 1;
    } else {
      System.out.println("bla");
    }
  }
  
  public void newLine() {
    lineIndex++;
  }

  public void reject() {
    lexicalUnitName = null;
    left = lastGood + 1;
  }
  
  /*
   * left = index
   * grupiraj sve do index -> lg = index - 1
   */
  public void returnTo(int index) {
    left = index;
    groupTo = left;
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
//    System.out.println(this.sourceCode);
//    System.out.println(initialLexicalAnalyzerState.getName());
//    System.out.println(initialLexicalAnalyzerState.getAutomatons());
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
