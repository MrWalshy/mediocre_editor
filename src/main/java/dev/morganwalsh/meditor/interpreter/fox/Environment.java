package dev.morganwalsh.meditor.interpreter.fox;

import java.util.HashMap;
import java.util.Map;

import dev.morganwalsh.meditor.interpreter.fox.token.Token;

public class Environment {
	
	final Environment enclosing;
	private final Map<String, Object> variables = new HashMap<>();
	
	public Environment() {
		this(null);
	}

	public Environment(Environment enclosing) {
		this.enclosing = enclosing;
	}

	public void define(String name, Object value) {
		variables.put(name, value);
	}
	
	public void assign(Token name, Object value) {
		// this environment
		if (variables.containsKey(name.lexeme)) {
			variables.put(name.lexeme, value);
			return;
		}
		
		// enclosing environment
		if (enclosing != null) {
			enclosing.assign(name, value);
			return;
		}
		
		throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
	}
	
	public void assignAt(Integer hops, Token name, Object value) {
		ancestor(hops).variables.put(name.lexeme, value);
	}
	
	private Environment ancestor(Integer hops) {
		Environment currentEnvironment = this;
		
		for (int i = 0; i < hops; i++) {
			currentEnvironment = currentEnvironment.enclosing;
		}
		return currentEnvironment;
	}

	public Object getAt(Integer hops, String name) {
		return ancestor(hops).variables.get(name);
	}

	public Object get(Token name) {
		if (variables.containsKey(name.lexeme)) return variables.get(name.lexeme);
		if (enclosing != null) return enclosing.get(name);
		throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
	}

}