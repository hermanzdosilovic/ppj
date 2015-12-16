package hr.fer.zemris.ppj.lab3.rules;

import java.util.Map;

import hr.fer.zemris.ppj.lab3.rules.definitions.DefinicijaFunkcije;
import hr.fer.zemris.ppj.lab3.rules.definitions.Deklaracija;
import hr.fer.zemris.ppj.lab3.rules.definitions.DeklaracijaParametra;
import hr.fer.zemris.ppj.lab3.rules.definitions.InitDeklarator;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaDeklaracija;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaInitDeklaratora;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaParametara;
import hr.fer.zemris.ppj.lab3.rules.expression.PrimarniIzraz;
import hr.fer.zemris.ppj.lab3.rules.structure.PrijevodnaJedinica;
import hr.fer.zemris.ppj.lab3.rules.structure.VanjskaDeklaracija;
import hr.fer.zemris.ppj.node.SNode;
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

    // Deklaracije i definicije
    ruleTable.put("<definicija_funkcije>", DefinicijaFunkcije.DEFINICIJA_FUNKCIJE);
    ruleTable.put("<deklaracija_parametra>", DeklaracijaParametra.DEKLARACIJA_PARAMETRA);
    ruleTable.put("<lista_parametara>", ListaParametara.LISTA_PARAMETARA);
    ruleTable.put("<lista_deklaracija>", ListaDeklaracija.LISTA_DEKLARACIJA); 
    ruleTable.put("<deklaracija>", Deklaracija.DEKLARACIJA); 
    ruleTable.put("<lista_init_deklaratora>", ListaInitDeklaratora.LISTA_INIT_DEKLARATORA);
    ruleTable.put("<init_deklarator>", InitDeklarator.INIT_DEKLARATOR);
  }

  public static Rule getRule(String name) {
    return ruleTable.get(name);
  }

  public static Rule getRule(Symbol symbol) {
    return getRule((String) symbol.getValue());
  }

  public static Rule getRule(SNode node) {
    return getRule(node.getSymbol());
  }
}
