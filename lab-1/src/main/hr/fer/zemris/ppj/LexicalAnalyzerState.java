package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing lexical analyzer states with all of regular definitions related to it.
 * 
 * @author Ivan Trubić
 *
 */
public class LexicalAnalyzerState {

  private String name;
  private List<RegexAction> list = new ArrayList<RegexAction>();

  public LexicalAnalyzerState(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void addRegexAction(RegexAction action) {
    list.add(action);
  }

  public List<RegexAction> getRegexActionList() {
    return list;
  }

}
