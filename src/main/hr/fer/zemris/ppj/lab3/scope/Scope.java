package hr.fer.zemris.ppj.lab3.scope;

import hr.fer.zemris.ppj.lab3.types.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scope {
  private Scope parentScope;
  private Map<String, ScopeValue> table;
  private List<Scope> childrenScopes = new ArrayList<>();

  public Scope() {
  }

  public Scope(Scope parentScope) {
    this.parentScope = parentScope;
    parentScope.addChildScope(this);
  }

  public Scope getParentScope() {
    return parentScope;
  }
  
  public Scope getGlobalScope() {
    Scope scope = this;
    while(scope.parentScope != null) {
      scope = scope.parentScope;
    }
    return scope;
  }

  /**
   * Returns <code>true</code> if there is an <strong>declared</strong> object in this scope.
   * 
   * @param name name of wanted object
   * @return <code>true</code> if there is an <strong>declared</strong> object in this scope,
   *         <code>false</code> otherwise.
   */
  public boolean hasDeclared(String name) {
    return table.containsKey(name);
  }

  public void insert(String name, Type type, boolean isDefined) {
    table.put(name, new ScopeValue(type, isDefined));
  }
  
  public Type getType(String name) {
    if (table.containsKey(name)) {
      return table.get(name).type;
    }
    return null;
  }
  
  public void setType(String name, Type type) {
    if (table.containsKey(name)) {
      table.get(name).setType(type);
    } else {
      table.put(name, new ScopeValue(type, false));
    }
  }

  /**
   * Returns true if this scope has defined object with given name.
   * 
   * @param name name to check in scope definition
   * @return true if this scope has defined object with given name
   */
  public boolean hasDefined(String name) {
    if (table.containsKey(name)) {
      return table.get(name).defined;
    }
    return false;
  }

  public void define(String name, boolean isDefined) {
    if (table.containsKey(name)) {
      table.get(name).setDefined(isDefined);
    }
  }
  
  public void addChildScope(Scope child) {
    childrenScopes.add(child);
  }
  
  public List<Scope> getChildrenScopes() {
    return childrenScopes;
  }

  public Set<String> getNames() {
    return table.keySet();
  }
  
  private class ScopeValue {
    private Type type;
    private boolean defined;

    private ScopeValue(Type type, boolean defined) {
      this.type = type;
      this.defined = defined;
    }

    public void setDefined(boolean defined) {
      this.defined = defined;
    }
    
    public void setType(Type type) {
      this.type = type;
    }
  }
}
