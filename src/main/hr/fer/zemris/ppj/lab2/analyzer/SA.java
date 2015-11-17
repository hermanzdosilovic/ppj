package hr.fer.zemris.ppj.lab2.analyzer;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.lab2.parser.action.RejectAction;
import hr.fer.zemris.ppj.node.Node;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of syntax analyzer. When constructed, analyzer has a table of actions and new
 * states, starting state and list of synchronization signs. It has a method which simulates an LR
 * parser.
 * 
 * 
 * @author Josipa Kelava
 *
 */

public class SA {


  private Map<Pair<Integer, String>, Action> actions;
  private Map<Pair<Integer, String>, MoveAction> newState;
  private List<String> synStrings;
  private Integer startState;
  public static final String EPSILON = "$";
  public static final String END_STRING = "<posljednji_znakic>";
  
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    List<String> input = readInput(System.in);
    input.add(END_STRING + " kraj T");
  }
  
  public static List<String> readInput(InputStream inputStream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line = "";
    List<String> input = new ArrayList<>();
    while((line = reader.readLine()) != null) {
      input.add(line);
    }
    return input;
  }

  /**
   * Constructs syntax analyzer with given parameters. It takes table of actions and new states,
   * starting state and list of synchronization signs which will be used for error recovery.
   * 
   * @param actions
   * @param newState
   * @param synStrings
   * @param startState
   */
  public SA(Map<Pair<Integer, String>, Action> actions,
      Map<Pair<Integer, String>, MoveAction> newState, List<String> synStrings, Integer startState) {
    this.actions = actions;
    this.newState = newState;
    this.synStrings = synStrings;
    this.startState = startState;

  }

  /**
   * This method simulates an LR parser using structures which were provided in the constructor. It
   * goes through input, checks if it can be parsed correctly. It builds a syntax tree of a LR
   * parser using {@link Node}. If input can be parsed correctly then a root of the tree is
   * returned, otherwise null is returned In case of an error method prints message to System.err
   * containing line in which the error occurred, signs which wouldn't cause an error and sign which
   * caused the error. If an error occurred, method continues reading the input until it finds a
   * synchronization sign and ignores all lexical units between the error and the synchronization
   * sign.
   * 
   * @param input
   * @return The root of the tree
   */
  public Node LR(List<String> input) {

    Node root = null;

    Deque<Integer> stackState = new ArrayDeque<Integer>();
    Deque<Node> stackValue = new ArrayDeque<Node>();

    stackState.push(startState);

    for (int index = 0; index < input.size() && !stackState.isEmpty();) {

      String currentInput = input.get(index);
      String[] splitInput = currentInput.split(" ");

      Pair<Integer, String> pair = new Pair<Integer, String>(stackState.peek(), splitInput[0]);
      Node node = new Node(currentInput);

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
          parent.addChild(new Node(EPSILON));
        }

        Deque<Node> reversedChildren = new ArrayDeque<Node>();
        for (int i = 0; i < reduceNumber; i++) {
          stackState.pop();
          reversedChildren.push(stackValue.pop());
        }
        while (!reversedChildren.isEmpty()) {
          parent.addChild(reversedChildren.pop());
        }

        MoveAction moveAction =
            newState.get(new Pair<Integer, String>(stackState.peek(), leftSide));

        stackState.push(moveAction.getState());
        stackValue.push(parent);
      } else if (action instanceof AcceptAction) {
        index++;
        root = stackValue.pop();
      } else {
        errorOutput(stackState.peek(), splitInput);

        for (; index < input.size(); index++) {

          currentInput = input.get(index);
          splitInput = currentInput.split(" ");
          if (synStrings.contains(splitInput[0])) {

            pair = new Pair<Integer, String>(stackState.peek(), splitInput[0]);
            while (!stackState.isEmpty()) {
              if (!(actions.get(pair) instanceof RejectAction)) {
                break;
              } else {
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

  private void errorOutput(Integer state, String[] podjela) {
    System.err.println("Broj retka: " + podjela[1]);


    System.err.print("Znakovi koji ne bi izazvali pogresku:");
    for (Map.Entry<Pair<Integer, String>, Action> entry : actions.entrySet()) {
      if (entry.getKey().getFirst().equals(state) && !(entry.getValue() instanceof RejectAction)) {
        System.err.print(" " + entry.getKey().getSecond());
      }
    }
    System.err.println();

    System.err.println(podjela[0] + " " + podjela[2]);

  }
}
