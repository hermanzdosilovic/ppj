package hr.fer.zemris.ppj.node;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a node of a tree. A node can have zero or more children.
 * Class has a static method which can output the given tree.
 * 
 * 
 * @author Josipa Kelava
 *
 */

public class Node {

  private String value;
  private List<Node> children = new ArrayList<Node>();

  /**
   * Construct the node with a given value.
   * 
   * @param value
   */
  public Node(String value) {
    this.value = value;
  }

  /**
   * Adds child to the node.
   * 
   * @param child 
   */
  public void addChild(Node child) {
    children.add(child);
  }
  
  /**
   * Makes a string which contains preorder output of this tree. Every child is written in a new line.
   * Child is indrawn for one space more than a parent. 
   * 
   * @param root - root of a tree
   * @return Returns a string that represents this tree.
   */
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
