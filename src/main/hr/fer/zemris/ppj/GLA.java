package hr.fer.zemris.ppj;

import hr.fer.zemris.ppj.regex.Regex;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Map;

public final class GLA {

  public static void main(String[] args) throws Exception {
    System.setIn(new FileInputStream("C_ulazna_datoteka.txt"));
    // line above is for testing purposes

    GeneratorInputDefinition inputDefinition = new GeneratorInputDefinition();
    Map<String, LexicalAnalyzerState> rules = inputDefinition.getLexicalState();
    LexicalAnalyzerState initialState = inputDefinition.getInitialAnalyzerState();
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
    PrintWriter java = new PrintWriter("src/main/hr/fer/zemris/ppj/analyizer/Actions.java");
    java.println("package hr.fer.zemris.ppj.analyizer;");
    java.println("public class Actions {");
    java.println("  public static void action(LA analyzer) {");
    java.println("      String state = analyzer.getState().getName();");
    java.println("      int automatonIndex = analyzer.getAutomatonIndex();");

    for (Map.Entry<String, LexicalAnalyzerState> entry : rules.entrySet()) {
      int index = 0;
      LexicalAnalyzerState state = entry.getValue();
      for (RegexAction regex : state.getRegexActions()) {
        java.println("      if(state.equals(\"" + state.getName()
            + "\") && automatonIndex == " + index + ") {");
        index++;

        for (String action : regex.getActionsList()) {
          java.println(resolveCommand(action));
        }
        java.println("      }");
        java.println();
      }

    }


    java.println("  }");
    java.println("}");
    java.close();
  }

  private static String resolveCommand(String line) {
    String[] parsedLine = line.split(" ");

    if (parsedLine[0].equals("-"))
      return "          analyzer.reject();";

    if (parsedLine[0].equals("NOVI_REDAK"))
      return "          analyzer.newLine();";

    if (parsedLine[0].equals("UDJI_U_STANJE"))
      return "          analyzer.setState(\"" + parsedLine[1] + "\");";

    if (parsedLine[0].equals("VRATI_SE"))
      return "          analyzer.returnTo(" + parsedLine[1] + ");";

    else
      return "          analyzer.setLexicalUnit(\"" + parsedLine[0] + "\");";
  }

}
