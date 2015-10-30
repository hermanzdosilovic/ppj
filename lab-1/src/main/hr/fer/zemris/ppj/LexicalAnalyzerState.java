package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.automaton.Automaton;

/**
 * Class for storing lexical analyzer states with all of regular definitions related to it.
 * 
 * @author Ivan Trubić
 */
public class LexicalAnalyzerState {
  private String name;
  private List<Automaton> automatons;
  private List<RegexAction> regexActions;

  public LexicalAnalyzerState(String name) {
    this.name = name;
    automatons = new ArrayList<>();
    regexActions = new ArrayList<>();
  }

  public void prepareForRun() {
    for (Automaton automaton : automatons) {
      automaton.prepareForRun();
    }
  }

  public boolean readCharacter(char character) {
    System.out.println("Znak: " + character);
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

  public boolean addRegexAction(RegexAction action) {
    return regexActions.add(action);
  }

  public List<Automaton> getAutomatons() {
    return automatons;
  }

  public String getName() {
    return name;
  }

  public List<RegexAction> getRegexActions() {
    return regexActions;
  }
}
