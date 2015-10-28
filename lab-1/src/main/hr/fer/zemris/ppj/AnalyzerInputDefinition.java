package hr.fer.zemris.ppj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.ppj.regex.RegDefResolver;
import hr.fer.zemris.ppj.regex.RegularDefinition;

/**
 * Class for lexical analyzer definition with a specific input format. Point of this class is to
 * just call the constructor and everything will be set to go according to the specification.
 * 
 * @author Truba
 *
 */
public class AnalyzerInputDefinition {

  private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
  private String textInput;
  private String[] parsedRegularDefinition;
  private String[] parsedStateDefinition;
  private List<RegularDefinition> listOfRegDefinitions;
  private List<LexicalUnit> lexicalName;
  private HashMap<String, LexicalAnalyzerState> lexicalState;

  public AnalyzerInputDefinition() {
    this.regularDefinitionInput();
    @SuppressWarnings("unused")
    RegDefResolver resolver = new RegDefResolver(listOfRegDefinitions);
    this.lexicalStateDefinition();
    try {
      this.lexicalNameDefinition();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.lexicalAnalyzerRulesDefinition();
  }

  public void regularDefinitionInput() {
    String name;
    String value;
    while (true) {
      try {
        textInput = input.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (textInput.startsWith("%")) {
        break;
      }
      parsedRegularDefinition = textInput.split(" ");

      name = parsedRegularDefinition[0].substring(1, parsedRegularDefinition[0].length() - 1);
      value = parsedRegularDefinition[1];
      RegularDefinition regularDefinition = new RegularDefinition(name, value);
      listOfRegDefinitions.add(regularDefinition);
    }
  }

  public void lexicalStateDefinition() {
    parsedStateDefinition = textInput.split(" ");
    for (int i = 1; i < parsedStateDefinition.length; i++) {
      LexicalAnalyzerState state = new LexicalAnalyzerState(parsedStateDefinition[i]);
      lexicalState.put(parsedStateDefinition[i], state);
    }
  }

  public void lexicalNameDefinition() throws Exception {
    try {
      textInput = input.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!textInput.startsWith("%L")) {
      throw new Exception("Bad input: missing %L line");
    }

    String[] parsedLexicalNames;
    parsedLexicalNames = textInput.split(" ");
    for (int i = 1; i < parsedLexicalNames.length; i++) {
      LexicalUnit unit = new LexicalUnit(parsedLexicalNames[i]);
      lexicalName.add(unit);
    }

  }

  public void lexicalAnalyzerRulesDefinition() {
 
  }
}
