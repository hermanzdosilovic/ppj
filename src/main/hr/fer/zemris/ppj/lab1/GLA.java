package hr.fer.zemris.ppj.lab1;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Map;

import hr.fer.zemris.ppj.regex.Regex;

public final class GLA {

  public static void main(String[] args) throws Exception {
    System.setIn(new FileInputStream("MinusLang.in"));

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition();
    Map<String, LexicalAnalyzerState> rules = inputDefinition.getLexicalState();
    LexicalAnalyzerState initialState = inputDefinition.getInitialAnalyzerState();

    inputDefinition.startGenerator();
    PrintWriter writer = new PrintWriter("analyzer_definition.txt");
    writer.println(initialState.getName());
    for (Map.Entry<String, LexicalAnalyzerState> entry : rules.entrySet()) {
      LexicalAnalyzerState state = entry.getValue();
      for (RegexAction action : state.getRegexActions()) {
        writer.println(state.getName());
        writer.println((new Regex(action.getRegex())).toString());
      }
    }
    writer.close();

    PrintWriter java = new PrintWriter("src/main/hr/fer/zemris/ppj/lab1/analyizer/AnalyzerAction.java");
    java.println("package hr.fer.zemris.ppj.lab1.analyizer;\n");
    java.println("public class AnalyzerAction {");
    java.println("  public static void performAction(LA analyzer) {");
    java.println("    String state = analyzer.getState().getName();");
    java.println("    int automatonIndex = analyzer.getAutomatonIndex();\n");

    for (Map.Entry<String, LexicalAnalyzerState> entry : rules.entrySet()) {
      int index = 0;
      LexicalAnalyzerState state = entry.getValue();
      for (RegexAction regex : state.getRegexActions()) {
        java.print("    if (state.equals(\"" + state.getName() + "\")");
        java.println("&& automatonIndex == " + index + ") {");
        for (String action : regex.getActionsList()) {
          java.println(resolveCommand(action));
        }
        java.println("    }");
        index++;
      }
    }
    java.println("  }");
    java.println("}");
    java.close();
  }

  private static String resolveCommand(String line) {
    String[] parsedLine = line.split(" ");
    if (parsedLine[0].equals("-"))
      return "      analyzer.reject();";
    if (parsedLine[0].equals("NOVI_REDAK"))
      return "      analyzer.newLine();";
    if (parsedLine[0].equals("UDJI_U_STANJE"))
      return "      analyzer.setState(\"" + parsedLine[1] + "\");";
    if (parsedLine[0].equals("VRATI_SE"))
      return "      analyzer.returnTo(" + parsedLine[1] + ");";
    return "      analyzer.setLexicalUnit(\"" + parsedLine[0] + "\");";
  }
}
