package hr.fer.zemris.ppj.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.regex.Regex;
import hr.fer.zemris.ppj.regex.RegularDefinition;
import hr.fer.zemris.ppj.regex.RegularDefinitionResolver;

/**
 * Class for lexical analyzer definition with a specific input format. Point of this class is to
 * just call the constructor and everything will be set to go according to the specification.
 * 
 * @author Ivan Trubic
 */
public class GeneratorInputDefinition {
  private List<RegularDefinition> regularDefinitions;
  private List<LexicalUnit> lexicalUnits;
  private Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;
  private RegularDefinitionResolver regularDefinitionResolver;
  private LexicalAnalyzerState initialLexicalAnalyzerState;
  private List<String> inputLines;
  private int readerIndex = 0;

  public GeneratorInputDefinition() throws IOException {
    this(System.in);
  }

  public GeneratorInputDefinition(InputStream stream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    inputLines = new ArrayList<>();
    String line;
    while ((line = reader.readLine()) != null) {
      inputLines.add(line);
    }
  }

  public GeneratorInputDefinition(List<String> inputLines) {
    this.inputLines = inputLines;
  }

  public void parseDefinition() {
    parseRegularDefinitions();
    parseLexicalStates();
    parseLexicalUnits();
    parseLexicalAnalyzerRules();
  }

  public void parseRegularDefinitions() {
    regularDefinitions = new ArrayList<>();
    while (readerIndex < inputLines.size()) {
      String inputLine = inputLines.get(readerIndex);
      if (inputLine.startsWith("%")) {
        break;
      }
      readerIndex++;

      String[] parsedRegularDefinition = inputLine.split(" ");
      String name =
          parsedRegularDefinition[0].substring(1, parsedRegularDefinition[0].length() - 1);
      String value = parsedRegularDefinition[1];
      regularDefinitions.add(new RegularDefinition(name, value));
    }
    regularDefinitionResolver = new RegularDefinitionResolver(regularDefinitions);
  }

  public void parseLexicalStates() {
    String[] parsedLexicalStates = inputLines.get(readerIndex++).split(" ");

    lexicalAnalyzerStateTable = new HashMap<>();
    initialLexicalAnalyzerState = new LexicalAnalyzerState(parsedLexicalStates[1]);
    for (int i = 1; i < parsedLexicalStates.length; i++) {
      lexicalAnalyzerStateTable.put(parsedLexicalStates[i],
          new LexicalAnalyzerState(parsedLexicalStates[i]));
    }
  }

  public void parseLexicalUnits() {
    String[] parsedLexicalUnits = inputLines.get(readerIndex++).split(" ");
    lexicalUnits = new ArrayList<>();
    for (int i = 1; i < parsedLexicalUnits.length; i++) {
      lexicalUnits.add(new LexicalUnit(parsedLexicalUnits[i]));
    }
  }

  public void parseLexicalAnalyzerRules() {
    while (readerIndex < inputLines.size()) {
      String inputLine = inputLines.get(readerIndex++);

      String[] parsedRule = inputLine.split(">", 2);
      LexicalAnalyzerState state = lexicalAnalyzerStateTable.get(parsedRule[0].substring(1));
      Regex regex = new Regex(regularDefinitionResolver.resolveRegex(parsedRule[1]));
      state.addRegex(regex);

      while (!(inputLine = inputLines.get(readerIndex++)).equals("}")) {
        state.addRegexAction(regex, inputLine);
      }
    }
  }

  /**
   * List of all regular definitions
   * 
   * @return List of regular definitions
   */
  public List<RegularDefinition> getRegularDefinitions() {
    return regularDefinitions;
  }

  /**
   * List of lexical unit names
   * 
   * @return List of lexical unit names
   */
  public List<LexicalUnit> getLexicalUnits() {
    return lexicalUnits;
  }

  /**
   * Hash map with lexical states as key and values are LexicalAnalyzerState objects. If you want to
   * access actions for certain regular expressions you can do it with an example
   * <code>lexicalState.get(key).list.action</code> which returns List<String> of actions
   * 
   * @return lexicalState Map<String, LexicalAnalyzerState>
   */
  public Map<String, LexicalAnalyzerState> getLexicalAnalyzerStateTable() {
    return lexicalAnalyzerStateTable;
  }

  /**
   * Resolved regular definitions
   * 
   * @return RegDefResolver
   */
  public RegularDefinitionResolver getRegularDefinitionResolver() {
    return regularDefinitionResolver;
  }

  public LexicalAnalyzerState getInitialLexicalAnalyzerState() {
    return initialLexicalAnalyzerState;
  }

  public Collection<LexicalAnalyzerState> getLexicalAnalyzerStates() {
    return lexicalAnalyzerStateTable.values();
  }
}
