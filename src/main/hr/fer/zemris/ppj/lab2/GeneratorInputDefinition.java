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

  private List<String> inputLines = new ArrayList<String>();
  private List<String> nonterminalSymbols = new ArrayList<String>();
  private List<String> terminalSymbols = new ArrayList<String>();
  private List<String> synchronousTerminalSymbols = new ArrayList<String>();
  private Map<String, List<String>> productions = new HashMap<String, List<String>>();
  private int index = 0;

  public GeneratorInputDefinition() throws IOException {
    this(System.in);
  }

  public GeneratorInputDefinition(InputStream stream) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(stream));
    String line = " ";
    while (line != null) {
      line = input.readLine();
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
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      nonterminalSymbols.add(line[i]);
    }
  }

  void parseTerminalSymbols() {
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      terminalSymbols.add(line[i]);
    }

  }

  void parseSynchronousTerminalSymbols() {
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      synchronousTerminalSymbols.add(line[i]);
    }
  }

  void parseProductions() {
    for (String simbol : nonterminalSymbols) {
      productions.put(simbol, new ArrayList<String>());
    }

    String line = " ";
    String leftSide = " ";
    line = inputLines.get(index++);
    while (line != null && index != inputLines.size()) {
      if (!line.startsWith(" ")) {
        leftSide = line;
        line = inputLines.get(index++);
        while (line != null && line.startsWith(" ")) {
          productions.get(leftSide).add(line.substring(1));
          if (index == inputLines.size())
            break;
          line = inputLines.get(index++);
        }
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
