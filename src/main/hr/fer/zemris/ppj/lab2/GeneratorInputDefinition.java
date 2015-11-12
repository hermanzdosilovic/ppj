package hr.fer.zemris.ppj.lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneratorInputDefinition {
  
  private List<String> inputLines = new ArrayList<String>();
  private List<String> nonterminalSymbols = new ArrayList<String>();
  private List<String> terminalSymbols = new ArrayList<String>();
  private List<String> synchronousTerminalSymbols = new ArrayList<String>();
  private HashMap<String, List<String>> productions = new HashMap<String, List<String>>();
  private int index = 0;
  
  public GeneratorInputDefinition() throws IOException{
    this(System.in);
    
  }
  
  public GeneratorInputDefinition(InputStream stream) throws IOException{
    BufferedReader input = new BufferedReader(new InputStreamReader(stream));
    String line = " ";
    while(line != null){
      line = input.readLine();
      inputLines.add(line);
    }
    
  }
  
  public void runGenerator(){
    parseNonterminalSymbols();
    parseTerminalSymbols();
    parseSynchronousTerminalSymbols();
    parseProductions();
  }
  
  private void parseNonterminalSymbols(){
    String[] line = inputLines.get(index++).split(" ");
    for(int i = 1; i < line.length; i++){
      nonterminalSymbols.add(line[i]);
    }
  }
  
  private void parseTerminalSymbols(){
    String[] line = inputLines.get(index++).split(" ");
    for(int i = 1; i < line.length; i++){
      terminalSymbols.add(line[i]);
    }
    
  }
  
  private void parseSynchronousTerminalSymbols(){
    String[] line = inputLines.get(index++).split(" ");
    for(int i = 1; i < line.length; i++){
      synchronousTerminalSymbols.add(line[i]);
    }
  }
  
  private void parseProductions(){
    for(String simbol : nonterminalSymbols){
      productions.put(simbol, new ArrayList<String>());
    }
    
    String line = " ";
    String leftSide = " ";
    line = inputLines.get(index++);
    while(line != null){
      if(!line.startsWith(" ")){
        leftSide = line;
        line = inputLines.get(index++);
        while(line != null && line.startsWith(" ")){
          productions.get(leftSide).add(line.substring(1));
          line = inputLines.get(index++);
        }
      }
    }
  }

}
