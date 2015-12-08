package hr.fer.zemris.ppj.lab2.parser.deserializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.parser.LRState;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class ParserDeserializer {

  public static final String ACTION_TABLE = "action_table.ser";
  public static final String NEW_STATE_TABLE = "new_state_table.ser";
  public static final String START_STATE = "start_state.ser";
  public static final String SYN_STRINGS = "syn_strings.ser";

  private Map<Pair<LRState, TerminalSymbol>, Action> actions;
  private Map<Pair<LRState, NonTerminalSymbol>, Action> newState;
  private List<TerminalSymbol> synStrings;
  private LRState startState;

  public void deserializeParserStructures() throws IOException, ClassNotFoundException {
    deserializeActions();
    deserializeNewState();
    deserializeSynStrings();
    deserializeStartState();
  }

  @SuppressWarnings("unchecked")
  public List<TerminalSymbol> deserializeSynStrings() throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(SYN_STRINGS);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    synStrings = (List<TerminalSymbol>) in.readObject();
    in.close();
    fileIn.close();
    return synStrings;
  }

  public LRState deserializeStartState() throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(START_STATE);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    startState = (LRState) in.readObject();
    in.close();
    fileIn.close();
    return startState;
  }

  @SuppressWarnings("unchecked")
  public Map<Pair<LRState, NonTerminalSymbol>, Action> deserializeNewState()
      throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(NEW_STATE_TABLE);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    newState = (Map<Pair<LRState, NonTerminalSymbol>, Action>) in.readObject();
    in.close();
    fileIn.close();
    return newState;
  }

  @SuppressWarnings("unchecked")
  public Map<Pair<LRState, TerminalSymbol>, Action> deserializeActions()
      throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(ACTION_TABLE);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    actions = (Map<Pair<LRState, TerminalSymbol>, Action>) in.readObject();
    in.close();
    fileIn.close();
    return actions;
  }

  public Map<Pair<LRState, TerminalSymbol>, Action> getActions() {
    return actions;
  }

  public Map<Pair<LRState, NonTerminalSymbol>, Action> getNewState() {
    return newState;
  }

  public List<TerminalSymbol> getSynStrings() {
    return synStrings;
  }

  public LRState getStartState() {
    return startState;
  }

}
