//> Statements and State environment-class
package com.craftinginterpreters.lox;
//environment - stores and looks at local variables using array slots
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Environment {//store values
    //A more efficient environment representation would store local variables in an array and look them up by index.
//> enclosing-field
  final Environment enclosing;
//< enclosing-field
  private final Map<String, Object> values = new HashMap<>();
  private final Object[] val = new Object[21];
//setup array and method for storing values
  void define(int index, Object vals){//put value in slot - array
      val[index] = vals;
  }
  void define(String names,Object vals){
     values.put(names,vals);
  }
//> environment-constructors
  Environment() {
    enclosing = null;
  }

  Environment(Environment enclosing) {
    this.enclosing = enclosing;
  }
//< environment-constructors
//> environment-get

  Object get(Token name) {
    if (values.containsKey(name.lexeme)) {
      return values.get(name.lexeme);
    }
//> environment-get-enclosing

    if (enclosing != null) return enclosing.get(name);
//< environment-get-enclosing

    throw new RuntimeError(name,
        "Undefined variable '" + name.lexeme + "'.");
  }

//< environment-get
//> environment-assign
  void assign(Token name, Object value) {
    if (values.containsKey(name.lexeme)) {
      values.put(name.lexeme, value);
      return;
    }

//> environment-assign-enclosing
    if (enclosing != null) {
      enclosing.assign(name, value);
      return;
    }

//< environment-assign-enclosing
    throw new RuntimeError(name,
        "Undefined variable '" + name.lexeme + "'.");
  }
//< environment-assign
//> environment-define

//< environment-define
//> Resolving and Binding ancestor
  Environment ancestor(int distance) {
    Environment environment = this;
    for (int i = 0; i < distance; i++) {
      environment = environment.enclosing; // [coupled]
    }

    return environment;
  }
//< Resolving and Binding ancestor
//> Resolving and Binding get-at
  Object getAt(int distance, int index) {
    return ancestor(distance).val[index];//retrieve value from slot - array
  }
//< Resolving and Binding get-at
//> Resolving and Binding assign-at
  void assignAt(int distance, int index, Object value) {
    ancestor(distance).val[index] = value; //change a value - array
  }
//< Resolving and Binding assign-at
//> omit
  @Override
  public String toString() {
    String result = values.toString();
    if (enclosing != null) {
      result += " -> " + enclosing.toString();
    }

    return result;
  }
//< omit
}
