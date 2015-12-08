package hr.fer.zemris.ppj.lab2.analyzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.ppj.lab2.GSA;
import hr.fer.zemris.ppj.lab2.parser.deserializer.ParserDeserializer;
import hr.fer.zemris.ppj.node.Node;

public class SATest {

  @Test
  public void kanonGrammarWithGSATest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("io/lab-2/kanon_gramatika.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("io/lab-2/kanon_gramatika.in")));
    input.add(SA.END_STRING + " kraj T");
    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("io/lab-2/kanon_gramatika.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }

  @Test
  public void minusLangWithGSATest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("io/lab-2/minusLang.san")));
    gsa.start();

    List<String> input = SA.readInput(new FileInputStream(new File("io/lab-2/minusLang.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("io/lab-2/minusLang.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }

  @Test
  public void simplePpjLangErrTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("io/lab-2/simplePpjLang.san")));
    gsa.start();

    List<String> input =
        SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_err.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_err.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }

  @Test
  public void simplePpjLangManjiTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("io/lab-2/simplePpjLang.san")));
    gsa.start();

    List<String> input =
        SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_manji.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_manji.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }

  @Test
  public void simplePpjLangNajmanjiTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("io/lab-2/simplePpjLang.san")));
    gsa.start();

    List<String> input =
        SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_najmanji.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_najmanji.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }

  @Test
  public void simplePpjLangVeciTest() throws Exception {
    GSA gsa = new GSA(new FileInputStream(new File("io/lab-2/simplePpjLang.san")));
    gsa.start();

    List<String> input =
        SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_veci.in")));
    input.add(SA.END_STRING + " kraj T");

    ParserDeserializer deserializer = new ParserDeserializer();
    deserializer.deserializeParserStructures();
    SA sa = new SA(deserializer.deserializeActions(), deserializer.deserializeNewState(),
        deserializer.deserializeSynStrings(), deserializer.deserializeStartState());

    Node node = sa.LR(input);

    StringBuilder expectedOutput = new StringBuilder();
    input = SA.readInput(new FileInputStream(new File("io/lab-2/simplePpjLang_veci.out")));
    for (String line : input) {
      expectedOutput.append(line).append(System.lineSeparator());
    }

    assertEquals(expectedOutput.toString(), Node.printTree(node));
  }
}
