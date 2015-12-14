package hr.fer.zemris.ppj.helpers;

import java.util.Stack;

/**
 * @author Herman Zvonimir Dosilovic
 */
public final class Stopwatch {
  private static Stack<Long> startTime = new Stack<>();

  private Stopwatch() {}

  public static void start() {
    startTime.push(System.currentTimeMillis());
  }

  public static String end() {
    int duration = (int) (System.currentTimeMillis() - startTime.pop());
    return String.format("%02d:%02d:%02d", (duration / (1000 * 60 * 60)) % 24,
        (duration / (1000 * 60)) % 60, (duration / 1000) % 60);
  }
}
