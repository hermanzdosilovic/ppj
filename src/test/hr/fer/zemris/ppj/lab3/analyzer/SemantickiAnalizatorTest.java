package hr.fer.zemris.ppj.lab3.analyzer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import hr.fer.zemris.ppj.node.SNode;

public class SemantickiAnalizatorTest {

  //ispis mora bit isti kao datoteka koja je procitana
  @Test
  public void buildTreeTest() throws FileNotFoundException, IOException {
    SemantickiAnalizator sa = 
        new SemantickiAnalizator(new FileInputStream(new File("io/lab-3/tests/009fea211f963ef96b16ab2c279ae416.in")));
    System.out.println(SNode.printTree(sa.getRoot()));
  }

}
