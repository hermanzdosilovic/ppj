package hr.fer.zemris.ppj.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.regex.Regex;

/**
 * Class for storing lexical analyzer states with all of regular definitions related to it.
 * 
 * @author Ivan Trubic
 */
public class LexicalAnalyzerState {
  private String name;
  private List<Automaton> automatons;
  private Map<Regex, List<String>> regexActionsTable;
  private List<Regex> regexes;

  public LexicalAnalyzerState(String name) {
    this.name = name;
    automatons = new ArrayList<>();
    regexActionsTable = new HashMap<>();
    regexes = new ArrayList<>();
  }

  public void prepareForRun() {
    for (Automaton automaton : automatons) {
      automaton.prepareForRun();
    }
  }

  public boolean readCharacter(char character) {
    boolean isAlive = false;
    for (Automaton automaton : automatons) {
      automaton.makeTransitions(character);
      isAlive |= automaton.isAlive();
    }
    return isAlive;
  }

  public void reloadAndReadSequence(String sequence) {
    prepareForRun();
    for (char character : sequence.toCharArray()) {
      readCharacter(character);
    }
  }

  public Integer getAutomatonIndex() {
    for (int i = 0; i < automatons.size(); i++) {
      if (automatons.get(i).isInAcceptableState()) {
        return i;
      }
    }
    return null;
  }

  public boolean addAutomaton(Automaton automaton) {
    return automatons.add(automaton);
  }
  
  public boolean addRegex(Regex regex) {
    return regexes.add(regex);
  }
  
  public boolean addRegexAction(Regex regex, String action) {
    if (!regexActionsTable.containsKey(regex)) {
      regexActionsTable.put(regex, new ArrayList<>());
    }
    return regexActionsTable.get(regex).add(action);
  }
  
  public List<Automaton> getAutomatons() {
    return automatons;
  }

  public String getName() {
    return name;
  }
  
  public Map<Regex, List<String>> getRegexActionsTable() {
    return regexActionsTable;
  }
  
  public List<Regex> getRegexes() {
    return regexes;
  }
  
  @Override
  public String toString() {
    return name;
  }
}
