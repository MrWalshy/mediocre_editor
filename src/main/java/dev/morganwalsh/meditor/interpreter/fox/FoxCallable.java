package dev.morganwalsh.meditor.interpreter.fox;

import java.util.List;

public interface FoxCallable {

	Object call(FoxInterpreter interpreter, List<Object> args);
	int arity();
}
