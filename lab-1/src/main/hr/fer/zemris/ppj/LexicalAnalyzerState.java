package hr.fer.zemris.ppj;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for storing lexical analyzer states with all of regular definitions
 * related to it.
 * 
 * @author truba
 *
 */
public class LexicalAnalyzerState {

  String name;
  List<RegexAction> list = new ArrayList<RegexAction>();

  public LexicalAnalyzerState(String name) {
  this.name = name;
}

}
