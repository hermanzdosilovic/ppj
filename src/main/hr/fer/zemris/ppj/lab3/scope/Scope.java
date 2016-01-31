package hr.fer.zemris.ppj.lab3.scope;

import hr.fer.zemris.ppj.lab3.types.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Scope {
  private Scope parentScope;
  private Map<String, ScopeValue> table = new HashMap<>();
  private List<Scope> childrenScopes = new ArrayList<>();
  private Map<String, Integer> stackOffset = new HashMap<>();

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
  
  public Map<String, ScopeValue> getTable() {
    return table;
  }
  
  public void setOffset(String variable, int offset) {
    stackOffset.put(variable, offset);
  }
  
  public int getOffset(String variable) {
    Scope parent = this;
    do {
      if (parent.stackOffset.containsKey(variable)) {
        return parent.stackOffset.get(variable);
      }
      parent = parent.getParentScope();
    } while (parent != null && parent.getParentScope() != null);
    return -1;
  }
  
  public int numberOfLocalVariables() {
    int cnt = 0;
    for (int offset : stackOffset.values()) {
      if (offset < 0) {
        cnt++;
      }
    }
    return cnt;
  }
  
  public int lastOffset() {
    int mini = 0;
    Scope parent = this;
    do {
      for (int offset : parent.stackOffset.values()) {
        if (offset < mini) {
          mini = offset;
        }
      }
      parent = parent.getParentScope();
    } while (parent != null && parent.getParentScope() != null);
    return mini;
  }
  
  public int getCurrentStackSize() {
    return stackOffset.keySet().size();
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
