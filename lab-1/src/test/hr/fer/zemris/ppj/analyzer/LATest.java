package hr.fer.zemris.ppj.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import hr.fer.zemris.ppj.analyizer.LA;

public class LATest {

  @Test
  public void basicLATest() {
    List<String> definitionLines = new ArrayList<>();
    definitionLines.add("S_pocetno");
    definitionLines.add("S_pocetno");
    definitionLines.add("#\\|");
    definitionLines.add("S_komentar");
    definitionLines.add("\\|#");
    definitionLines.add("S_komentar");
    definitionLines.add("\\n");
    definitionLines.add("S_komentar");
    definitionLines.add("\\(|\\)|\\{|\\}|\\||\\*|\\$|\\\\"); // specijalni znakovi ( ) { } | * $ |
                                                             // prefiksani znakom \
    definitionLines.add("S_pocetno");
    definitionLines.add("-(\\n|\\t|\\_)*-"); // -{bjelina}*-
    definitionLines.add("S_pocetno");
    definitionLines.add("\\((\\n|\\t|\\_)*-"); // -{bjelina}*-
    definitionLines.add("S_unarni");
    definitionLines.add("-");
    definitionLines.add("S_unarni");
    definitionLines.add("-(\\n|\\t|\\_)*-");

    List<String> sourceCodeLines = new ArrayList<>();
    sourceCodeLines.add("#| ovo je primjer |#");
    sourceCodeLines.add("3 - -0x12 - ( #| ovdje ce doci grupirane");
    sourceCodeLines.add("   operacije |#");
    sourceCodeLines.add("3- -");
    sourceCodeLines.add("--076) #| 3 - ---076 = 3 - -076 = 3 + 076 |#");

    List<String> expectedOutput = new ArrayList<>();
    expectedOutput.add("OPERAND 2 3");
    expectedOutput.add("OP_MINUS 2 -");
    expectedOutput.add("UMINUS 2 -");
    expectedOutput.add("OPERAND 2 0x12");
    expectedOutput.add("OP_MINUS 2 -");
    expectedOutput.add("LIJEVA_ZAGRADA 2 (");
    expectedOutput.add("OPERAND 4 3");
    expectedOutput.add("OP_MINUS 4 -");
    expectedOutput.add("UMINUS 4 -");
    expectedOutput.add("UMINUS 5 -");
    expectedOutput.add("UMINUS 5 -");
    expectedOutput.add("OPERAND 5 076");
    expectedOutput.add("DESNA_ZAGRADA 5 )");

    LA la = new LA(definitionLines, sourceCodeLines);
    la.analyzeSourceCode();

    assertEquals(expectedOutput, la.getOutput());
  }
}
