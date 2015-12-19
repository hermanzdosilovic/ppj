package hr.fer.zemris.ppj.lab3.rules;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.ppj.lab3.rules.commands.IzrazNaredba;
import hr.fer.zemris.ppj.lab3.rules.commands.ListaNaredbi;
import hr.fer.zemris.ppj.lab3.rules.commands.Naredba;
import hr.fer.zemris.ppj.lab3.rules.commands.NaredbaGrananja;
import hr.fer.zemris.ppj.lab3.rules.commands.NaredbaPetlje;
import hr.fer.zemris.ppj.lab3.rules.commands.NaredbaSkoka;
import hr.fer.zemris.ppj.lab3.rules.commands.SlozenaNaredba;
import hr.fer.zemris.ppj.lab3.rules.definitions.DefinicijaFunkcije;
import hr.fer.zemris.ppj.lab3.rules.definitions.Deklaracija;
import hr.fer.zemris.ppj.lab3.rules.definitions.DeklaracijaParametra;
import hr.fer.zemris.ppj.lab3.rules.definitions.Inicijalizator;
import hr.fer.zemris.ppj.lab3.rules.definitions.InitDeklarator;
import hr.fer.zemris.ppj.lab3.rules.definitions.IzravniDeklarator;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaDeklaracija;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaInitDeklaratora;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaIzrazaPridruzivanja;
import hr.fer.zemris.ppj.lab3.rules.definitions.ListaParametara;
import hr.fer.zemris.ppj.lab3.rules.expression.AditivniIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.BinIIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.BinIliIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.BinXiliIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.CastIzraz;
import hr.fer.zemris.ppj.lab3.rules.expression.ImeTipa;
import hr.fer.zemris.ppj.lab3.rules.expression.Izraz;
import hr.fer.zemris.ppj.lab3.rules.expression.IzrazPridruzivanja;
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

  private static Map<String, Rule> ruleTable = new HashMap<>();

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
    ruleTable.put("<izraz_pridruzivanja>", IzrazPridruzivanja.IZRAZ_PRIDRUZIVANJA);
    ruleTable.put("<izraz>", Izraz.IZRAZ);

    // Deklaracije i definicije
    ruleTable.put("<definicija_funkcije>", DefinicijaFunkcije.DEFINICIJA_FUNKCIJE);
    ruleTable.put("<deklaracija_parametra>", DeklaracijaParametra.DEKLARACIJA_PARAMETRA);
    ruleTable.put("<lista_parametara>", ListaParametara.LISTA_PARAMETARA);
    ruleTable.put("<lista_deklaracija>", ListaDeklaracija.LISTA_DEKLARACIJA); 
    ruleTable.put("<deklaracija>", Deklaracija.DEKLARACIJA); 
    ruleTable.put("<lista_init_deklaratora>", ListaInitDeklaratora.LISTA_INIT_DEKLARATORA);
    ruleTable.put("<init_deklarator>", InitDeklarator.INIT_DEKLARATOR);
    ruleTable.put("<izravni_deklarator>", IzravniDeklarator.IZRAVNI_DEKLARATOR);
    ruleTable.put("<inicijalizator>", Inicijalizator.INICIJALIZATOR);
    ruleTable.put("<lista_izraza_pridruzivanja>", ListaIzrazaPridruzivanja.LISTA_IZRAZA_PRIDRUZIVANJA);
    
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
