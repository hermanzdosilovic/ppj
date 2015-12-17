package hr.fer.zemris.ppj.lab3.rules;

import java.util.Map;

import hr.fer.zemris.ppj.lab3.rules.commands.IzrazNaredba;
import hr.fer.zemris.ppj.lab3.rules.commands.ListaNaredbi;
import hr.fer.zemris.ppj.lab3.rules.commands.Naredba;
import hr.fer.zemris.ppj.lab3.rules.commands.NaredbaGrananja;
import hr.fer.zemris.ppj.lab3.rules.commands.NaredbaPetlje;
import hr.fer.zemris.ppj.lab3.rules.commands.NaredbaSkoka;
import hr.fer.zemris.ppj.lab3.rules.commands.SlozenaNaredba;
import hr.fer.zemris.ppj.lab3.rules.definitions.DefinicijaFunkcije;
import hr.fer.zemris.ppj.lab3.rules.definitions.DeklaracijaParametra;
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
    ruleTable.put("<deklaracija parametra>", DeklaracijaParametra.DEKLARACIJA_PARAMETRA);
    
    //Naredbe
    ruleTable.put("<izraz_naredba>", IzrazNaredba.IZRAZ_NAREDBA);
    ruleTable.put("<lista_naredbi>", ListaNaredbi.LISTA_NAREDBI);
    ruleTable.put("<naredba>", Naredba.NAREDBA);
    ruleTable.put("<naredba_grananja>", NaredbaGrananja.NAREDBA_GRANANJA);
    ruleTable.put("<naredba_petlje>", NaredbaPetlje.NAREDBA_PETLJE);
    ruleTable.put("<naredba_skoka>", NaredbaSkoka.NAREDBA_SKOKA);
    ruleTable.put("<slozena_naredba>", SlozenaNaredba.SLOZENA_NAREDBA);
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
