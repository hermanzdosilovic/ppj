package hr.fer.zemris.ppj.lab3.scope;

import hr.fer.zemris.ppj.lab3.types.Type;

import java.util.Map;

public class Scope {

  private Scope parentScope;
  private Map<String, ScopeValue> table;

  public Scope(Scope parentScope) {
    this.parentScope = parentScope;
  }

  public Scope getParentScope() {
    return parentScope;
  }

  public boolean checkName(String name) {
    if (table.containsKey(name)) {
      return false;
    } else {
      return true;
    }
  }

  public void insert(String name, Type type, boolean def) {
    ScopeValue value = new ScopeValue(type, def);
    table.put(name, value);
  }

  public Type returnType(String name) {
    if (table.containsKey(name)) {
      return table.get(name).type;
    }
    return null;
  }

  public boolean returndDef(String name) {
    if (table.containsKey(name)) {
      return table.get(name).def;
    }
    return false;
  }

  public void define(String name, boolean def) {
    if (table.containsKey(name)) {
      table.get(name).setDef(def);
    }
  }

  private class ScopeValue {

    private Type type;
    private boolean def;

    private ScopeValue(Type type, boolean def) {
      this.type = type;
      this.def = def;
    }

    public void setDef(boolean def) {
      this.def = def;
    }

  }
}
