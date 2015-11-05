package hr.fer.zemris.ppj.lab1.analyzer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LATest {

  @Test
  public void basicLATest() {
    List<String> definitionLines = new ArrayList<>();
    definitionLines.add("S_pocetno");
    definitionLines.add("S_unarni");
    definitionLines.add("\\t|\\_");
    definitionLines.add("S_unarni");
    definitionLines.add("\\n");
    definitionLines.add("S_unarni");
    definitionLines.add("-");
    definitionLines.add("S_unarni");
    definitionLines.add("-(\\t|\\n|\\_)*-");
    definitionLines.add("S_komentar");
    definitionLines.add("\\|#");
    definitionLines.add("S_komentar");
    definitionLines.add("\\n");
    definitionLines.add("S_komentar");
    definitionLines.add("(\\(|\\)|\\{|\\}|\\||\\*|\\\\|\\$|\\t|\\n|\\_|!|\"|#|%|&|'|+|,|-|.|/|0|1|2|3|4|5|6|7|8|9|:|;|<|=|>|?|@|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|[|]|^|_|`|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|~)");
    definitionLines.add("S_pocetno");
    definitionLines.add("\\t|\\_");
    definitionLines.add("S_pocetno");
    definitionLines.add("\\n");
    definitionLines.add("S_pocetno");
    definitionLines.add("#\\|");
    definitionLines.add("S_pocetno");
    definitionLines.add("((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*|0x((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)((0|1|2|3|4|5|6|7|8|9)|a|b|c|d|e|f|A|B|C|D|E|F)*)");
    definitionLines.add("S_pocetno");
    definitionLines.add("\\(");
    definitionLines.add("S_pocetno");
    definitionLines.add("\\)");
    definitionLines.add("S_pocetno");
    definitionLines.add("-");
    definitionLines.add("S_pocetno");
    definitionLines.add("-(\\t|\\n|\\_)*-");
    definitionLines.add("S_pocetno");
    definitionLines.add("\\((\\t|\\n|\\_)*-");

    StringBuilder sourceCodeBuilder = new StringBuilder();
    sourceCodeBuilder.append("#| ovo je primjer |#\n");
    sourceCodeBuilder.append("3 - -0x12 - ( #| ovdje ce doci grupirane\n");
    sourceCodeBuilder.append("   operacije |#\n");
    sourceCodeBuilder.append("3- -\n");
    sourceCodeBuilder.append("--076) #| 3 - ---076 = 3 - -076 = 3 + 076 |#\n");

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

    LA la = new LA(definitionLines, sourceCodeBuilder.toString());
    la.analyzeSourceCode();

    assertEquals(expectedOutput, la.getOutput());
  }
}
