package hr.fer.zemris.ppj;

import java.util.List;

/**
 * Structure for storing list of actions for certain regular expressions
 * 
 * @author Ivan Trubić
 *
 */
public class RegexAction {
  private String definition;
  private List<String> action;

  public RegexAction(String definition, List<String> action) {
    this.definition = definition;
    this.action = action;
  }
  
  public String getRegex(){
    return definition;
  }
  
  public void addAction(List<String> actionList){
    action = actionList;
  }
}
