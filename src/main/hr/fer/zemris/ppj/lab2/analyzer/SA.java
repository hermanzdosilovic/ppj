package hr.fer.zemris.ppj.lab2.analyzer;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.node.Node;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SA {

  private List<String> input;
  private Map<Pair<Integer, String>, Action> actions;
  private Map<Pair<Integer, String>, MoveAction> newState;
  private List<String> synStrings;
  private Integer startState;
  private Node root;
  
  public static void main(String[] args) {

    FileInputStream fileIn = new FileInputStream("  ");
    ObjectInputStream in = new ObjectInputStream(fileIn);
    Map<Pair<Integer, String>, Action> actions =
        (Map<Pair<Integer, String>, Action>) in.readObject();



    SA sa = new SA();


  }

  public SA(List<String> input, Map<Pair<Integer, String>, Action> actions,
      Map<Pair<Integer, String>, MoveAction> newState, List<String> synStrings, Integer startState) {
    this.input = input;
    this.actions = actions;
    this.newState = newState;
    this.synStrings = synStrings;
    this.startState = startState;
    
    input.add("&");
  }

  public void LR() {
    
    Integer stanje = startState; 
    Deque<Integer> stackState = new ArrayDeque<Integer>();
    Deque<Node> stackValue = new ArrayDeque<Node>();
    
    stackState.push(startState);
    
    for(int j = 0; j < input.size();) {
      
      String a = input.get(j);
      String[] podjela = a.split(" ");
        
      Pair<Integer, String> pair = new Pair<Integer, String>(stanje, podjela[0]);
      Node node = new Node(a);
      
      Action action = actions.get(pair);
       
      if(action instanceof MoveAction) {
        stackValue.push(node);
        stanje = ((MoveAction) action).getState();
        stackState.push(stanje);
        j++;
      } else if(action instanceof ReduceAction) {
        
        int reduceNumber = ((ReduceAction) action).getHowMany();
        String leftSide = ((ReduceAction) action).getLeftHandSide();
        
        Node parent = new Node(leftSide);
        
        for(int i = 0; i < reduceNumber; i++) {
          stackState.pop();
          parent.addChild(stackValue.pop());
        }
        stanje = stackState.peek();
        Pair<Integer, String> pairNewState = new Pair<Integer, String>(stanje, leftSide);
        MoveAction actionNewState = newState.get(pairNewState);
        stanje = actionNewState.getState();
        stackState.add(stanje);
        stackValue.add(parent);
        
      } else if(action instanceof AcceptAction) {
        j++;
        root = stackValue.pop();
        
      } else {
        //nesto nevalja
      }    
    }
  }
}
