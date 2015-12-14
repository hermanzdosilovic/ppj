package hr.fer.zemris.ppj.symbol;

/**
 * @author Herman Zvonimir Dosilovic
 * 
 * @param <T> type of terminal symbol
 */
public final class TerminalSymbol extends Symbol {
  private static final long serialVersionUID = 7680926996707314498L;

  public TerminalSymbol(Object value) {
    super(value);
  }
}
