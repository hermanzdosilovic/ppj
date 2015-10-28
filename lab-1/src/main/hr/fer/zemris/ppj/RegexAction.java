package hr.fer.zemris.ppj;

import java.util.List;
/**
 * Structure for storing list of actions for certain regular expressions
 * @author truba
 *
 */
public class RegexAction {
  String definition;
  List<String> action;

  public RegexAction(String definition, List<String> action) {
    this.definition = definition;
    this.action = action;
  }
}
