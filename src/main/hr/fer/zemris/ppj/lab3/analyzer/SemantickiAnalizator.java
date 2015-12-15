package hr.fer.zemris.ppj.lab3.analyzer;

import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SemantickiAnalizator {
  private SNode root;

  public static void main(String[] args) throws IOException {
    SemantickiAnalizator sa = new SemantickiAnalizator();
    try {
      sa.root.visit(new Scope());
    } catch (SemanticException e) {
      System.out.println(e.getMessage());
    }
  }

  public SemantickiAnalizator() throws IOException {
    this(System.in);
  }

  public SemantickiAnalizator(InputStream stream) throws IOException {
    root = treeBuilder(new SemAnInputDefinition(stream).getInputLines(), 0);
  }

  public SNode getRoot() {
    return root;
  }

  public static SNode treeBuilder(List<String> inputLines, int index) {
    int countSpacesIndex = countSpaces(inputLines.get(index));
    SNode node = new SNode();

    for (int i = 1; index + i < inputLines.size()
        && countSpacesIndex < countSpaces(inputLines.get(index + i)); i++) {
      if (countSpaces(inputLines.get(index + i)) - countSpacesIndex == 1) {
        node.addChild(treeBuilder(inputLines, index + i));
      }
    }

    String line = inputLines.get(index).trim();
    if (node.getChildren().isEmpty()) {
      node.setSymbol(new TerminalSymbol(line.substring(0, line.indexOf(' '))));
      line = line.substring(line.indexOf(' ') + 1);
      node.setLineNumber(Integer.valueOf(line.substring(0, line.indexOf(' '))));
      node.setValue(line.substring(line.indexOf(' ') + 1));
    } else {
      node.setSymbol(new NonTerminalSymbol(line));
    }
    return node;
  }

  public static int countSpaces(String line) {
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
