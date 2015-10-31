package hr.fer.zemris.ppj.analyizer;

public class AnalyzerAction {
  public static void performAction(LA analyzer) {
    String state = analyzer.getState().getName();
    int automatonIndex = analyzer.getAutomatonIndex();

    if (state.equals("S_unarni")&& automatonIndex == 0) {
      analyzer.reject();
      return;
    }
    if (state.equals("S_unarni")&& automatonIndex == 1) {
      analyzer.reject();
      analyzer.newLine();
      return;
    }
    if (state.equals("S_unarni")&& automatonIndex == 2) {
      analyzer.setLexicalUnit("UMINUS");
      analyzer.setState("S_pocetno");
      return;
    }
    if (state.equals("S_unarni")&& automatonIndex == 3) {
      analyzer.setLexicalUnit("UMINUS");
      analyzer.returnTo(1);
      return;
    }
    if (state.equals("S_komentar")&& automatonIndex == 0) {
      analyzer.reject();
      analyzer.setState("S_pocetno");
      return;
    }
    if (state.equals("S_komentar")&& automatonIndex == 1) {
      analyzer.reject();
      analyzer.newLine();
      return;
    }
    if (state.equals("S_komentar")&& automatonIndex == 2) {
      analyzer.reject();
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 0) {
      analyzer.reject();
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 1) {
      analyzer.reject();
      analyzer.newLine();
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 2) {
      analyzer.reject();
      analyzer.setState("S_komentar");
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 3) {
      analyzer.setLexicalUnit("OPERAND");
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 4) {
      analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 5) {
      analyzer.setLexicalUnit("DESNA_ZAGRADA");
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 6) {
      analyzer.setLexicalUnit("OP_MINUS");
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 7) {
      analyzer.setLexicalUnit("OP_MINUS");
      analyzer.setState("S_unarni");
      analyzer.returnTo(1);
      return;
    }
    if (state.equals("S_pocetno")&& automatonIndex == 8) {
      analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
      analyzer.setState("S_unarni");
      analyzer.returnTo(1);
      return;
    }
  }
}
