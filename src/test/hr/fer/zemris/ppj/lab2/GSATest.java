package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.automaton.Automaton;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class GSATest {
  private KanonGrammarFactory kanonGrammar;
  
  @Before
  public void createKanonGrammarFactory() {
    kanonGrammar = new KanonGrammarFactory();
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void serializeActionTableTest() throws IOException, ClassNotFoundException {
    new GSA().serialize(kanonGrammar.expectedActionTable, ParserDeserializer.ACTION_TABLE);

    ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(ParserDeserializer.ACTION_TABLE));
    Map<Pair<Set<LRItem>, TerminalSymbol>, Action> actualActionTable =
        (Map<Pair<Set<LRItem>, TerminalSymbol>, Action>) objectInputStream.readObject();
    objectInputStream.close();

    assertEquals(kanonGrammar.expectedActionTable, actualActionTable);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void actionTableTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/kanon_gramatika.san")));
    gsa.start();

    ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(ParserDeserializer.ACTION_TABLE));
    Map<Pair<Set<LRItem>, TerminalSymbol>, Action> actualActionTable =
        (Map<Pair<Set<LRItem>, TerminalSymbol>, Action>) objectInputStream.readObject();
    objectInputStream.close();

    assertEquals(kanonGrammar.expectedActionTable, actualActionTable);
  }

  @Test
  public void getENFATest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/kanon_gramatika.san")));
    gsa.start();

    Automaton<LRItem, Symbol> actualENFA = gsa.getENFA();
    assertEquals(kanonGrammar.expectedENFA, actualENFA);
  }

  @Test
  public void getDFATest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/kanon_gramatika.san")));
    gsa.start();

    Automaton<Set<LRItem>, Symbol> actualDFA = gsa.getDFA();

    assertEquals(kanonGrammar.expectedDFA, actualDFA);
  }
}
