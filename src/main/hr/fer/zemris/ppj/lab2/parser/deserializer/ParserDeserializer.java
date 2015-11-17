package hr.fer.zemris.ppj.lab2.parser.deserializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ppj.Pair;
import hr.fer.zemris.ppj.lab2.parser.action.Action;
import hr.fer.zemris.ppj.lab2.parser.action.MoveAction;

public class ParserDeserializer {

  public static final String ACTION_TABLE = "action_table.ser";
  public static final String NEW_STATE_TABLE = "new_state_table.ser";
  public static final String START_STATE = "start_state.ser";
  public static final String SYN_STRINGS = "syn_strings.ser";
  
  private Map<Pair<Integer, String>, Action> actions;
  private Map<Pair<Integer, String>, MoveAction> newState;
  private List<String> synStrings;
  private Integer startState;
  
  public void deserializeParserStructures() throws IOException, ClassNotFoundException {
    deserializeActions();
    deserializeNewState();
    deserializeSynStrings();
    deserializeStartState();
  }

  @SuppressWarnings("unchecked")
  public List<String> deserializeSynStrings() throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(SYN_STRINGS);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    synStrings = (List<String>) in.readObject();
    in.close();
    fileIn.close();
    return synStrings;
  }

  public Integer deserializeStartState() throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(START_STATE);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    startState = (Integer) in.readObject();
    in.close();
    fileIn.close();
    return startState;
  }

  @SuppressWarnings("unchecked")
  public Map<Pair<Integer, String>, MoveAction> deserializeNewState() throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(NEW_STATE_TABLE);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    newState = (Map<Pair<Integer, String>, MoveAction>) in.readObject();
    in.close();
    fileIn.close();
    return newState;
  }

  @SuppressWarnings("unchecked")
  public Map<Pair<Integer, String>, Action> deserializeActions() throws IOException, ClassNotFoundException {
    FileInputStream fileIn = new FileInputStream(ACTION_TABLE);
    ObjectInputStream in = new ObjectInputStream(fileIn);
    actions = (Map<Pair<Integer, String>, Action>) in.readObject();
    in.close();
    fileIn.close();
    return actions;
  }

  public Map<Pair<Integer, String>, Action> getActions()  {
    return actions;
  }

  public Map<Pair<Integer, String>, MoveAction> getNewState() {
    return newState;
  }

  public List<String> getSynStrings() {
    return synStrings;
  }

  public Integer getStartState() {
    return startState;
  }
  
}
