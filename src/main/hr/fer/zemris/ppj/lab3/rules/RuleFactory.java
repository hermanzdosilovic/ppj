package hr.fer.zemris.ppj.lab3.rules;

import java.util.Map;

import hr.fer.zemris.ppj.lab3.rules.expression.PrimarniIzraz;
import hr.fer.zemris.ppj.lab3.rules.structure.PrijevodnaJedinica;
import hr.fer.zemris.ppj.lab3.rules.structure.VanjskaDeklaracija;
import hr.fer.zemris.ppj.symbol.Symbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public final class RuleFactory {

  private static Map<String, Rule> ruleTable;

  static {
	// Deklaracije i definicije
    ruleTable.put("<prijevodna_jedinica>", PrijevodnaJedinica.PRIJEVODNA_JEDINICA);
    ruleTable.put("<vanjska_deklaracija>", VanjskaDeklaracija.VANJSKA_DEKLARACIJA);
    
    // Izrazi
    ruleTable.put("<primarni_izraz>", PrimarniIzraz.PRIMARNI_IZRAZ);
  }

  public static Rule getRule(String name) {
    return ruleTable.get(name);
  }

  public static Rule getRule(Symbol symbol) {
    return ruleTable.get(symbol.getValue());
  }
}
