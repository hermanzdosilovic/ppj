package hr.fer.zemris.ppj.lab3.rules.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Int;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab4.GeneratorKoda;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class NaredbaPetlje extends Rule {

  public static NaredbaPetlje NAREDBA_PETLJE = new NaredbaPetlje();

  private NaredbaPetlje() {
    super(new NonTerminalSymbol("<naredba_petlje>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> childrenValues = node.getValuesOfChildren();
    List<SNode> children = node.getChildren();
    if (childrenValues.equals(Arrays.asList("KR_WHILE", "L_ZAGRADA", "<izraz>", "D_ZAGRADA",
        "<naredba>"))) {

      String labela1 = GeneratorKoda.getNextLabel();
      String labela2 = GeneratorKoda.getNextLabel();
      GeneratorKoda.writeln(labela2);
      children.get(2).visit(scope);

      if (!TypesHelper.canImplicitlyCast(children.get(2).getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.povratneLabele.push(labela2);
      GeneratorKoda.prekidneLabele.push(labela1);

      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tCMP R0, 0");
      GeneratorKoda.writeln("\tJP_EQ " + labela1);

      children.get(4).visit(scope);
      GeneratorKoda.writeln("\tJP " + labela2);

      GeneratorKoda.writeln(labela1);

      GeneratorKoda.povratneLabele.pop();
      GeneratorKoda.prekidneLabele.pop();

    } else if (childrenValues.equals(Arrays.asList("KR_FOR", "L_ZAGRADA", "<izraz_naredba>",
        "<izraz_naredba>", "D_ZAGRADA", "<naredba>"))) {

      String labela1 = GeneratorKoda.getNextLabel();
      String labela2 = GeneratorKoda.getNextLabel();

      children.get(2).visit(scope);
      GeneratorKoda.writeln(labela2);
      children.get(3).visit(scope);

      if (!TypesHelper.canImplicitlyCast(children.get(3).getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.povratneLabele.push(labela2);
      GeneratorKoda.prekidneLabele.push(labela1);

      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tCMP R0, 0");
      GeneratorKoda.writeln("\tJP_EQ " + labela1);

      children.get(5).visit(scope);

      GeneratorKoda.writeln("\tJP " + labela2);
      GeneratorKoda.writeln(labela1);

      GeneratorKoda.povratneLabele.pop();
      GeneratorKoda.prekidneLabele.pop();

    } else if (childrenValues.equals(Arrays.asList("KR_FOR", "L_ZAGRADA", "<izraz_naredba>",
        "<izraz_naredba>", "<izraz>", "D_ZAGRADA", "<naredba>"))) {

      String labela1 = GeneratorKoda.getNextLabel();
      String labela2 = GeneratorKoda.getNextLabel();

      children.get(2).visit(scope);
      GeneratorKoda.writeln(labela2);
      children.get(3).visit(scope);
      if (!TypesHelper.canImplicitlyCast(children.get(3).getType(), Int.INT)) {
        throw new SemanticException(getErrorMessage(node));
      }

      GeneratorKoda.povratneLabele.push(labela2);
      GeneratorKoda.prekidneLabele.push(labela1);

      children.get(4).visit(scope);
      GeneratorKoda.writeln("\tPOP R0");
      GeneratorKoda.writeln("\tCMP R0, 0");
      GeneratorKoda.writeln("\tJP_EQ " + labela1);

      children.get(6).visit(scope);
      GeneratorKoda.writeln("\tJP " + labela2);
      GeneratorKoda.writeln(labela1);

      GeneratorKoda.povratneLabele.pop();
      GeneratorKoda.prekidneLabele.pop();
    }
  }

}
