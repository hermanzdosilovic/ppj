package hr.fer.zemris.ppj.analyizer;
public class Actions {
  public static void action(LA analyzer) {
      if(analyzer.getState().toString().equals("S_unarni") && analyzer.getAutomatonIndex() == 0) {
          analyzer.reject();
      }

      if(analyzer.getState().toString().equals("S_unarni") && analyzer.getAutomatonIndex() == 1) {
          analyzer.reject();
          analyzer.newLine();
      }

      if(analyzer.getState().toString().equals("S_unarni") && analyzer.getAutomatonIndex() == 2) {
          analyzer.setLexicalUnit("UMINUS");
          analyzer.setState("S_pocetno");
      }

      if(analyzer.getState().toString().equals("S_unarni") && analyzer.getAutomatonIndex() == 3) {
          analyzer.setLexicalUnit("UMINUS");
          analyzer.returnTo(1);
      }

      if(analyzer.getState().toString().equals("S_komentar") && analyzer.getAutomatonIndex() == 0) {
          analyzer.reject();
          analyzer.setState("S_pocetno");
      }

      if(analyzer.getState().toString().equals("S_komentar") && analyzer.getAutomatonIndex() == 1) {
          analyzer.reject();
          analyzer.newLine();
      }

      if(analyzer.getState().toString().equals("S_komentar") && analyzer.getAutomatonIndex() == 2) {
          analyzer.reject();
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 0) {
          analyzer.reject();
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 1) {
          analyzer.reject();
          analyzer.newLine();
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 2) {
          analyzer.reject();
          analyzer.setState("S_komentar");
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 3) {
          analyzer.setLexicalUnit("OPERAND");
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 4) {
          analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 5) {
          analyzer.setLexicalUnit("DESNA_ZAGRADA");
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 6) {
          analyzer.setLexicalUnit("OP_MINUS");
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 7) {
          analyzer.setLexicalUnit("OP_MINUS");
          analyzer.setState("S_unarni");
          analyzer.returnTo(1);
      }

      if(analyzer.getState().toString().equals("S_pocetno") && analyzer.getAutomatonIndex() == 8) {
          analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
          analyzer.setState("S_unarni");
          analyzer.returnTo(1);
      }

  }
}
