package hr.fer.zemris.ppj.lab3.analyzer;

import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.FunctionType;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.VoidFunctionType;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SemantickiAnalizator {
  private SNode root;
  private Scope globalScope;
  
  public static void main(String[] args) throws IOException {
    SemantickiAnalizator sa = new SemantickiAnalizator();
    try {
      sa.globalScope = new Scope();
      sa.root.visit(sa.globalScope);
      sa.checkMain();
      sa.checkFunction(sa.globalScope);
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

  public void checkMain() throws SemanticException {
    if (!globalScope.hasDefined("main")) {
      throw new SemanticException("main");
    }
    if (!globalScope.getType("main").equals(new VoidFunctionType(Int.INT))) {
      throw new SemanticException("main");
    }
  }
  
  public void checkFunction(Scope scope) throws SemanticException {
    for (String name : scope.getNames()) {
      if (scope.getType(name) instanceof FunctionType && !globalScope.hasDefined(name)) {
        throw new SemanticException("funkcija");
      }
    }
    for (Scope child : scope.getChildrenScopes()) {
      checkFunction(child);
    }
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
      String value = line.substring(line.indexOf(' ') + 1);
      if (node.getSymbol().getValue().equals("IDN")) {
        node.setName(value);
      }
      node.setValue(value);
    } else {
      node.setSymbol(new NonTerminalSymbol(line));
      for (SNode child : node.getChildren()) {
        child.setParent(node);
      }
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
