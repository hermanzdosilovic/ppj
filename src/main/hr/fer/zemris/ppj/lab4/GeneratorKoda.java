package hr.fer.zemris.ppj.lab4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.ppj.lab3.analyzer.SemantickiAnalizator;

public class GeneratorKoda {
  public static String MULT_LABEL = "F_1";
  public static String DIV_LABEL = "F_2";
  public static String MOD_LABEL = "F_3";

  private static BufferedWriter fileWriter;
  private static int counter = 0;
  
  public static Set<Long> constants = new HashSet<Long>(); 
  public static Deque<String> povratneLabele = new ArrayDeque<String>();
  public static Deque<String> prekidneLabele = new ArrayDeque<String>();
  
  public static boolean inUse;
  
  public static void main(String[] args) throws IOException {
    fileWriter = new BufferedWriter(new FileWriter(new File("a.frisc")));
    writeln("\tMOVE 40000, R7");
    writeln("\tCALL F_main");
    writeln("\tHALT");
    inUse = true;
    SemantickiAnalizator.main(args);
    
    modulo();
    division();
    multiplication();
    constants();
    fileWriter.close();


  }

  /**
   * Writes line to <i>a.frisc</i> file if <code>GeneratorKoda.main</i> was started. Does not add
   * new line to file.
   * 
   * @param line to write
   */
  public static void write(String line) {
    if (fileWriter == null) {
      return;
    }
    System.err.print(line);
    try {
      fileWriter.write(line);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Write line to <i>a.frisc</i> file if <code>GeneratorKoda.main</i> was started. Does add new
   * line to file.
   * 
   * @param line to write
   */
  public static void writeln(String line) {
    write(line + "\n");
  }

  private static void multiplication() {
    writeln("");
    writeln(MULT_LABEL + "\tPUSH R0");
    writeln("\tPUSH R1");

    writeln("\tLOAD R0,(R7+010)");
    writeln("\tLOAD R1,(R7+0C)");
    
    writeln("\tMOVE 0, R6");
    String labela = getNextLabel();
    writeln(labela + "\tADD R0, R6, R6");
    writeln("\tSUB R1, 1, R1");
    writeln("\tJP_NZ " + labela);

 
    writeln("\tPOP R1");
    writeln("\tPOP R0");
    writeln("\tRET");
    writeln("");
  }
  
  private static void division() {
    writeln("");
    writeln(DIV_LABEL + "\tPUSH R0");
    writeln("\tPUSH R1");
    writeln("\tPUSH R2");
    
    
    writeln("\tLOAD R0,(R7+014)");
    writeln("\tLOAD R1,(R7+010)");
   
    writeln("\tMOVE 0, R2");
    writeln("\tCMP R0, 0");
    String labela3 = getNextLabel();
    writeln("\tJP_SGT " + labela3);
    writeln("\tMOVE 1, R2");
    writeln("\tXOR R0, -1, R0");
    writeln("\tADD R0, 1, R0");
    
    writeln(labela3 + "\tCMP R1, 0");
    String labela4 = getNextLabel();
    writeln("\tJP_SGT " + labela4);
    writeln("\tXOR R2, 1, R2");
    writeln("\tXOR R1, -1, R1");
    writeln("\tADD R1, 1, R1");
    
    writeln(labela4 + "\tMOVE 0, R6");
    String labela1 = getNextLabel();
    writeln(labela1 + "\tSUB R0, R1, R0");
    String labela2 = getNextLabel();
    writeln("\tJR_ULT " + labela2);
    
    writeln("\tADD R6, 1, R6");
    writeln("\tJP " + labela1);
    
    writeln(labela2 + "\tCMP R2, 0");
    String labela5 = getNextLabel();
    writeln("\tJP_EQ " + labela5);
    writeln("\tXOR R6, -1, R6");
    writeln("\tADD R6, 1, R6");

    writeln(labela5 + "\tPOP R2");
    writeln("\tPOP R1");
    writeln("\tPOP R0");
    writeln("\tRET");
    writeln("");
  }
  
  private static void modulo() {
    writeln("");
    writeln(MOD_LABEL + "\tPUSH R0");
    writeln("\tPUSH R1");
    writeln("\tPUSH R2");
    
    writeln("\tLOAD R0, (R7+014)");
    writeln("\tLOAD R1, (R7+010)");
     
    // dijeljenje
    writeln("\tPUSH R6");
    writeln("\tPUSH R0");
    writeln("\tPUSH R1");
    writeln("\tCALL " + DIV_LABEL);
    
    writeln("\tPOP R1");
    writeln("\tPOP R0");
    
    writeln("\tADD R6, 0, R2");
    writeln("\tPOP R6");
    
    //mnozenje
    writeln("\tPUSH R6");
    writeln("\tPUSH R1");
    writeln("\tPUSH R2");
    writeln("\tCALL " + MULT_LABEL);
    
    writeln("\tPOP R2");
    writeln("\tPOP R1");
    
    writeln("\tADD R6, 0, R2");
    writeln("\tPOP R6");
    
    //oduzimanje
    writeln("\tSUB R0, R2, R6");
    
    writeln("\tPOP R2");
    writeln("\tPOP R1");
    writeln("\tPOP R0");
    writeln("\tRET");
    writeln("");
    
    
  }
  
  public static void constants(){
    writeln("\t`ORG 20000");
    for(Long constant : constants){
      writeln("C_" + constant.toString() + " `DW %D " + constant.toString());
    }
  }
  
  
  /**
   * Takes a name of a global variable and returns a label representing that
   * global variable in FRISC code. 
   * @param name - name of a global variable
   * @return label representing provided variable
   */
  public static String getGlobalVariableLabel(String name) {
    return "G_" + name.toUpperCase();
  }
  
  /**
   * Takes a name of a function and returns a label representing
   * that function in FRISC code.
   * @param name - function name
   * @return label representing provided function name
   */
  public static String getFunctionLabel(String name) {
    return "F_" + name.toUpperCase();
  }
  
  /**
   * Generates a new label with unique name.
   * @return new label
   */
  public static String getNextLabel() {
    return "L_" + counter++;
  }
  
  public static String getConstantLabel(long value) {
    return "C_" + value;
  }
}
  