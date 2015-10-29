package hr.fer.zemris.ppj.analyizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.LexicalAnalyzerState;
import hr.fer.zemris.ppj.regex.Regex;

public class AnalyzerInputDefinition {
  
  private List<String> inputLines;
  private int readerIndex;
  
  private LexicalAnalyzerState initialLexicalAnalyzerState;;
  
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;
  
  public AnalyzerInputDefinition(InputStream stream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String line;
    inputLines = new ArrayList<>();
    while((line = reader.readLine()) != null) {
      inputLines.add(line);
    }
  }
  
  public AnalyzerInputDefinition(List<String> inputLines) {
    this.inputLines = inputLines;
  }
  
  public LexicalAnalyzerState readInitialLexicalAnalyzerState() {
    initialLexicalAnalyzerState = new LexicalAnalyzerState(inputLines.get(readerIndex++).substring(3));
    return initialLexicalAnalyzerState;
  }
  
  public void readLexicalAnalyzerStateDefinitions() {
    while(readerIndex < inputLines.size()) {
      String lexicalAnalyzerStateName = inputLines.get(readerIndex++);
      LexicalAnalyzerState lexicalAnalyzerState = lexicalAnalyzerStateTable.get(lexicalAnalyzerStateName);
      if (lexicalAnalyzerState == null) {
        lexicalAnalyzerState = new LexicalAnalyzerState(lexicalAnalyzerStateName);
        lexicalAnalyzerStateTable.put(lexicalAnalyzerStateName, lexicalAnalyzerState);
      }
      
      Regex regex = new Regex(inputLines.get(readerIndex++));
      lexicalAnalyzerState.addAutomaton(regex.toAutomaton());
      
      readerIndex++; // skip blank line
    }
    
  }
  
  public LexicalAnalyzerState getInitialLexicalAnalyzerState() {
    return initialLexicalAnalyzerState;
  }

}
