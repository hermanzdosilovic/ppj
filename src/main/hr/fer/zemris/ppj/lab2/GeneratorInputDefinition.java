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


public class GeneratorInputDefinition {

  private List<String> inputLines;
  private List<NonTerminalSymbol<String>> nonterminalSymbols;
  private NonTerminalSymbol<String> initialNonTerminalSymbol;
  private List<TerminalSymbol<String>> terminalSymbols;
  private List<TerminalSymbol<String>> synchronousTerminalSymbols;
  private List<Production> productions;
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
    nonterminalSymbols = new ArrayList<NonTerminalSymbol<String>>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      nonterminalSymbols.add(new NonTerminalSymbol<String>(line[i]));
    }
    initialNonTerminalSymbol = new NonTerminalSymbol<String>(nonterminalSymbols.get(0).getValue());
  }

  void parseTerminalSymbols() {
    terminalSymbols = new ArrayList<TerminalSymbol<String>>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      terminalSymbols.add(new TerminalSymbol<String>(line[i]));
    }
  }

  void parseSynchronousTerminalSymbols() {
    synchronousTerminalSymbols = new ArrayList<TerminalSymbol<String>>();
    String[] line = inputLines.get(index++).split(" ");
    for (int i = 1; i < line.length; i++) {
      synchronousTerminalSymbols.add(new TerminalSymbol<String>(line[i]));
    }
  }

  void parseProductions() {
    productions = new ArrayList<Production>();
    while (index < inputLines.size()) {
      String leftSide = inputLines.get(index++);
      while (index < inputLines.size() && inputLines.get(index).charAt(0) == ' ') {
        String rightSide = inputLines.get(index++);
        List<Symbol<?>> symbols = new ArrayList<Symbol<?>>();
        for(String symbol : rightSide.substring(1).split(" ")){
          if(symbol.charAt(0) == '<'){
            symbols.add(new NonTerminalSymbol<String>(symbol));
          }
          else if(!symbol.equals("$")){
            symbols.add(new TerminalSymbol<String>(symbol));
          }
        }
        if(rightSide.contains("$"))
          productions.add(new Production(new NonTerminalSymbol<String>(leftSide)));
        else
          productions.add(new Production(new NonTerminalSymbol<String>(leftSide), symbols));
      }
    }
  }

  public List<NonTerminalSymbol<String>> getNonterminalSymbols() {
    return nonterminalSymbols;
  }

  public List<TerminalSymbol<String>> getTerminalSymbols() {
    return terminalSymbols;
  }

  public List<TerminalSymbol<String>> getSynchronousTerminalSymbols() {
    return synchronousTerminalSymbols;
  }

  public List<Production> getProductions() {
    return productions;
  }
  
  public NonTerminalSymbol<String> getInitialNonTerminalSymbol(){
    return initialNonTerminalSymbol;
  }
  
  public Grammar getGrammar(){
    return new Grammar(productions, initialNonTerminalSymbol);
  }
}
