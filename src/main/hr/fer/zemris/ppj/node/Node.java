package hr.fer.zemris.ppj.node;
import java.util.ArrayList;
import java.util.List;


public class Node {

  private String value;
  private List<Node> children = new ArrayList<Node>();

  public Node(String value) {
    this.value = value;
  }

  public void addChild(Node child) {
    children.add(child);
  }

  public static String printTree(Node root) {
    StringBuilder output = new StringBuilder();
    buildOutputTree(root, 0, output);
    return output.toString();
  }

  private static void buildOutputTree(Node node, int spaces, StringBuilder output) {
    for (int i = 0; i < spaces; i++) {
      output.append(" ");
    }
    output.append(node.value).append(System.lineSeparator());
    for (Node m : node.children) {
      buildOutputTree(m, spaces + 1, output);
    }
  }
}
