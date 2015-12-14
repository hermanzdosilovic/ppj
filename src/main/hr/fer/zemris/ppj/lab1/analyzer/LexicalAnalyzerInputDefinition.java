package hr.fer.zemris.ppj.lab1.analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.lab1.LexicalAnalyzerState;
import hr.fer.zemris.ppj.regex.Regex;

public class LexicalAnalyzerInputDefinition {
  private List<String> inputLines;
  private int readerIndex;

  private LexicalAnalyzerState initialLexicalAnalyzerState;;
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable = new HashMap<>();

  public LexicalAnalyzerInputDefinition(InputStream stream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String line;
    inputLines = new ArrayList<>();
    while ((line = reader.readLine()) != null) {
      inputLines.add(line);
    }
  }

  public LexicalAnalyzerInputDefinition(List<String> inputLines) {
    this.inputLines = inputLines;
  }

  public void readAnalyzerDefinition() {
    readInitialLexicalAnalyzerState();
    readLexicalAnalyzerStateDefinitions();
  }

  public LexicalAnalyzerState readInitialLexicalAnalyzerState() {
    initialLexicalAnalyzerState = new LexicalAnalyzerState(inputLines.get(readerIndex++));
    lexicalAnalyzerStateTable.put(initialLexicalAnalyzerState.getName(),
        initialLexicalAnalyzerState);
    return initialLexicalAnalyzerState;
  }

  public Map<String, LexicalAnalyzerState> readLexicalAnalyzerStateDefinitions() {
    while (readerIndex < inputLines.size()) {
      String lexicalAnalyzerStateName = inputLines.get(readerIndex++);
      LexicalAnalyzerState lexicalAnalyzerState =
          lexicalAnalyzerStateTable.get(lexicalAnalyzerStateName);
      if (lexicalAnalyzerState == null) {
        lexicalAnalyzerState = new LexicalAnalyzerState(lexicalAnalyzerStateName);
        lexicalAnalyzerStateTable.put(lexicalAnalyzerStateName, lexicalAnalyzerState);
      }

      Regex regex = new Regex(inputLines.get(readerIndex++));
      lexicalAnalyzerState.addAutomaton(regex.toAutomaton());
    }
    return lexicalAnalyzerStateTable;
  }

  public LexicalAnalyzerState getInitialLexicalAnalyzerState() {
    return initialLexicalAnalyzerState;
  }

  public Map<String, LexicalAnalyzerState> getLexicalAnalyzerStateTable() {
    return lexicalAnalyzerStateTable;
  }
}
