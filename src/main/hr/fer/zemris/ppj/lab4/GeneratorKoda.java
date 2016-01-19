package hr.fer.zemris.ppj.lab4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import hr.fer.zemris.ppj.lab3.analyzer.SemantickiAnalizator;

public class GeneratorKoda {
  private static BufferedWriter fileWriter;

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
}
