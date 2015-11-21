package hr.fer.zemris.ppj.grammar.converters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.lab2.GeneratorInputDefinition;
import hr.fer.zemris.ppj.lab2.KanonGrammarFactory;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

/**
 * @author Herman Zvonimir Dosilovic
 */
public class GrammarEpsilonNFAConverterTest {
  private KanonGrammarFactory kanonGrammarFactory;

  @Before
  public void buildKanonGrammarFactory() {
    kanonGrammarFactory = new KanonGrammarFactory();
  }

  @Test
  public void convertTest() {
    assertEquals(kanonGrammarFactory.expectedENFA,
        GrammarEpsilonNFAConverter.convert(kanonGrammarFactory.grammar, kanonGrammarFactory.end));
  }

  @Test
  public void kanonGrammarTest() throws Exception {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%V <A> <B>");
    inputLines.add("%T a b");
    inputLines.add("%Syn b");
    inputLines.add("<A>");
    inputLines.add(" <B> <A>");
    inputLines.add("<B>");
    inputLines.add(" a <B>");
    inputLines.add(" b");
    inputLines.add("<A>");
    inputLines.add(" $");
    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();

    Grammar grammar =
        Grammar.extendGrammar(generatorInputDefinition.getGrammar(), new NonTerminalSymbol("<%>"));
    Automaton<LRItem, Symbol> eNFA =
        GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol("END"));
    assertEquals(11, eNFA.getNumberOfStates());
    assertEquals(14, eNFA.getNumberOfTransitions());
  }

  @Test
  public void minusLangGrammarTest() throws Exception {
    List<String> inputLines = new ArrayList<>();
    inputLines.add("%V <expr> <atom>");
    inputLines.add("%T OPERAND OP_MINUS UMINUS LIJEVA_ZAGRADA DESNA_ZAGRADA");
    inputLines.add("%Syn OPERAND UMINUS LIJEVA_ZAGRADA");
    inputLines.add("<atom>");
    inputLines.add(" OPERAND");
    inputLines.add(" UMINUS <atom>");
    inputLines.add(" LIJEVA_ZAGRADA <expr> DESNA_ZAGRADA");
    inputLines.add("<expr>");
    inputLines.add(" <atom>");
    inputLines.add(" <expr> OP_MINUS <atom>");

    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();

    Grammar grammar =
        Grammar.extendGrammar(generatorInputDefinition.getGrammar(), new NonTerminalSymbol("<%>"));
    Automaton<LRItem, Symbol> eNFA =
        GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol("END"));
    assertEquals(47, eNFA.getNumberOfStates());
    assertEquals(72, eNFA.getNumberOfTransitions());
  }

  @Test
  public void simplePpjLangGrammarTest() throws Exception {
    List<String> inputLines = new ArrayList<>();
    inputLines.add(
        "%V <prijevodna_jedinica> <vanjska_deklaracija> <deklaracija> <definicija_funkcije> <specifikatori_deklaracije> <primarni_izraz> <izraz> <postfiks_izraz> <lista_argumenata> <izraz_pridruzivanja> <unarni_izraz> <unarni_operator> <cast_izraz> <ime_tipa> <multiplikativni_izraz> <aditivni_izraz> <odnosni_izraz> <jednakosni_izraz> <bin_i_izraz> <bin_xili_izraz> <bin_ili_izraz> <log_i_izraz> <log_ili_izraz> <specifikator_tipa> <lista_init_deklaratora> <init_deklarator> <inicijalizator> <lista_specifikatora_kvalifikatora> <izravni_deklarator> <lista_parametara> <deklaracija_parametra> <lista_izraza_pridruzivanja> <naredba> <slozena_naredba> <izraz_naredba> <naredba_grananja> <naredba_petlje> <naredba_skoka> <lista_naredbi> <lista_deklaracija>");
    inputLines.add(
        "%T IDN BROJ ZNAK NIZ_ZNAKOVA KR_BREAK KR_CHAR KR_CONST KR_CONTINUE KR_ELSE KR_FOR KR_IF KR_INT KR_RETURN KR_VOID KR_WHILE PLUS OP_INC MINUS OP_DEC OP_PUTA OP_DIJELI OP_MOD OP_PRIDRUZI OP_LT OP_LTE OP_GT OP_GTE OP_EQ OP_NEQ OP_NEG OP_TILDA OP_I OP_ILI OP_BIN_I OP_BIN_ILI OP_BIN_XILI ZAREZ TOCKAZAREZ L_ZAGRADA D_ZAGRADA L_UGL_ZAGRADA D_UGL_ZAGRADA L_VIT_ZAGRADA D_VIT_ZAGRADA");
    inputLines.add("%Syn TOCKAZAREZ D_VIT_ZAGRADA");
    inputLines.add("<prijevodna_jedinica>");
    inputLines.add(" <vanjska_deklaracija>");
    inputLines.add(" <prijevodna_jedinica> <vanjska_deklaracija>");
    inputLines.add("<vanjska_deklaracija>");
    inputLines.add(" <definicija_funkcije>");
    inputLines.add(" <deklaracija>");
    inputLines.add("<definicija_funkcije>");
    inputLines.add(" <specifikatori_deklaracije> <izravni_deklarator> <slozena_naredba>");
    inputLines.add("<slozena_naredba>");
    inputLines.add(" L_VIT_ZAGRADA D_VIT_ZAGRADA");
    inputLines.add(" L_VIT_ZAGRADA <lista_naredbi> D_VIT_ZAGRADA");
    inputLines.add(" L_VIT_ZAGRADA <lista_deklaracija> D_VIT_ZAGRADA");
    inputLines.add(" L_VIT_ZAGRADA <lista_deklaracija> <lista_naredbi> D_VIT_ZAGRADA");
    inputLines.add("<lista_deklaracija>");
    inputLines.add(" <deklaracija>");
    inputLines.add(" <lista_deklaracija> <deklaracija>");
    inputLines.add("<lista_naredbi>");
    inputLines.add(" <naredba>");
    inputLines.add(" <lista_naredbi> <naredba>");
    inputLines.add("<naredba>");
    inputLines.add(" <slozena_naredba>");
    inputLines.add(" <izraz_naredba>");
    inputLines.add(" <naredba_grananja>");
    inputLines.add(" <naredba_petlje>");
    inputLines.add(" <naredba_skoka>");
    inputLines.add("<izraz_naredba>");
    inputLines.add(" TOCKAZAREZ");
    inputLines.add(" <izraz> TOCKAZAREZ");
    inputLines.add("<naredba_grananja>");
    inputLines.add(" KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba>");
    inputLines.add(" KR_IF L_ZAGRADA <izraz> D_ZAGRADA <naredba> KR_ELSE <naredba>");
    inputLines.add("<naredba_petlje>");
    inputLines.add(" KR_WHILE L_ZAGRADA <izraz> D_ZAGRADA <naredba>");
    inputLines.add(" KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> D_ZAGRADA <naredba>");
    inputLines.add(" KR_FOR L_ZAGRADA <izraz_naredba> <izraz_naredba> <izraz> D_ZAGRADA <naredba>");
    inputLines.add("<naredba_skoka>");
    inputLines.add(" KR_CONTINUE TOCKAZAREZ");
    inputLines.add(" KR_BREAK TOCKAZAREZ");
    inputLines.add(" KR_RETURN TOCKAZAREZ");
    inputLines.add(" KR_RETURN <izraz> TOCKAZAREZ");
    inputLines.add("<primarni_izraz>");
    inputLines.add(" IDN");
    inputLines.add(" BROJ");
    inputLines.add(" ZNAK");
    inputLines.add(" NIZ_ZNAKOVA");
    inputLines.add(" L_ZAGRADA <izraz> D_ZAGRADA");
    inputLines.add("<postfiks_izraz>");
    inputLines.add(" <primarni_izraz>");
    inputLines.add(" <postfiks_izraz> L_UGL_ZAGRADA <izraz> D_UGL_ZAGRADA");
    inputLines.add(" <postfiks_izraz> L_ZAGRADA D_ZAGRADA");
    inputLines.add(" <postfiks_izraz> L_ZAGRADA <lista_argumenata> D_ZAGRADA");
    inputLines.add(" <postfiks_izraz> OP_INC");
    inputLines.add(" <postfiks_izraz> OP_DEC");
    inputLines.add("<lista_argumenata>");
    inputLines.add(" <izraz_pridruzivanja>");
    inputLines.add(" <lista_argumenata> ZAREZ <izraz_pridruzivanja>");
    inputLines.add("<unarni_izraz>");
    inputLines.add(" <postfiks_izraz>");
    inputLines.add(" OP_INC <unarni_izraz>");
    inputLines.add(" OP_DEC <unarni_izraz>");
    inputLines.add(" <unarni_operator> <cast_izraz>");
    inputLines.add("<unarni_operator>");
    inputLines.add(" PLUS");
    inputLines.add(" MINUS");
    inputLines.add(" OP_TILDA");
    inputLines.add(" OP_NEG");
    inputLines.add("<cast_izraz>");
    inputLines.add(" <unarni_izraz>");
    inputLines.add(" L_ZAGRADA <ime_tipa> D_ZAGRADA <cast_izraz>");
    inputLines.add("<ime_tipa>");
    inputLines.add(" <lista_specifikatora_kvalifikatora>");
    inputLines.add("<lista_specifikatora_kvalifikatora>");
    inputLines.add(" <specifikator_tipa> <lista_specifikatora_kvalifikatora>");
    inputLines.add(" <specifikator_tipa>");
    inputLines.add(" KR_CONST <lista_specifikatora_kvalifikatora>");
    inputLines.add(" KR_CONST");
    inputLines.add("<specifikator_tipa>");
    inputLines.add(" KR_VOID");
    inputLines.add(" KR_CHAR");
    inputLines.add(" KR_INT");
    inputLines.add("<multiplikativni_izraz>");
    inputLines.add(" <cast_izraz>");
    inputLines.add(" <multiplikativni_izraz> OP_PUTA <cast_izraz>");
    inputLines.add(" <multiplikativni_izraz> OP_DIJELI <cast_izraz>");
    inputLines.add(" <multiplikativni_izraz> OP_MOD <cast_izraz>");
    inputLines.add("<aditivni_izraz>");
    inputLines.add(" <multiplikativni_izraz>");
    inputLines.add(" <aditivni_izraz> PLUS <multiplikativni_izraz>");
    inputLines.add(" <aditivni_izraz> MINUS <multiplikativni_izraz>");
    inputLines.add("<odnosni_izraz>");
    inputLines.add(" <aditivni_izraz>");
    inputLines.add(" <odnosni_izraz> OP_LT <aditivni_izraz>");
    inputLines.add(" <odnosni_izraz> OP_GT <aditivni_izraz>");
    inputLines.add(" <odnosni_izraz> OP_LTE <aditivni_izraz>");
    inputLines.add(" <odnosni_izraz> OP_GTE <aditivni_izraz>");
    inputLines.add("<jednakosni_izraz>");
    inputLines.add(" <odnosni_izraz>");
    inputLines.add(" <jednakosni_izraz> OP_EQ <odnosni_izraz>");
    inputLines.add(" <jednakosni_izraz> OP_NEQ <odnosni_izraz>");
    inputLines.add("<bin_i_izraz>");
    inputLines.add(" <jednakosni_izraz>");
    inputLines.add(" <bin_i_izraz> OP_BIN_I <jednakosni_izraz>");
    inputLines.add("<bin_xili_izraz>");
    inputLines.add(" <bin_i_izraz>");
    inputLines.add(" <bin_xili_izraz> OP_BIN_XILI <bin_i_izraz>");
    inputLines.add("<bin_ili_izraz>");
    inputLines.add(" <bin_xili_izraz>");
    inputLines.add(" <bin_ili_izraz> OP_BIN_ILI <bin_xili_izraz>");
    inputLines.add("<log_i_izraz>");
    inputLines.add(" <bin_ili_izraz>");
    inputLines.add(" <log_i_izraz> OP_I <bin_ili_izraz>");
    inputLines.add("<log_ili_izraz>");
    inputLines.add(" <log_i_izraz>");
    inputLines.add(" <log_ili_izraz> OP_ILI <log_i_izraz>");
    inputLines.add("<izraz_pridruzivanja>");
    inputLines.add(" <log_ili_izraz>");
    inputLines.add(" <unarni_izraz> OP_PRIDRUZI <izraz_pridruzivanja>");
    inputLines.add("<izraz>");
    inputLines.add(" <izraz_pridruzivanja>");
    inputLines.add(" <izraz> ZAREZ <izraz_pridruzivanja>");
    inputLines.add("<deklaracija>");
    inputLines.add(" <specifikatori_deklaracije> TOCKAZAREZ");
    inputLines.add(" <specifikatori_deklaracije> <lista_init_deklaratora> TOCKAZAREZ");
    inputLines.add("<specifikatori_deklaracije>");
    inputLines.add(" <specifikator_tipa>");
    inputLines.add(" <specifikator_tipa> <specifikatori_deklaracije>");
    inputLines.add(" KR_CONST");
    inputLines.add(" KR_CONST <specifikatori_deklaracije>");
    inputLines.add("<lista_init_deklaratora>");
    inputLines.add(" <init_deklarator>");
    inputLines.add(" <lista_init_deklaratora> ZAREZ <init_deklarator>");
    inputLines.add("<init_deklarator>");
    inputLines.add(" <izravni_deklarator>");
    inputLines.add(" <izravni_deklarator> OP_PRIDRUZI <inicijalizator>");
    inputLines.add("<izravni_deklarator>");
    inputLines.add(" IDN");
    inputLines.add(" <izravni_deklarator> L_UGL_ZAGRADA <log_ili_izraz> D_UGL_ZAGRADA");
    inputLines.add(" <izravni_deklarator> L_UGL_ZAGRADA D_UGL_ZAGRADA");
    inputLines.add(" <izravni_deklarator> L_ZAGRADA <lista_parametara> D_ZAGRADA");
    inputLines.add("<lista_parametara>");
    inputLines.add(" <deklaracija_parametra>");
    inputLines.add(" <lista_parametara> ZAREZ <deklaracija_parametra>");
    inputLines.add("<deklaracija_parametra>");
    inputLines.add(" <specifikatori_deklaracije> <izravni_deklarator>");
    inputLines.add(" <specifikatori_deklaracije>");
    inputLines.add("<inicijalizator>");
    inputLines.add(" <izraz_pridruzivanja>");
    inputLines.add(" L_VIT_ZAGRADA <lista_izraza_pridruzivanja> D_VIT_ZAGRADA");
    inputLines.add(" L_VIT_ZAGRADA <lista_izraza_pridruzivanja> ZAREZ D_VIT_ZAGRADA");
    inputLines.add("<lista_izraza_pridruzivanja>");
    inputLines.add(" <izraz_pridruzivanja>");
    inputLines.add(" <lista_izraza_pridruzivanja> ZAREZ <izraz_pridruzivanja>");

    GeneratorInputDefinition generatorInputDefinition = new GeneratorInputDefinition(inputLines);
    generatorInputDefinition.readDefinition();
    generatorInputDefinition.parseDefinition();

    Grammar grammar =
        Grammar.extendGrammar(generatorInputDefinition.getGrammar(), new NonTerminalSymbol("<%>"));
    Automaton<LRItem, Symbol> eNFA =
        GrammarEpsilonNFAConverter.convert(grammar, new TerminalSymbol("END"));

    assertEquals(3115, eNFA.getNumberOfStates());
    assertEquals(6343, eNFA.getNumberOfTransitions());
  }
}
