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
  public LRItem i00, i01, i02, i03, i04, i05, i06, i07, i08, i09, i10, i11, i12, i13, i14, i15, i16,
      i17, i18, i19, i20, i21, i22, i23, i24, i25, i26, i27, i28, i29, i30, i31, i32, i33, i34, i35,
      i36, i37, i38, i39, i40, i41, i42, i43, i44, i45, i46;

  public MinusLangGrammarFactory() {
    createSymbols();
    createAlphabet();
    createInitialProduction();
    createInitialCompleteLRItem();
    createGrammar();
    createLRItems();
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
  
  private void createLRItems() {
    i00 = new LRItem(new Production(exprNEW, expr), 0, Arrays.asList(end));
    i01 = new LRItem(new Production(exprNEW, expr), 1, Arrays.asList(end));
    i02 = new LRItem(new Production(expr, atom), 0, Arrays.asList(end));
    i03 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 0, Arrays.asList(end));
    i04 = new LRItem(new Production(expr, atom), 1, Arrays.asList(end));
    i05 = new LRItem(new Production(expr, OPERAND), 0, Arrays.asList(end));
    i06 = new LRItem(new Production(atom, UMINUS, atom), 0, Arrays.asList(end));
    i07 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 0, Arrays.asList(end));
    i08 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 1, Arrays.asList(end));
    i09 = new LRItem(new Production(expr, atom), 0, Arrays.asList(OP_MINUS));
    i10 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 0, Arrays.asList(OP_MINUS));
    i11 = new LRItem(new Production(atom, OPERAND), 1, Arrays.asList(end));
    i12 = new LRItem(new Production(atom, UMINUS, atom), 1, Arrays.asList(end));
    i13 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 1, Arrays.asList(end));
    i14 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 2, Arrays.asList(end));
    i15 = new LRItem(new Production(expr, atom), 1, Arrays.asList(OP_MINUS));
    i16 = new LRItem(new Production(atom, OPERAND), 0, Arrays.asList(OP_MINUS));
    i17 = new LRItem(new Production(atom, UMINUS, atom), 0, Arrays.asList(OP_MINUS));
    i18 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 0, Arrays.asList(OP_MINUS));
    i19 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 1, Arrays.asList(OP_MINUS));
    i20 = new LRItem(new Production(atom, UMINUS, atom), 2, Arrays.asList(end));
    i21 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 2, Arrays.asList(end));
    i22 = new LRItem(new Production(expr, atom), 0, Arrays.asList(DESNA_ZAGRADA));
    i23 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 0, Arrays.asList(DESNA_ZAGRADA));
    i24 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 3, Arrays.asList(end));
    i25 = new LRItem(new Production(atom, OPERAND), 1, Arrays.asList(OP_MINUS));
    i26 = new LRItem(new Production(atom, UMINUS, atom), 1, Arrays.asList(OP_MINUS));
    i27 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 1, Arrays.asList(OP_MINUS));
    i28 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 1, Arrays.asList(OP_MINUS));
    i29 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 3, Arrays.asList(end));
    i30 = new LRItem(new Production(expr, atom), 1, Arrays.asList(DESNA_ZAGRADA));
    i31 = new LRItem(new Production(atom, OPERAND), 0, Arrays.asList(DESNA_ZAGRADA));
    i32 = new LRItem(new Production(atom, UMINUS, atom), 0, Arrays.asList(DESNA_ZAGRADA));
    i33 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 0, Arrays.asList(DESNA_ZAGRADA));
    i34 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 1, Arrays.asList(DESNA_ZAGRADA));
    i35 = new LRItem(new Production(atom, UMINUS, atom), 2, Arrays.asList(OP_MINUS));
    i36 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 2, Arrays.asList(OP_MINUS));
    i37 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 3, Arrays.asList(OP_MINUS));
    i38 = new LRItem(new Production(atom, OPERAND), 1, Arrays.asList(DESNA_ZAGRADA));
    i39 = new LRItem(new Production(atom, UMINUS, atom), 1, Arrays.asList(DESNA_ZAGRADA));
    i40 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 1, Arrays.asList(DESNA_ZAGRADA));
    i41 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 2, Arrays.asList(DESNA_ZAGRADA));
    i42 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 3, Arrays.asList(OP_MINUS));
    i43 = new LRItem(new Production(atom, UMINUS, atom), 2, Arrays.asList(DESNA_ZAGRADA));
    i44 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 2, Arrays.asList(DESNA_ZAGRADA));
    i45 = new LRItem(new Production(expr, expr, OP_MINUS, atom), 3, Arrays.asList(DESNA_ZAGRADA));
    i46 = new LRItem(new Production(atom, LIJEVA_ZAGRADA, expr, DESNA_ZAGRADA), 3, Arrays.asList(DESNA_ZAGRADA));
  }
}





















