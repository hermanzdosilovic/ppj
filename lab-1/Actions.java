public class Actions {
  public static void action(Analyzer analyzer) {
      if(analyzer.getState().equals("S_unarni") && analyzer.getAutomationIndex() == 0) {
          analyzer.reject();
      }

      if(analyzer.getState().equals("S_unarni") && analyzer.getAutomationIndex() == 1) {
          analyzer.reject();
          analyzer.newLine();
      }

      if(analyzer.getState().equals("S_unarni") && analyzer.getAutomationIndex() == 2) {
          analyzer.setLexicalUnit("UMINUS");
          analyzer.setState("S_pocetno");
      }

      if(analyzer.getState().equals("S_unarni") && analyzer.getAutomationIndex() == 3) {
          analyzer.setLexicalUnit("UMINUS");
          analyzer.returnTo(1);
      }

      if(analyzer.getState().equals("S_komentar") && analyzer.getAutomationIndex() == 0) {
          analyzer.reject();
          analyzer.setState("S_pocetno");
      }

      if(analyzer.getState().equals("S_komentar") && analyzer.getAutomationIndex() == 1) {
          analyzer.reject();
          analyzer.newLine();
      }

      if(analyzer.getState().equals("S_komentar") && analyzer.getAutomationIndex() == 2) {
          analyzer.reject();
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 0) {
          analyzer.reject();
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 1) {
          analyzer.reject();
          analyzer.newLine();
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 2) {
          analyzer.reject();
          analyzer.setState("S_komentar");
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 3) {
          analyzer.setLexicalUnit("OPERAND");
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 4) {
          analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 5) {
          analyzer.setLexicalUnit("DESNA_ZAGRADA");
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 6) {
          analyzer.setLexicalUnit("OP_MINUS");
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 7) {
          analyzer.setLexicalUnit("OP_MINUS");
          analyzer.setState("S_unarni");
          analyzer.returnTo(1);
      }

      if(analyzer.getState().equals("S_pocetno") && analyzer.getAutomationIndex() == 8) {
          analyzer.setLexicalUnit("LIJEVA_ZAGRADA");
          analyzer.setState("S_unarni");
          analyzer.returnTo(1);
      }

  }
}
