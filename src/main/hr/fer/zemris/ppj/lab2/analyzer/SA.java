package hr.fer.zemris.ppj.lab2.analyzer;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.lab2.parser.action.RejectAction;
import hr.fer.zemris.ppj.node.Node;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SA {

 
  private Map<Pair<Integer, String>, Action> actions;
  private Map<Pair<Integer, String>, MoveAction> newState;
  private List<String> synStrings;
  private Integer startState;
  public static String epsilon = "$";

  public static void main(String[] args) {

    // FileInputStream fileIn = new FileInputStream("  ");
    // ObjectInputStream in = new ObjectInputStream(fileIn);
    // Map<Pair<Integer, String>, Action> actions =
    // (Map<Pair<Integer, String>, Action>) in.readObject();
    //
    //
    //  
  }

  public SA(Map<Pair<Integer, String>, Action> actions,
      Map<Pair<Integer, String>, MoveAction> newState, List<String> synStrings, Integer startState) {
    this.actions = actions;
    this.newState = newState;
    this.synStrings = synStrings;
    this.startState = startState;

  }
  

  public Node LR(List<String> input) {

    Node root = null;
    
    Deque<Integer> stackState = new ArrayDeque<Integer>();
    Deque<Node> stackValue = new ArrayDeque<Node>();

    stackState.push(startState);

    for (int index = 0; index < input.size() && !stackState.isEmpty();) {

      String a = input.get(index);
      String[] podjela = a.split(" ");

      Pair<Integer, String> pair = new Pair<Integer, String>(stackState.peek(), podjela[0]);
      Node node = new Node(a);

      Action action = actions.get(pair);

      if (action instanceof MoveAction) {
        stackValue.push(node);
       
        stackState.push(((MoveAction) action).getState());
        index++;
      } else if (action instanceof ReduceAction) {

        int reduceNumber = ((ReduceAction) action).getHowMany();
        String leftSide = ((ReduceAction) action).getLeftHandSide();

        Node parent = new Node(leftSide);
        if (reduceNumber == 0) {
          parent.addChild(new Node(epsilon));
        }
        for (int i = 0; i < reduceNumber; i++) {
          stackState.pop();
          parent.addChild(stackValue.pop());
        }
     
        Pair<Integer, String> pairNewState = new Pair<Integer, String>(stackState.peek(), leftSide);
        MoveAction actionNewState = newState.get(pairNewState);
        stackState.add(actionNewState.getState());
        stackValue.add(parent);

      } else if (action instanceof AcceptAction) {
        index++;
        root = stackValue.pop();

      } else {
        errorOutput(stackState, podjela);
      
        for (; index < input.size(); index++) {
        
          if (synStrings.contains(a)) {
            a = input.get(index);
            podjela = a.split(" ");
           
            pair = new Pair<Integer, String>(stackState.peek(), podjela[0]);
            while(!stackState.isEmpty()) {
              if(!(actions.get(pair) instanceof RejectAction)) {
                break;
              }
              else {
                stackState.pop();
                stackValue.pop();
              }
            }
            break;
          }
        }
      }
    }
    return root;
  }
  private void errorOutput( Deque<Integer> stackState, String[] podjela) {
    System.err.println("Broj retka: " + podjela[1]);
    

    System.err.print("Znakovi koji ne bi izazvali pogresku:");
    for (Map.Entry<Pair<Integer, String>, Action> entry : actions.entrySet()) {
      if (entry.getKey().getFirst().equals(stackState.peek())
          && !(entry.getValue() instanceof RejectAction)) {
        System.err.print(" " + entry.getKey().getSecond());
      }
    }
    System.err.println();

    System.err.println(podjela[0] + " " + podjela[2]);

  }
}
