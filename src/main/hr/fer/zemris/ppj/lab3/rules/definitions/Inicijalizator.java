package hr.fer.zemris.ppj.lab3.rules.definitions;

import hr.fer.zemris.ppj.lab3.analyzer.SemanticException;
import hr.fer.zemris.ppj.lab3.rules.Rule;
import hr.fer.zemris.ppj.lab3.scope.Scope;
import hr.fer.zemris.ppj.lab3.types.Char;
import hr.fer.zemris.ppj.lab3.types.Type;
import hr.fer.zemris.ppj.node.SNode;
import hr.fer.zemris.ppj.symbol.NonTerminalSymbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inicijalizator extends Rule {
  public static Inicijalizator INICIJALIZATOR = new Inicijalizator();

  private Inicijalizator() {
    super(new NonTerminalSymbol("<inicijalizator>"));
  }

  @Override
  public void checkRule(SNode node, Scope scope) throws SemanticException {
    List<String> children = node.getValuesOfChildren();

    if (children.equals(Arrays.asList("<izraz_pridruzivanja>"))) {
      SNode izraz_pridruzivanja = node.getChildren().get(0);

      // 1
      izraz_pridruzivanja.visit(scope);

      SNode trazenoDjete = izraz_pridruzivanja;
      while (trazenoDjete.getChildren().size() == 1) {
        if (trazenoDjete.getName().equals("NIZ_ZNAKOVA")) {
          break;
        }
        trazenoDjete = trazenoDjete.getChildren().get(0);
      }

      if (trazenoDjete.getName().equals("NIZ_ZNAKOVA")) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Integer.parseInt(trazenoDjete.getElemCount()) + 1);
        node.setElemCount(stringBuffer.toString());

        List<Type> tipovi = new ArrayList<Type>();
        for (int i = 0; i < Integer.parseInt(node.getElemCount()); i++) {
          tipovi.add(Char.CHAR);
        }
        node.setTypes(tipovi);
      }

      else {
        node.setType(izraz_pridruzivanja.getType());
      }

    } else if (children.equals(Arrays.asList("L_VIT_ZAGRADA", "<lista_izraza_pridruzivanja>",
        "D_VIT_ZAGRADA"))) {
      SNode lista_izraza_pridruzivanja = node.getChildren().get(1);

      // 1
      lista_izraza_pridruzivanja.visit(scope);

      node.setElemCount(lista_izraza_pridruzivanja.getElemCount());
      node.setTypes(lista_izraza_pridruzivanja.getTypes());
    }
  }
}
