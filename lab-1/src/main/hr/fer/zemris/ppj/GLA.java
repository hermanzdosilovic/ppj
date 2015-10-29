package hr.fer.zemris.ppj;

import hr.fer.zemris.ppj.regex.Regex;

import java.io.FileInputStream;
import java.util.Map;

public final class GLA {
  public static void main(String[] args) throws Exception {
    System.setIn(new FileInputStream("/home/truba/workspace/ppj/in.txt"));
    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition();
    Map<String, LexicalAnalyzerState> rules = inputDefinition.getLexicalState();
    LexicalAnalyzerState initialState = inputDefinition.getInitialAnalyzerState();
    System.out.println("%s " + initialState.getName());
//    int index = 0;
    for(Map.Entry<String, LexicalAnalyzerState> entry : rules.entrySet()){
      LexicalAnalyzerState state = entry.getValue();
      for(RegexAction action : state.getRegexActionList()){
        System.out.println(state.getName());
        System.out.println((new Regex(action.getRegex())).toAutomaton());
      }
      
    }
  }
}
