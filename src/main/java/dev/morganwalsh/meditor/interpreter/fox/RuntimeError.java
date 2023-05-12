package dev.morganwalsh.meditor.interpreter.fox;

import dev.morganwalsh.meditor.interpreter.fox.token.Token;

public class RuntimeError extends RuntimeException {

	final Token token;

	public RuntimeError(Token token, String message) {
		super(message);
		this.token = token;
	}
}
