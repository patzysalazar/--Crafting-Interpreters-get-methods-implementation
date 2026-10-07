//> Classes lox-class
package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Classes lox-class < Classes lox-class-callable
class LoxClass {
*/
//> lox-class-callable
class LoxClass extends LoxInstance implements LoxCallable {
//< lox-class-callable
  final String name;
//> Inheritance lox-class-superclass-field
  final LoxClass superclass;
//< Inheritance lox-class-superclass-field
/* Classes lox-class < Classes lox-class-methods

  LoxClass(String name) {
    this.name = name;
  }
*/
//> lox-class-methods
  private final Map<String, LoxFunction> methods;
  private final Map<String, LoxFunction> statMethods;

/* Classes lox-class-methods < Inheritance lox-class-constructor
  LoxClass(String name, Map<String, LoxFunction> methods) {
*/
//> Inheritance lox-class-constructor
  LoxClass(String name, LoxClass superclass,
           Map<String, LoxFunction> methods, Map<String, LoxFunction> statMethods) {
    super();
    this.superclass = superclass;
//< Inheritance lox-class-constructor
    this.name = name;
    this.methods = methods;
    this.statMethods = statMethods;
  }
//< lox-class-methods
//> lox-class-find-method
  LoxFunction findMethod(String name) {
    if (methods.containsKey(name)) {
      return methods.get(name);
    }

//> Inheritance find-method-recurse-superclass
    if (superclass != null) {
      return superclass.findMethod(name);
    }

//< Inheritance find-method-recurse-superclass
    return null;
  }
//< lox-class-find-method

  @Override
  public String toString() {
    return name;
  }
//> lox-class-call-arity
  @Override
  public Object call(Interpreter interpreter,
                     List<Object> arguments) {
    LoxInstance instance = new LoxInstance(this);
//> lox-class-call-initializer
    LoxFunction initializer = findMethod("init");
    if (initializer != null) {
      initializer.bind(instance).call(interpreter, arguments);
    }

//< lox-class-call-initializer
    return instance;
  }

  @Override
  public int arity() {
/* Classes lox-class-call-arity < Classes lox-initializer-arity
    return 0;
*/
//> lox-initializer-arity
    LoxFunction initializer = findMethod("init");
    if (initializer == null) return 0;
    return initializer.arity();
//< lox-initializer-arity
  }
//< lox-class-call-arity
    //get looks at dot from class and looks for name in static method, but this time lox handles get to search for static methods rather than normal methods
    Object get(Token name)//overriden method from lox Instance bc we need to implement get for static methods
    {
     if(statMethods.get(name.lexeme) == null)
        {
            throw new RuntimeError(name, // [hidden]
                "Undefined property '" + name.lexeme + "'.");
        }
     else{
        return statMethods.get(name.lexeme);
     }

}
}
