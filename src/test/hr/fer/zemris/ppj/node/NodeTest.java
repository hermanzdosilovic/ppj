package hr.fer.zemris.ppj.node;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

  @Test
  public void test() {
    Node root = new Node ("<A>");
    Node child2 = new Node("a b <S>");
    Node child3 = new Node("1 2 3 4");
    Node child4 = new Node("<B>");
    Node child5 = new Node("<S>");
    Node child7 = new Node("a b c d");
    Node child8 = new Node("1 b c 2");
    Node child9 = new Node("<S> b c <A>");
    Node child10 = new Node("<D>");
    
    root.addChild(child4);
    root.addChild(child5);
    root.addChild(child10);
  
    
    child4.addChild(child9);
    child5.addChild(child8);
    child5.addChild(child7);
    child10.addChild(child2);
    child10.addChild(child3);
    
    System.out.println(Node.printTree(root));
    
    StringBuilder sb = new StringBuilder();
    sb.append("<A>").append(System.lineSeparator())
      .append(" <B>").append(System.lineSeparator())
      .append("  <S> b c <A>").append(System.lineSeparator())
      .append(" <S>").append(System.lineSeparator())
      .append("  1 b c 2").append(System.lineSeparator())
      .append("  a b c d").append(System.lineSeparator())
      .append(" <D>").append(System.lineSeparator())
      .append("  a b <S>").append(System.lineSeparator())
      .append("  1 2 3 4").append(System.lineSeparator());
    
    assertEquals(sb.toString(), Node.printTree(root) );
  }
 
}
