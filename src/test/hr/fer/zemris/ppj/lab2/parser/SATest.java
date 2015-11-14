package hr.fer.zemris.ppj.lab2.parser;

import static org.junit.Assert.*;
import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.analyzer.SA;
import hr.fer.zemris.ppj.lab2.parser.action.AcceptAction;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;
import hr.fer.zemris.ppj.lab2.parser.action.ReduceAction;
import hr.fer.zemris.ppj.node.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SATest {

  @Test
  public void test() {
    Map<Pair<Integer, String>, Action> actions = new HashMap<Pair<Integer, String>, Action>();
    actions.put(new Pair<Integer, String>(0, "a"), new MoveAction(3));
    actions.put(new Pair<Integer, String>(0, "b"), new MoveAction(4));
    actions.put(new Pair<Integer, String>(0, "T"), new ReduceAction(0, "A"));
    actions.put(new Pair<Integer, String>(1, "T"), new AcceptAction());
    actions.put(new Pair<Integer, String>(2, "a"), new MoveAction(3));
    actions.put(new Pair<Integer, String>(2, "b"), new MoveAction(4));
    actions.put(new Pair<Integer, String>(2, "T"), new ReduceAction(0, "A"));
    actions.put(new Pair<Integer, String>(3, "a"), new MoveAction(3));
    actions.put(new Pair<Integer, String>(3, "b"), new MoveAction(4));
    actions.put(new Pair<Integer, String>(4, "a"), new ReduceAction(1, "B"));
    actions.put(new Pair<Integer, String>(4, "b"), new ReduceAction(1, "B"));
    actions.put(new Pair<Integer, String>(4, "T"), new ReduceAction(1, "B"));
    actions.put(new Pair<Integer, String>(5, "T"), new ReduceAction(2, "A"));
    actions.put(new Pair<Integer, String>(6, "T"), new ReduceAction(2, "B"));
    actions.put(new Pair<Integer, String>(6, "a"), new ReduceAction(2, "B"));
    actions.put(new Pair<Integer, String>(6, "b"), new ReduceAction(2, "B"));

    Map<Pair<Integer, String>, MoveAction> newState =
        new HashMap<Pair<Integer, String>, MoveAction>();
    newState.put(new Pair<Integer, String>(0, "B"), new MoveAction(2));
    newState.put(new Pair<Integer, String>(0, "A"), new MoveAction(1));
    newState.put(new Pair<Integer, String>(2, "B"), new MoveAction(2));
    newState.put(new Pair<Integer, String>(2, "A"), new MoveAction(5));
    newState.put(new Pair<Integer, String>(3, "B"), new MoveAction(6));

    List<String> input = new ArrayList<>();

    input.add("a 1 x x x");
    input.add("b 2 y y");
    input.add("a 3 xx xx");
    input.add("a 4 xx xx xx");
    input.add("b 4 y");
    input.add("T 5 1");

    List<String> syn = new ArrayList<String>();
    syn.add("b");

    SA sa = new SA(actions, newState, syn, 0);
    StringBuilder expected = new StringBuilder();
    expected.append("A").append(System.lineSeparator()).append(" B").append(System.lineSeparator())
        .append("  a 1 x x x").append(System.lineSeparator()).append("  B")
        .append(System.lineSeparator()).append("   b 2 y y").append(System.lineSeparator())
        .append(" A").append(System.lineSeparator()).append("  B").append(System.lineSeparator())
        .append("   a 3 xx xx").append(System.lineSeparator()).append("   B")
        .append(System.lineSeparator()).append("    a 4 xx xx xx").append(System.lineSeparator())
        .append("    B").append(System.lineSeparator()).append("     b 4 y")
        .append(System.lineSeparator()).append("  A").append(System.lineSeparator()).append("   $")
        .append(System.lineSeparator());

    assertEquals(expected.toString(), Node.printTree(sa.LR(input)));
  }

}
