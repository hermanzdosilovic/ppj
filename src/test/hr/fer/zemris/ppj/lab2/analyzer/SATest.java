package hr.fer.zemris.ppj.lab2.analyzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.ppj.lab2.GSA;
import hr.fer.zemris.ppj.lab2.KanonGrammarFactory;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.node.Node;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class SATest {
  private KanonGrammarFactory kanonGrammar;

  @Before
  public void createKanonGrammarFactory() {
    kanonGrammar = new KanonGrammarFactory();
  }

  @Test
  public void basicTest() {
    List<String> input = new ArrayList<>();
    input.add("a 1 x x x");
    input.add("b 2 y y");
    input.add("a 3 xx xx");
    input.add("a 4 xx xx xx");
    input.add("b 4 y");
    input.add(SA.END_STRING + " 5 T");

    List<TerminalSymbol> syn = Arrays.asList(kanonGrammar.b);

    SA sa = new SA(kanonGrammar.expectedActionTable, kanonGrammar.expectedNewStateTable, syn,
        new ArrayList<>(kanonGrammar.expectedDFA.getInitialState()).get(0));

    StringBuilder expectedOutput = new StringBuilder();
    expectedOutput.append("<A>").append(System.lineSeparator()).append(" <B>")
        .append(System.lineSeparator()).append("  a 1 x x x").append(System.lineSeparator())
        .append("  <B>").append(System.lineSeparator()).append("   b 2 y y")
        .append(System.lineSeparator()).append(" <A>").append(System.lineSeparator())
        .append("  <B>").append(System.lineSeparator()).append("   a 3 xx xx")
        .append(System.lineSeparator()).append("   <B>").append(System.lineSeparator())
        .append("    a 4 xx xx xx").append(System.lineSeparator()).append("    <B>")
        .append(System.lineSeparator()).append("     b 4 y").append(System.lineSeparator())
        .append("  <A>").append(System.lineSeparator()).append("   $")
        .append(System.lineSeparator());

    assertEquals(expectedOutput.toString(), Node.printTree(sa.LR(input)));
  }

  @Test
  public void kanonGrammarWithoutGSATest()
      throws FileNotFoundException, IOException, ClassNotFoundException {
    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/kanon_gramatika.in")));
    input.add(SA.END_STRING + " kraj T");

    SA sa = new SA(kanonGrammar.expectedActionTable, kanonGrammar.expectedNewStateTable,
        Arrays.asList(kanonGrammar.b),
        new ArrayList<>(kanonGrammar.expectedDFA.getInitialState()).get(0));

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/kanon_gramatika.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }

  @Test
  public void kanonGrammarWithGSATest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/kanon_gramatika.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/kanon_gramatika.in")));
    input.add(SA.END_STRING + " kraj T");
    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/kanon_gramatika.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
  
  @Test
  public void minusLangWithGSATest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/minusLang.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/minusLang.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/minusLang.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
  
  @Test
  public void simplePpjLangErrTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/simplePpjLang.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_err.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_err.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
  
  @Test
  public void simplePpjLangManjiTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/simplePpjLang.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_manji.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_manji.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
  
  @Test
  public void simplePpjLangNajmanjiTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/simplePpjLang.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_najmanji.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_najmanji.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
  
  @Test
  public void simplePpjLangVeciTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("langdefs/simplePpjLang.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_veci.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("langdefs/simplePpjLang_veci.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
}
