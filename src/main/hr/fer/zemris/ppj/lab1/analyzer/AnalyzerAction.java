package hr.fer.zemris.ppj.lab1.analyzer;

public class AnalyzerAction {
  public static void performAction(LA analyzer) {
    String state = analyzer.getState().getName();
    int automatonIndex = analyzer.getAutomatonIndex();

    if (state.equals("S_unarni") && automatonIndex == 0) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
    }
    if (state.equals("S_unarni") && automatonIndex == 1) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.newLine();
    }
    if (state.equals("S_unarni") && automatonIndex == 2) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("UMINUS");
      analyzer.setState("S_pocetno");
    }
    if (state.equals("S_unarni") && automatonIndex == 3) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("UMINUS");
      analyzer.returnTo(1);
    }
    if (state.equals("S_komentar") && automatonIndex == 0) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.setState("S_pocetno");
    }
    if (state.equals("S_komentar") && automatonIndex == 1) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.newLine();
    }
    if (state.equals("S_komentar") && automatonIndex == 2) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
    }
    if (state.equals("S_pocetno") && automatonIndex == 0) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
    }
    if (state.equals("S_pocetno") && automatonIndex == 1) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.newLine();
    }
    if (state.equals("S_pocetno") && automatonIndex == 2) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.setState("S_komentar");
    }
    if (state.equals("S_pocetno") && automatonIndex == 3) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OPERAND");
    }
    if (state.equals("S_pocetno") && automatonIndex == 4) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 5) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("DESNA_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 6) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_MINUS");
    }
    if (state.equals("S_pocetno") && automatonIndex == 7) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_MINUS");
      analyzer.setState("S_unarni");
      analyzer.returnTo(1);
    }
    if (state.equals("S_pocetno") && automatonIndex == 8) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
      analyzer.setState("S_unarni");
      analyzer.returnTo(1);
    }
  }
}
