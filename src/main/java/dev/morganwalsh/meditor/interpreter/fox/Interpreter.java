package dev.morganwalsh.meditor.interpreter.fox;

import java.util.List;

public interface Interpreter {

	Object interpret(String input);

	Object interpret(List<Expression> expressions);

	Object interpret(Expression expression);
}
