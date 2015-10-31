package hr.fer.zemris.ppj.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * Structure for storing list of actions for certain regular expressions
 * 
 * @author Ivan Trubić
 */
public class RegexAction {
  private String definition;
  private List<String> actions;

  public RegexAction(String definition, List<String> action) {
    this.definition = definition;
    this.actions = action;
  }

  public boolean addActions(List<String> actions) {
    if (this.actions == null) {
      this.actions = new ArrayList<>();
    }
    return this.actions.addAll(actions);
  }

  public String getRegex() {
    return definition;
  }

  public List<String> getActionsList() {
    return actions;
  }
}
