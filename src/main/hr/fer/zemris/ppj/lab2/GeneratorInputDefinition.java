package hr.fer.zemris.ppj.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorInputDefinition {

  private List<String> inputLines;
  private List<String> nonterminalSymbols;
  private List<String> terminalSymbols;
  private List<String> synchronousTerminalSymbols;
  private Map<String, List<String>> productions;
  private int index = 0;

  public GeneratorInputDefinition() throws IOException {
    this(System.in);
  }

  public GeneratorInputDefinition(InputStream stream) throws IOException {
    inputLines = new ArrayList<String>();
    BufferedReader input = new BufferedReader(new InputStreamReader(stream));
    String line;
    while ((line = input.readLine()) != null) {
      inputLines.add(line);
    }
  }

  public GeneratorInputDefinition(List<String> inputLines) {
    this.inputLines = inputLines;
  }

  public void runGenerator() {
    parseNonterminalSymbols();
    parseTerminalSymbols();
    parseSynchronousTerminalSymbols();
    parseProductions();
  }

  void parseNonterminalSymbols() {
    nonterminalSymbols = new ArrayList<String>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      nonterminalSymbols.add(line[i]);
    }
  }

  void parseTerminalSymbols() {
    terminalSymbols = new ArrayList<String>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      terminalSymbols.add(line[i]);
    }
  }

  void parseSynchronousTerminalSymbols() {
    synchronousTerminalSymbols = new ArrayList<String>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      synchronousTerminalSymbols.add(line[i]);
    }
  }

  void parseProductions() {
    productions = new HashMap<String, List<String>>();
    while (index < inputLines.size()) {
      String leftSide = inputLines.get(index++);
      if (!productions.containsKey(leftSide)) {
        productions.put(leftSide, new ArrayList<>());
      }      
      while (index < inputLines.size() && inputLines.get(index).charAt(0) == ' ') {
        String rightSide = inputLines.get(index++);
        productions.get(leftSide).add(rightSide.substring(1));
      }
    }
  }

  public List<String> getNonterminalSymbols() {
    return nonterminalSymbols;
  }

  public List<String> getTerminalSymbols() {
    return terminalSymbols;
  }

  public List<String> getSynchronousTerminalSymbols() {
    return synchronousTerminalSymbols;
  }

  public Map<String, List<String>> getProductions() {
    return productions;
  }
}