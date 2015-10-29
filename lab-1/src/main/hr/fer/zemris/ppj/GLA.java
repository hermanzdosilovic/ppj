package hr.fer.zemris.ppj;

import hr.fer.zemris.ppj.regex.Regex;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Map;

public final class GLA {
  public static void main(String[] args) throws Exception {
    System.setIn(new FileInputStream("/home/truba/workspace/ppj/in.txt"));

    PrintWriter writer = new PrintWriter("file.txt");

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition();
    Map<String, LexicalAnalyzerState> rules = inputDefinition.getLexicalState();
    LexicalAnalyzerState initialState = inputDefinition.getInitialAnalyzerState();
    System.out.println("%s " + initialState.getName());
//    int index = 0;
    for(Map.Entry<String, LexicalAnalyzerState> entry : rules.entrySet()){
      LexicalAnalyzerState state = entry.getValue();
      for(RegexAction action : state.getRegexActionList()){
        writer.println("%");
        writer.println(state.getName());
        writer.println((new Regex(action.getRegex())).toAutomaton());
        writer.println("%");
      }
     writer.close();
      
    }
  }
}
