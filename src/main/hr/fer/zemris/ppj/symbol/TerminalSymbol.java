package hr.fer.zemris.ppj.symbol;

/**
 * @author Herman Zvonimir Dosilovic
 * 
 * @param <T> type of terminal symbol
 */
public final class TerminalSymbol<T> extends Symbol<T> {
  public TerminalSymbol(T value) {
    super(value);
  }
}
