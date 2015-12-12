package hr.fer.zemris.ppj.lab3.analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class SemAnInputDefinition {

  public List<String> inputLines;

  public SemAnInputDefinition(InputStream stream) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String line = reader.readLine();
    while (line != null) {
      inputLines.add(line);
      line = reader.readLine();
    }
  }
}
