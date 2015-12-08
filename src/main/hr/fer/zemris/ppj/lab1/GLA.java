package hr.fer.zemris.ppj.lab1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.regex.Regex;

public final class GLA {
  GeneratorInputDefinition inputDefinition;
  Map<String, LexicalAnalyzerState> lexicalAnalyzerStateTable;
  LexicalAnalyzerState initialState;

  public static void main(String[] args) throws Exception {
    GLA gla = new GLA();
    gla.start();
  }

  public GLA() throws IOException {
    this(System.in);
  }

  public GLA(InputStream stream) throws IOException {
    inputDefinition = new GeneratorInputDefinition(stream);
  }

  public GLA(List<String> inputLines) {
    inputDefinition = new GeneratorInputDefinition(inputLines);
  }

  public void start() throws FileNotFoundException {
    inputDefinition.parseDefinition();
    lexicalAnalyzerStateTable = inputDefinition.getLexicalAnalyzerStateTable();
    initialState = inputDefinition.getInitialLexicalAnalyzerState();

    generateAnalyzerDefinition();
    generateAnalyzerAction();
  }

  private void generateAnalyzerDefinition() throws FileNotFoundException {
    PrintWriter writer = new PrintWriter("analyzer_definition.ser");
    writer.println(initialState);
    for (LexicalAnalyzerState state : lexicalAnalyzerStateTable.values()) {
      for (Regex regex : state.getRegexes()) {
        writer.println(state);
        writer.println(regex);
      }
    }
    writer.close();
  }

  private void generateAnalyzerAction() throws FileNotFoundException {
    PrintWriter java =
        new PrintWriter("src/main/hr/fer/zemris/ppj/lab1/analyzer/AnalyzerAction.java");
    java.println("package hr.fer.zemris.ppj.lab1.analyzer;\n");
    java.println("public class AnalyzerAction {");
    java.println("  public static void performAction(LA analyzer) {");
    java.println("    String state = analyzer.getState().getName();");
    java.println("    int automatonIndex = analyzer.getAutomatonIndex();\n");

    for (LexicalAnalyzerState state : lexicalAnalyzerStateTable.values()) {
      int index = 0;
      Map<Regex, List<String>> regexActionsTable = state.getRegexActionsTable();
      for (Regex regex : state.getRegexes()) {
        java.print("    if (state.equals(\"" + state.getName() + "\")");
        java.println(" && automatonIndex == " + index + ") {");
        for (String action : regexActionsTable.get(regex)) {
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

  private String resolveCommand(String line) {
    String[] parsedLine = line.split(" ");
    if (parsedLine[0].equals("-")) {
      return "      analyzer.reject();";
    }
    if (parsedLine[0].equals("NOVI_REDAK")) {
      return "      analyzer.newLine();";
    }
    if (parsedLine[0].equals("UDJI_U_STANJE")) {
      return "      analyzer.setState(\"" + parsedLine[1] + "\");";
    }
    if (parsedLine[0].equals("VRATI_SE")) {
      return "      analyzer.returnTo(" + parsedLine[1] + ");";
    }
    return "      analyzer.setLexicalUnit(\"" + parsedLine[0] + "\");";
  }
}
