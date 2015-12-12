package hr.fer.zemris.ppj.lab3.analyzer;

import hr.fer.zemris.ppj.node.SNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SemantickiAnalizator {

  public static SNode root;

  public static void main(String[] args) throws IOException {
    SemantickiAnalizator sa = new SemantickiAnalizator();

  }

  public SemantickiAnalizator() throws IOException {
    this(System.in);
  }

  public SemantickiAnalizator(InputStream stream) throws IOException {
    root = treeBuilder(new SemAnInputDefinition(stream).inputLines, 0);

  }

  public static SNode treeBuilder(List<String> inputLines, int index) {

    int countSpacesIndex = countSpaces(inputLines.get(index));
    SNode node = new SNode(inputLines.get(index).substring(countSpacesIndex));
    
    int i = 1;
    if (index + i >= inputLines.size()) {
      return node;
    }

    for (; index + i < inputLines.size()
        && countSpacesIndex < countSpaces(inputLines.get(index + i)); i++) {
      if (countSpaces(inputLines.get(index + i)) - countSpacesIndex == 1) {
        node.addChild(treeBuilder(inputLines, index + i));
      }
    }
    
    return node;
  }

  static int countSpaces(String line) {
    int count = 0;
    for (int j = 0; j < line.length(); j++) {
      if (line.charAt(j) == ' ') {
        count++;
      } else
        break;
    }
    return count;
  }
}
