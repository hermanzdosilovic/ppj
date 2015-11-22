package hr.fer.zemris.ppj.lab2;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class TableBuilderTest {
  private KanonGrammarFactory kanonGrammar;

  @Before
  public void createKanonGrammarFactory() {
    kanonGrammar = new KanonGrammarFactory();
  }

  @Test
  public void buildActionTableTest() {
    Map<Pair<Set<Set<LRItem>>, TerminalSymbol>, Action> actualActionTable =
        TableBuilder.buildActionTable(kanonGrammar.expectedDFA, kanonGrammar.initialCompleteLRItem);

    assertEquals(kanonGrammar.expectedActionTable, actualActionTable);
  }

  @Test
  public void buildNewStateTableTest() {
    Map<Pair<Set<Set<LRItem>>, NonTerminalSymbol>, Action> actualNewStateTable =
        TableBuilder.buildNewStateTable(kanonGrammar.expectedDFA);

    assertEquals(kanonGrammar.expectedNewStateTable, actualNewStateTable);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void actionTableSerializerTest() throws IOException, ClassNotFoundException {
    ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(new FileOutputStream(ParserDeserializer.ACTION_TABLE));
    objectOutputStream.writeObject(kanonGrammar.expectedActionTable);
    objectOutputStream.close();

    ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(ParserDeserializer.ACTION_TABLE));
    Map<Pair<Set<Set<LRItem>>, TerminalSymbol>, Action> actualActionTable =
        (Map<Pair<Set<Set<LRItem>>, TerminalSymbol>, Action>) objectInputStream.readObject();
    objectInputStream.close();

    assertEquals(kanonGrammar.expectedActionTable, actualActionTable);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void newStateTableSerializerTest() throws IOException, ClassNotFoundException {
    ObjectOutputStream objectOutputStream =
        new ObjectOutputStream(new FileOutputStream(ParserDeserializer.NEW_STATE_TABLE));
    objectOutputStream.writeObject(kanonGrammar.expectedNewStateTable);
    objectOutputStream.close();

    ObjectInputStream objectInputStream =
        new ObjectInputStream(new FileInputStream(ParserDeserializer.NEW_STATE_TABLE));
    Map<Pair<Set<Set<LRItem>>, NonTerminalSymbol>, Action> actualNewStateTable =
        (Map<Pair<Set<Set<LRItem>>, NonTerminalSymbol>, Action>) objectInputStream.readObject();
    objectInputStream.close();

    assertEquals(kanonGrammar.expectedNewStateTable, actualNewStateTable);
  }
}
