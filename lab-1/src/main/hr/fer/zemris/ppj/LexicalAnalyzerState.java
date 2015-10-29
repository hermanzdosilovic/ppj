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
