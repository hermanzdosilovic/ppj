package hr.fer.zemris.ppj.lab1.analyzer;

public class AnalyzerAction {
  public static void performAction(LA analyzer) {
    String state = analyzer.getState().getName();
    int automatonIndex = analyzer.getAutomatonIndex();

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
    if (state.equals("S_jednolinijskiKomentar") && automatonIndex == 0) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.newLine();
      analyzer.setState("S_pocetno");
    }
    if (state.equals("S_jednolinijskiKomentar") && automatonIndex == 1) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
    }
    if (state.equals("S_string") && automatonIndex == 0) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("NIZ_ZNAKOVA");
      analyzer.setState("S_pocetno");
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
      analyzer.setState("S_jednolinijskiKomentar");
    }
    if (state.equals("S_pocetno") && automatonIndex == 3) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.setState("S_komentar");
    }
    if (state.equals("S_pocetno") && automatonIndex == 4) {
      analyzer.setLexicalUnit("{");
      analyzer.reject();
      analyzer.setState("S_string");
      analyzer.returnTo(0);
    }
    if (state.equals("S_pocetno") && automatonIndex == 5) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_BREAK");
    }
    if (state.equals("S_pocetno") && automatonIndex == 6) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_CHAR");
    }
    if (state.equals("S_pocetno") && automatonIndex == 7) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_CONST");
    }
    if (state.equals("S_pocetno") && automatonIndex == 8) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_CONTINUE");
    }
    if (state.equals("S_pocetno") && automatonIndex == 9) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_ELSE");
    }
    if (state.equals("S_pocetno") && automatonIndex == 10) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_FOR");
    }
    if (state.equals("S_pocetno") && automatonIndex == 11) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_IF");
    }
    if (state.equals("S_pocetno") && automatonIndex == 12) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_INT");
    }
    if (state.equals("S_pocetno") && automatonIndex == 13) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_RETURN");
    }
    if (state.equals("S_pocetno") && automatonIndex == 14) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_VOID");
    }
    if (state.equals("S_pocetno") && automatonIndex == 15) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("KR_WHILE");
    }
    if (state.equals("S_pocetno") && automatonIndex == 16) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("IDN");
    }
    if (state.equals("S_pocetno") && automatonIndex == 17) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("BROJ");
    }
    if (state.equals("S_pocetno") && automatonIndex == 18) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("BROJ");
    }
    if (state.equals("S_pocetno") && automatonIndex == 19) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("ZNAK");
    }
    if (state.equals("S_pocetno") && automatonIndex == 20) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("ZNAK");
    }
    if (state.equals("S_pocetno") && automatonIndex == 21) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_INC");
    }
    if (state.equals("S_pocetno") && automatonIndex == 22) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_DEC");
    }
    if (state.equals("S_pocetno") && automatonIndex == 23) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("PLUS");
    }
    if (state.equals("S_pocetno") && automatonIndex == 24) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("MINUS");
    }
    if (state.equals("S_pocetno") && automatonIndex == 25) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_PUTA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 26) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_DIJELI");
    }
    if (state.equals("S_pocetno") && automatonIndex == 27) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_MOD");
    }
    if (state.equals("S_pocetno") && automatonIndex == 28) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_PRIDRUZI");
    }
    if (state.equals("S_pocetno") && automatonIndex == 29) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_LT");
    }
    if (state.equals("S_pocetno") && automatonIndex == 30) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_LTE");
    }
    if (state.equals("S_pocetno") && automatonIndex == 31) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_GT");
    }
    if (state.equals("S_pocetno") && automatonIndex == 32) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_GTE");
    }
    if (state.equals("S_pocetno") && automatonIndex == 33) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_EQ");
    }
    if (state.equals("S_pocetno") && automatonIndex == 34) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_NEQ");
    }
    if (state.equals("S_pocetno") && automatonIndex == 35) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_NEG");
    }
    if (state.equals("S_pocetno") && automatonIndex == 36) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_TILDA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 37) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_I");
    }
    if (state.equals("S_pocetno") && automatonIndex == 38) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_ILI");
    }
    if (state.equals("S_pocetno") && automatonIndex == 39) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_BIN_I");
    }
    if (state.equals("S_pocetno") && automatonIndex == 40) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_BIN_ILI");
    }
    if (state.equals("S_pocetno") && automatonIndex == 41) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("OP_BIN_XILI");
    }
    if (state.equals("S_pocetno") && automatonIndex == 42) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("ZAREZ");
    }
    if (state.equals("S_pocetno") && automatonIndex == 43) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("TOCKAZAREZ");
    }
    if (state.equals("S_pocetno") && automatonIndex == 44) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("L_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 45) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("D_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 46) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("L_VIT_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 47) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("D_VIT_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 48) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("L_UGL_ZAGRADA");
    }
    if (state.equals("S_pocetno") && automatonIndex == 49) {
      analyzer.setLexicalUnit("{");
      analyzer.setLexicalUnit("D_UGL_ZAGRADA");
    }
  }
}
