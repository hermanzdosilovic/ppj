package hr.fer.zemris.ppj.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.ppj.regex.RegularDefinition;
import hr.fer.zemris.ppj.regex.RegularDefinitionResolver;

/**
 * Class for lexical analyzer definition with a specific input format. Point of this class is to
 * just call the constructor and everything will be set to go according to the specification.
 * 
 * @author Ivan Trubić
 *
 */
public class GeneratorInputDefinition {

  private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  private String textInput;
  private List<RegularDefinition> listOfRegDefinitions = new ArrayList<RegularDefinition>();
  private List<LexicalUnit> lexicalName = new ArrayList<LexicalUnit>();
  private List<String> lexicalRules = new ArrayList<String>();
  private HashMap<String, LexicalAnalyzerState> lexicalState =
      new HashMap<String, LexicalAnalyzerState>();
  private RegularDefinitionResolver resolvedDefinitions;
  private LexicalAnalyzerState InitialAnalyzerState;
  private List<String> inputList;

  public GeneratorInputDefinition() {
	  this(System.in);
  }
  
  public GeneratorInputDefinition(InputStream stream){
	  input = new BufferedReader(new InputStreamReader(stream));
	  String txt = " ";
	  
	  while(txt != null){
		  txt = read();
		  inputList.add(txt);
	  }
  }
  
  public GeneratorInputDefinition(List<String> inputList){
	  this.inputList = inputList;
  }

  public void startGenerator(){
	this.regularDefinitionInput();
    this.lexicalStateDefinition();
    this.lexicalNameDefinition();
    this.lexicalAnalyzerRulesDefinition(); 
  }

  private void regularDefinitionInput() {
    String name;
    String value;
    String[] parsedRegularDefinition;
    while (true) {
      textInput = read();
      if (textInput.startsWith("%")) {
        break;
      }
      parsedRegularDefinition = textInput.split(" ");

      name = parsedRegularDefinition[0].substring(1, parsedRegularDefinition[0].length() - 1);
      value = parsedRegularDefinition[1];
      listOfRegDefinitions.add(new RegularDefinition(name, value));
    }
    resolvedDefinitions = new RegularDefinitionResolver(listOfRegDefinitions);
  }

  private void lexicalStateDefinition() {
    String[] parsedStateDefinition = textInput.split(" ");
    InitialAnalyzerState = new LexicalAnalyzerState(parsedStateDefinition[1]);

    for (int i = 1; i < parsedStateDefinition.length; i++) {
      LexicalAnalyzerState state = new LexicalAnalyzerState(parsedStateDefinition[i]);
      lexicalState.put(parsedStateDefinition[i], state);
    }
  }

  private void lexicalNameDefinition() {
    textInput = read();

    String[] parsedLexicalNames;
    parsedLexicalNames = textInput.split(" ");
    for (int i = 1; i < parsedLexicalNames.length; i++) {
      LexicalUnit unit = new LexicalUnit(parsedLexicalNames[i]);
      lexicalName.add(unit);
    }
  }

  private void lexicalAnalyzerRulesDefinition() {
    String[] parsedRules;
    String name;
    String regEx;
    while (true) {
      textInput = read();
      if (textInput == null || textInput.isEmpty()) {
        break;
      }
      parsedRules = textInput.split(">", 2);
      name = parsedRules[0].substring(1);
      regEx = parsedRules[1];

      textInput = read();
      textInput = read();

      while (!textInput.equals("}")) {

        lexicalRules.add(textInput);

        textInput = read();
      }

      lexicalState.get(name).addRegexAction(new RegexAction(resolvedDefinitions.resolveRegex(regEx),
          new ArrayList<String>(lexicalRules)));
      lexicalRules.clear();

    }
  }

  private String read() {
    String textInput = " ";
    try {
      textInput = input.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return textInput;
  }

  /**
   * List of all regular definitions
   * 
   * @return List of regular definitions
   */
  public List<RegularDefinition> getListOfRegularDefinitions() {
    return listOfRegDefinitions;
  }

  /**
   * List of lexical unit names
   * 
   * @return List of lexical unit names
   */
  public List<LexicalUnit> getLexicalNames() {
    return lexicalName;
  }

  /**
   * Hash map with lexical states as key and values are LexicalAnalyzerState objects. If you want to
   * access actions for certain regular expressions you can do it with an example
   * <code>lexicalState.get(key).list.action</code> which returns List<String> of actions
   * 
   * @return lexicalState Map<String, LexicalAnalyzerState>
   */
  public HashMap<String, LexicalAnalyzerState> getLexicalState() {
    return lexicalState;
  }

  /**
   * Resolved regular definitions
   * 
   * @return RegDefResolver
   */
  public RegularDefinitionResolver getResolver() {
    return resolvedDefinitions;
  }

  public LexicalAnalyzerState getInitialAnalyzerState() {
    return InitialAnalyzerState;
  }
}
