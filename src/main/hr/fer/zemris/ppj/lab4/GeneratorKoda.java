package hr.fer.zemris.ppj.lab4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.ppj.lab3.analyzer.SemantickiAnalizator;

public class GeneratorKoda {
  private static BufferedWriter fileWriter;
  private static int counter = 0;
  
  public static void main(String[] args) throws IOException {
    fileWriter = new BufferedWriter(new FileWriter(new File("a.frisc")));
    writeln("\tMOVE 40000, R7");
    writeln("\tCALL F_main");
    writeln("\tHALT");
    SemantickiAnalizator.main(args);


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
<<<<<<< HEAD

  private static void multiplication() {

    writeln("\tPUSH R0");
    writeln("\tPUSH R1");

    writeln("\tLOAD R0,(R7 + 010");
    writeln("\tLOAD R1,(R7 + 0C)");
    
    writeln("\tMOVE 0, R6");
    writeln("PETLJA\tADD R0, R6, R6");
    writeln("\tSUB R1, 1, R1");
    writeln("\tJR_NZ PETLJA");

 
    writeln("\tPOP R1");
    writeln("\tPOP R0");
    writeln("\tRET");
  }
  
  private static void division() {

    writeln("\tPUSH R0");
    writeln("\tPUSH R1");

    writeln("\tLOAD R0,(R7 + 010)");
    writeln("\tLOAD R1,(R7 + 0C)");
    
    writeln("\tPOP R1");
    writeln("\tPOP R0");
    writeln("\tRET");
  }
  
  /**
   * Takes a name of a global variable and returns a label representing that
   * global variable in FRISC code. 
   * @param name - name of a global variable
   * @return label representing provided variable
   */
  public static String getGlobalVariableLabel(String name) {
    return "G_" + name;
  }
  
  /**
   * Takes a name of a function and returns a label representing
   * that function in FRISC code.
   * @param name - function name
   * @return label representing provided function name
   */
  public static String getFunctionLabel(String name) {
    return "F_" + name;
  }
  
  /**
   * Generates a new label with unique name.
   * @return new label
   */
  public static String getNextLabel() {
    return "L_" + counter++;
  }

}
