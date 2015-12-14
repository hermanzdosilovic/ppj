package hr.fer.zemris.ppj.node;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class SNode {

  private Symbol symbol;
  private Type type;
  private boolean lValue;
  private List<Type> types;
  private String name;
  private List<String> names;
  private Type nType;
  private String elemCount;
  private int lineNumber;
  private String value;
  private List<SNode> children = new ArrayList<>();
  
  public SNode() {
  }

  

  public Symbol getSymbol() {
    return symbol;
  }



  public void setSymbol(Symbol symbol) {
    this.symbol = symbol;
  }



  public Type getType() {
    return type;
  }



  public void setType(Type type) {
    this.type = type;
  }



  public List<Type> getTypes() {
    return types;
  }



  public void setTypes(List<Type> types) {
    this.types = types;
  }



  public String getName() {
    return name;
  }



  public void setName(String name) {
    this.name = name;
  }



  public List<String> getNames() {
    return names;
  }



  public void setNames(List<String> names) {
    this.names = names;
  }



  public Type getnType() {
    return nType;
  }



  public void setnType(Type nType) {
    this.nType = nType;
  }



  public String getElemCount() {
    return elemCount;
  }



  public void setElemCount(String elemCount) {
    this.elemCount = elemCount;
  }



  public void setlValue(boolean lValue) {
    this.lValue = lValue;
  }


  public boolean islValue() {
    return lValue;
  }



  public int getLineNumber() {
    return lineNumber;
  }



  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }
  
  public void addChild(SNode child) {
    children.add(child);
  }

  public List<SNode> getChildren() {
    return children;
  }
  
  public static String printTree(SNode root) {
    if (root == null) {
      return "";
    }
    StringBuilder output = new StringBuilder();
    buildOutputTree(root, 0, output);
    return output.toString();
  }

  private static void buildOutputTree(SNode node, int spaces, StringBuilder output) {
    for (int i = 0; i < spaces; i++) {
      output.append(" ");
    }
    output.append(node.getSymbol());
    if (node.getSymbol() instanceof TerminalSymbol) {
      output.append(" ").append(node.getLineNumber()).append(" ").append(node.getValue());
    }
    output.append(System.lineSeparator());
    for (SNode m : node.getChildren()) {
      buildOutputTree(m, spaces + 1, output);
    }
  }



  public String getValue() {
    return value;
  }



  public void setValue(String value) {
    this.value = value;
  }
}
