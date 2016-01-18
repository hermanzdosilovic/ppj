package hr.fer.zemris.ppj.lab4;

import java.io.IOException;

import hr.fer.zemris.ppj.lab3.analyzer.SemantickiAnalizator;

public class GeneratorKoda {

  public static void main(String[] args) throws IOException {
    System.out.println("\tMOVE 40000, R7");
    System.out.println("\tCALL F_MAIN");
    System.out.println("\tHALT");
    SemantickiAnalizator.main(args);
  }
}
