package hr.fer.zemris.ppj.symbol;

/**
 * @author Herman Zvonimir Dosilovic
 *
 * @param <T> type of non terminal symbol
 */
public final class NonTerminalSymbol<T> extends Symbol<T> {
  public NonTerminalSymbol(T value) {
    super(value);
  }
}
