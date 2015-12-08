package hr.fer.zemris.ppj.lab2.parser.action;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SerializationExample {

  @SuppressWarnings("unchecked")
  @Test
  public void test() {
    Map<String, Action> m = new HashMap<String, Action>();
    Action a = new MoveAction<>(5);
    m.put("action", a);
    try {
       FileOutputStream fileOut =
       new FileOutputStream("action_map.ser");
       ObjectOutputStream out = new ObjectOutputStream(fileOut);
       out.writeObject(m);
       out.close();
       fileOut.close();
    } catch(Exception e) {
        e.printStackTrace();
    }
    
    Map<String, Action> m2 = null;
    try {
       FileInputStream fileIn = new FileInputStream("action_map.ser");
       ObjectInputStream in = new ObjectInputStream(fileIn);
       m2 = (Map<String, Action>) in.readObject();
       in.close();
       fileIn.close();
       Files.delete(FileSystems.getDefault().getPath("action_map.ser"));
    } catch(Exception e) {
      System.err.print(e);
    }
    assertEquals(m.keySet(), m2.keySet());
    Action a1 = m.get("action");
    Action a2 = m2.get("action");
    assertEquals(a1 instanceof MoveAction, a2 instanceof MoveAction);
    if (a1 instanceof MoveAction && a2 instanceof MoveAction) {
      assertEquals(((MoveAction<?>) a1).getState(), ((MoveAction<?>) a2).getState());
    }
  }

}
