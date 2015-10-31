package hr.fer.zemris.ppj.regex;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that serves as a resolver for regular expressions which contain names of regular
 * definitions. Regular definitions are given while this object is constructed. Expected class for
 * regular definitions is {@link RegularDefinition}. A map of resolved definitions is built in the
 * constructor. RegDefResolver offers a method which can resolve a given regular expression using
 * the map of regular definitions. Regular expression is resolved if it doesn't contain any
 * references to regular definitions.
 * 
 * @author Josipa Kelava
 *
 */
public class RegularDefinitionResolver {

  private Map<String, String> resolvedRegDef = new HashMap<String, String>();

  /**
   * Constructor takes a list of regular definitions and creates a map of resolved regular
   * definitions. If a regular definition R1 is using another regular definition R2, R2 must come
   * before R1 in the given list.
   * 
   * @param regularDefinitions - list of regular definitions
   */
  public RegularDefinitionResolver(List<RegularDefinition> regularDefinitions) {
    resolve(regularDefinitions);
  }

  /**
   * Resolves a list of regular definitions.
   * 
   * @param regularDefinitions - list of regular definitions
   * @return map which contains resolved regular definitions
   */
  private Map<String, String> resolve(List<RegularDefinition> regularDefinitions) {

    for (RegularDefinition regDef : regularDefinitions) {
      resolvedRegDef.put(regDef.getName(), resolveRegex(regDef.getValue()));
    }

    return resolvedRegDef;
  }

  /**
   * Resolves given regular expression using the map of regular defintions which was created in
   * constructor.
   * 
   * @param regEx
   * @return resolved regular expression
   */
  public String resolveRegex(String regEx) {

    int beginIndex = -1;
    int endIndex = -1;
    int count = 0;

    for (int i = 0; i < regEx.length(); i++) {

      if (regEx.charAt(i) == '{' && count % 2 == 0) {
        beginIndex = i;
      }

      if (regEx.charAt(i) == '}' && count % 2 == 0 && beginIndex != -1) {
        endIndex = i;
      }

      if (regEx.charAt(i) == '\\') {
        count++;
      } else {
        count = 0;
      }

      if (beginIndex != -1 && endIndex != -1) {

        String value = resolvedRegDef.get(regEx.substring(beginIndex + 1, endIndex));

        regEx = regEx.substring(0, beginIndex) + "(" + value + ")"
            + regEx.substring(endIndex + 1, regEx.length());

        i = beginIndex;
        beginIndex = -1;
        endIndex = -1;
      }
    }

    return regEx;
  }
}
