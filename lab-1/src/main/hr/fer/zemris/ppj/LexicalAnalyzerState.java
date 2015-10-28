package hr.fer.zemris.ppj;

import hr.fer.zemris.ppj.regex.RegularDefinition;

import java.util.List;

/**
 * Class for storing lexical analyzer states with all of regular definitions related to it.
 * 
 * @author truba
 *
 */
public class LexicalAnalyzerState {

  String name;
  List<RegularDefinition> list;

  public LexicalAnalyzerState(String name) {
    this.name = name;
  }

}
