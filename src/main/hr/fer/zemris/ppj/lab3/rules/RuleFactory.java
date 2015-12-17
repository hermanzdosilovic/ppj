package hr.fer.zemris.ppj.lab3.rules;

import java.util.Map;

import hr.fer.zemris.ppj.lab3.rules.definitions.DefinicijaFunkcije;
import hr.fer.zemris.ppj.lab3.rules.definitions.DeklaracijaParametra;
import hr.fer.zemris.ppj.lab3.rules.expression.AditivniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.BinIIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.BinIliIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.BinXiliIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.CastIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.ImeTipa;
import hr.fer.zemris.ppj.lab3.rules.expression.JednakosniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.ListaArgumenata;
import hr.fer.zemris.ppj.lab3.rules.expression.LogIIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.LogIliIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.MultiplikativniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.OdnosniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.PostfiksIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.PrimarniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.SpecifikatorTipa;
import hr.fer.zemris.ppj.lab3.rules.expression.UnarniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.UnarniOperator;
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
    ruleTable.put("<postfiks_izraz>", PostfiksIzraz.POSTFIKS_IZRAZ);
    ruleTable.put("<lista_argumenata>", ListaArgumenata.LISTA_ARGUMENATA);
    ruleTable.put("<unarni_izraz>", UnarniIzraz.UNARNI_IZRAZ);
    ruleTable.put("<unarni_operator>", UnarniOperator.UNARNI_OPERATOR);
    ruleTable.put("<cast_izraz>", CastIzraz.CAST_IZRAZ);
    ruleTable.put("<ime_tipa>", ImeTipa.IME_TIPA);
    ruleTable.put("<specifikator_tipa>", SpecifikatorTipa.SPECIFIKATOR_TIPA);
    ruleTable.put("<multiplikativni_izraz>", MultiplikativniIzraz.MULTIPLIKATIVNI_IZRAZ);
    ruleTable.put("<aditivni_izraz>", AditivniIzraz.ADITIVNI_IZRAZ);
    ruleTable.put("<odnosni_izraz>", OdnosniIzraz.ODNOSNI_IZRAZ);
    ruleTable.put("<jednakosni_izraz>", JednakosniIzraz.JEDNAKOSNI_IZRAZ);
    ruleTable.put("<bin_i_izraz>", BinIIzraz.BIN_I_IZRAZ);
    ruleTable.put("<bin_xili_izraz>", BinXiliIzraz.BIN_XILI_IZRAZ);
    ruleTable.put("<bin_ili_izraz>", BinIliIzraz.BIN_ILI_IZRAZ);
    ruleTable.put("<log_i_izraz>", LogIIzraz.LOG_I_IZRAZ);
    ruleTable.put("<log_ili_izraz>", LogIliIzraz.LOG_ILI_IZRAZ);
    
    // Deklaracije i definicije
    ruleTable.put("<definicija_funkcije>", DefinicijaFunkcije.DEFINICIJA_FUNKCIJE);
    ruleTable.put("<deklaracija_parametra>", DeklaracijaParametra.DEKLARACIJA_PARAMETRA);
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
