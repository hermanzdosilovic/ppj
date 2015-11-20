package hr.fer.zemris.ppj.lab2;

import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Trubic
 */
public class GeneratorInputDefinition {

  private List<String> inputLines;
  private InputStream inputStream;
  
  private List<NonTerminalSymbol> nonterminalSymbols;
  private NonTerminalSymbol initialNonTerminalSymbol;
  private List<TerminalSymbol> terminalSymbols;
  private List<TerminalSymbol> synchronousTerminalSymbols;
  private List<Production> productions;
  private int index = 0;

  public GeneratorInputDefinition() throws IOException {
    this(System.in);
  }

  public GeneratorInputDefinition(InputStream stream) throws IOException {
    if (stream == null) {
      throw new IllegalArgumentException("stream cannot be null");
    }
    inputStream = stream;
  }

  public GeneratorInputDefinition(List<String> inputLines) {
    if (inputLines == null) {
      throw new IllegalArgumentException("input lines cannot be null");
    }
    this.inputLines = inputLines;
  }

  public void readDefinition() throws IOException {
    if (inputStream != null) {
      inputLines = new ArrayList<String>();
      BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = input.readLine()) != null) {
        inputLines.add(line);
      }
    }
  }
  
  public void parseDefinition() throws Exception {
    if (inputLines == null) {
      throw new Exception("definition must be read before parsing");
    }
    
    parseNonterminalSymbols();
    parseTerminalSymbols();
    parseSynchronousTerminalSymbols();
    parseProductions();
  }
  
  void parseNonterminalSymbols() throws Exception {
    if (inputLines == null) {
      throw new Exception("definition must be read before parsing");
    }
    
    nonterminalSymbols = new ArrayList<NonTerminalSymbol>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      nonterminalSymbols.add(new NonTerminalSymbol(line[i]));
    }
    initialNonTerminalSymbol = new NonTerminalSymbol(nonterminalSymbols.get(0).getValue());
  }

  void parseTerminalSymbols() throws Exception {
    if (inputLines == null) {
      throw new Exception("definition must be read before parsing");
    }
    
    terminalSymbols = new ArrayList<TerminalSymbol>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      terminalSymbols.add(new TerminalSymbol(line[i]));
    }
  }

  void parseSynchronousTerminalSymbols() throws Exception {
    if (inputLines == null) {
      throw new Exception("definition must be read before parsing");
    }
    
    synchronousTerminalSymbols = new ArrayList<TerminalSymbol>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      synchronousTerminalSymbols.add(new TerminalSymbol(line[i]));
    }
  }

  void parseProductions() throws Exception {
    if (inputLines == null) {
      throw new Exception("definition must be read before parsing");
    }
    
    productions = new ArrayList<Production>();
    while (index < inputLines.size()) {
      String leftSide = inputLines.get(index++);
      while (index < inputLines.size() && inputLines.get(index).charAt(0) == ' ') {
        String rightSide = inputLines.get(index++);
        List<Symbol> symbols = new ArrayList<Symbol>();
        for(String symbol : rightSide.substring(1).split(" ")){
          if(symbol.charAt(0) == '<'){
            symbols.add(new NonTerminalSymbol(symbol));
          }
          else if(!symbol.equals("$")){
            symbols.add(new TerminalSymbol(symbol));
          }
        }
        if(rightSide.contains("$"))
          productions.add(new Production(new NonTerminalSymbol(leftSide)));
        else
          productions.add(new Production(new NonTerminalSymbol(leftSide), symbols));
      }
    }
  }

  public List<NonTerminalSymbol> getNonterminalSymbols() {
    return nonterminalSymbols;
  }

  public List<TerminalSymbol> getTerminalSymbols() {
    return terminalSymbols;
  }

  public List<TerminalSymbol> getSynchronousTerminalSymbols() {
    return synchronousTerminalSymbols;
  }

  public List<Production> getProductions() {
    return productions;
  }
  
  public NonTerminalSymbol getInitialNonTerminalSymbol(){
    return initialNonTerminalSymbol;
  }
  
  public Grammar getGrammar(){
    return new Grammar(productions, initialNonTerminalSymbol);
  }
}
