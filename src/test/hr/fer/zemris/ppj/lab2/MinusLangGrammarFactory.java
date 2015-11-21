package hr.fer.zemris.ppj.lab2;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.grammar.Grammar;
import hr.fer.zemris.ppj.grammar.Production;
import hr.fer.zemris.ppj.lab2.analyzer.SA;
import hr.fer.zemris.ppj.lab2.parser.LRItem;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;
import hr.fer.zemris.ppj.symbol.Symbol;
import hr.fer.zemris.ppj.symbol.TerminalSymbol;

public class MinusLangGrammarFactory {
  public NonTerminalSymbol exprNEW, expr, atom;
  public TerminalSymbol OPERAND, OP_MINUS, UMINUS, LIJEVA_ZAGRADA, DESNA_ZAGRADA, end;
  public List<Symbol> alphabet;
  public Production initialProduction;
  public LRItem initialCompleteLRItem;
  public Grammar grammar;

  public MinusLangGrammarFactory() {
    createSymbols();
    createAlphabet();
    createInitialProduction();
    createInitialCompleteLRItem();
    createGrammar();
  }

  private void createSymbols() {
    exprNEW = new NonTerminalSymbol("<expr>++");
    expr = new NonTerminalSymbol("<expr>");
    atom = new NonTerminalSymbol("<atom>");
    OPERAND = new TerminalSymbol("OPERAND");
    OP_MINUS = new TerminalSymbol("OP_MINUS");
    UMINUS = new TerminalSymbol("UMINUS");
    LIJEVA_ZAGRADA = new TerminalSymbol("LIJEVA_ZAGRADA");
    DESNA_ZAGRADA = new TerminalSymbol("DESNA_ZAGRADA");
    end = new TerminalSymbol(SA.END_STRING);
  }

  private void createAlphabet() {
    alphabet = Arrays.asList(exprNEW, expr, atom, OPERAND, OP_MINUS, UMINUS, LIJEVA_ZAGRADA,
        DESNA_ZAGRADA); // not sure about SA.END_STRING
  }

  private void createInitialProduction() {
    initialProduction = new Production(exprNEW, expr);
  }

  private void createInitialCompleteLRItem() {
    initialCompleteLRItem = new LRItem(initialProduction, 1, Arrays.asList(end));
  }

  private void createGrammar() {
    grammar =
        new Grammar(Arrays.asList(new Production(atom, OPERAND), new Production(atom, UMINUS, atom),
            new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), new Production(expr, atom),
            new Production(expr, OP_MINUS, atom)), exprNEW);
  }
}
