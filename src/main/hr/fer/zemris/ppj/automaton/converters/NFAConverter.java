package hr.fer.zemris.ppj.automaton.converters;

import java.util.HashSet;

import hr.fer.zemris.ppj.automaton.Automaton;

public class NFAConverter {

  public static <S, C> Automaton convertToDFA(Automaton automaton) {



    return automaton;

  }



  HashSet<String> getpowerset(int a[], int size, HashSet<String> states) {
    if (size < 0) {
      return null;
    }

    if (size == 0) {
      if (states == null)
        states = new HashSet<String>();
      states.add(" ");
      return states;
    }
    states = getpowerset(a, size - 1, states);
    HashSet<String> tmpStates = new HashSet<String>();
    for (String state : states) {
      if (state.equals(" "))
        tmpStates.add("" + a[size - 1]);
      else
        tmpStates.add(state + a[size - 1]);
    }
    states.addAll(tmpStates);
    return states;
  }

  HashSet<String> getpowerset2(int size, HashSet<String> states) {
    if (size < 0) {
      return null;
    }

    if (size == 0) {
      if (states == null)
        states = new HashSet<String>();
      states.add(" ");
      return states;
    }
    states = getpowerset2(size - 1, states);
    HashSet<String> tmpStates = new HashSet<String>();
    for (String state : states) {
      if (state.equals(" "))
        tmpStates.add("");
      else
        tmpStates.add(state);
    }
    states.addAll(tmpStates);
    return states;
  }
}
