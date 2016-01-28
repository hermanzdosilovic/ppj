package hr.fer.zemris.ppj.lab3.rules.definitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Array;
import hr.fer.zemris.ppj.lab3.types.NumericType;
import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.lab3.types.TypesHelper;
import hr.fer.zemris.ppj.lab4.GeneratorKoda;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

public class InitDeklarator extends Rule {
  public static InitDeklarator INIT_DEKLARATOR = new InitDeklarator();

  private InitDeklarator() {
    super(new NonTerminalSymbol("<init_deklarator>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<izravni_deklarator>"))) {
      if (scope.getParentScope() == null) {
        GeneratorKoda.isGlobalCode = true;
      }
      SNode izravni_deklarator = node.getChildren().get(0);

      // 1
      izravni_deklarator.setnType(node.getnType());
      izravni_deklarator.visit(scope);
      
      if (GeneratorKoda.isGlobalCode && izravni_deklarator.getValuesOfChildren().equals(Arrays.asList("IDN"))) {
        String name = izravni_deklarator.getChildren().get(0).getValue();
        List<String> value = new ArrayList<String>();
        value.add("0");
        GeneratorKoda.globalneVarijable.put(name, value);
        GeneratorKoda.writeln("\tPOP R0");
      }
      
      GeneratorKoda.isGlobalCode = false;

      // 2
      if (TypesHelper.isConstT(izravni_deklarator.getType())
          || TypesHelper.isArrayConstT(izravni_deklarator.getType())) {
        throw new SemanticException(getErrorMessage(node));
      }
    } else if (children
        .equals(Arrays.asList("<izravni_deklarator>", "OP_PRIDRUZI", "<inicijalizator>"))) {
      if (scope.getParentScope() == null) {
        GeneratorKoda.isGlobalCode = true;
      }
      SNode izravni_deklarator = node.getChildren().get(0);
      // 1
      izravni_deklarator.setnType(node.getnType());
      izravni_deklarator.visit(scope);

      // 2
      SNode inicijalizator = node.getChildren().get(2);
      inicijalizator.visit(scope);

      // 3
      if (TypesHelper.isT(izravni_deklarator.getType())
          || TypesHelper.isConstT(izravni_deklarator.getType())) {
        if (!TypesHelper.canImplicitlyCast(inicijalizator.getType(),
            TypesHelper.getTFromX((NumericType) izravni_deklarator.getType()))) {
          throw new SemanticException(getErrorMessage(node));
        }
      } else if ((TypesHelper.isArray(izravni_deklarator.getType())
          || TypesHelper.isArrayConstT(izravni_deklarator.getType()))
          && inicijalizator.getElemCount() <= izravni_deklarator.getElemCount()) {
        if (inicijalizator.getElemCount() == 0) {
          throw new SemanticException(getErrorMessage(node));
        }
        for (Type U : inicijalizator.getTypes()) {
          if (!TypesHelper.canImplicitlyCast(U,
              TypesHelper.getTFromX(((Array) izravni_deklarator.getType()).getNumericType()))) {
            throw new SemanticException(getErrorMessage(node));
          }
        }
      } else {
        throw new SemanticException(getErrorMessage(node));
      }
      GeneratorKoda.writeln("\tPOP R0");
      if (inicijalizator.getValuesOfChildren().equals(Arrays.asList("<izraz_pridruzivanja>"))) {
        SNode izraz_pridruzivanja = inicijalizator.getChildren().get(0);
        if (izraz_pridruzivanja.islValue()) {
          GeneratorKoda.writeln("\tLOAD R0, (R0)");
        }
      }
      GeneratorKoda.writeln("\tPUSH R0");

      if (GeneratorKoda.isGlobalCode) {
        String key = "";
        List<String> value = new ArrayList<String>();

        if (izravni_deklarator.getValuesOfChildren().equals(Arrays.asList("IDN"))) {
          key = izravni_deklarator.getChildren().get(0).getValue();
          SNode newNode = inicijalizator;
          value.add("0");
          GeneratorKoda.writeln("\tPOP R0");
          GeneratorKoda.writeln("\tSTORE R0, (" + GeneratorKoda.getGlobalVariableLabel(key) + ")");
        } else if (izravni_deklarator.getValuesOfChildren()
            .equals(Arrays.asList("IDN", "L_UGL_ZAGRADA", "BROJ", "D_UGL_ZAGRADA"))) {
          SNode broj = izravni_deklarator.getChildren().get(2);
          key = izravni_deklarator.getChildren().get(0).getValue();
          SNode newNode;
          if (inicijalizator.getValuesOfChildren().contains("<lista_izraza_pridruzivanja>")) {
            newNode = inicijalizator.getChildren().get(1);

            for (int i = 1; i <= Integer.parseInt(broj.getValue()); i++) {
              for (int j = Integer.parseInt(broj.getValue()) - i; j > 0; j--) {
                newNode = newNode.getChildren().get(0);
              }
              value.add("0");

              for (int j = Integer.parseInt(broj.getValue()) - i; j > 0; j--) {
                newNode = newNode.getParent();
              }
            }
          }
        }
        if (key != "" && !GeneratorKoda.globalneVarijable.containsKey(key)) {
          if (value.isEmpty())
            value.add("0");
          GeneratorKoda.globalneVarijable.put(key, value);
        }
      }
      GeneratorKoda.isGlobalCode = false;
    }
  }

  // private String findValue(SNode newNode) {
  // String value = "";
  //
  // if (newNode.getValuesOfChildren().contains("<izraz_pridruzivanja>")) {
  // if (newNode.getValuesOfChildren().equals(Arrays.asList("<izraz_pridruzivanja>")))
  // newNode = newNode.getChildren().get(0);
  // else
  // newNode = newNode.getChildren().get(2);
  // if (newNode.getValuesOfChildren().contains("<log_ili_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<log_i_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<bin_ili_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<bin_xili_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<bin_i_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<jednakosni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<odnosni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<aditivni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<multiplikativni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<cast_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<unarni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<postfiks_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<primarni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("BROJ")) {
  // value = newNode.getChildren().get(0).getValue();
  // } else if (newNode.getValuesOfChildren().contains("ZNAK")) {
  // value = Integer.toString(
  // (int) newNode.getChildren().get(0).getValue().charAt(1));
  // } else if (newNode.getValuesOfChildren().contains("IDN")) {
  // value = GeneratorKoda.globalneVarijable
  // .get(newNode.getChildren().get(0).getValue()).get(0);
  // }
  // } else if (newNode.getValuesOfChildren()
  // .contains("<postfiks_izraz>")) {
  // String varijabla = "";
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<primarni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("IDN")) {
  // varijabla = newNode.getChildren().get(0).getValue();
  // }
  // newNode = newNode.getParent();
  // newNode = newNode.getParent();
  // }
  // newNode = newNode.getChildren().get(2);
  // int index = Integer.parseInt(findValue(newNode));
  // value = GeneratorKoda.globalneVarijable.get(varijabla).get(index);
  // }
  //
  // } else if (newNode.getValuesOfChildren().contains("OP_INC")
  // || newNode.getValuesOfChildren().contains("OP_DEC")) {
  // boolean flag = false;
  // if (newNode.getValuesOfChildren().get(0).equals("OP_INC")) {
  // flag = true;
  // }
  // if (newNode.getValuesOfChildren().contains("<unarni_izraz>")) {
  // newNode = newNode.getChildren().get(1);
  // if (newNode.getValuesOfChildren().contains("<postfiks_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("<primarni_izraz>")) {
  // newNode = newNode.getChildren().get(0);
  // if (newNode.getValuesOfChildren().contains("IDN")) {
  // value = GeneratorKoda.globalneVarijable
  // .get(newNode.getChildren().get(0).getValue()).get(0);
  // if (flag) {
  // value = Integer.toString(Integer.parseInt(value) + 1);
  // } else {
  // value = Integer.toString(Integer.parseInt(value) - 1);
  // }
  // }
  //
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // }
  // return value;
  // }
}
