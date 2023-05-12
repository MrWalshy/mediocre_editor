package dev.morganwalsh.meditor.interpreter.meditor_basic;

import java.util.ArrayList;
import java.util.List;

import dev.morganwalsh.meditor.editor.MeditorCommandBridge;
import dev.morganwalsh.meditor.editor.command.MeditorCommand;
import dev.morganwalsh.meditor.editor.command.MeditorCommandException;

public class MeditorBasicCommandInterpreter extends MeditorCommandBridge {

	@Override
	public Object interpret(String input) {
		if (input == null || input.isBlank()) throw new MeditorCommandException("Input was null, empty or only whitespace");
		
		List<String> parts = new ArrayList<>(List.of(input.split(" ")));
		String command = parts.get(0);
		parts.remove(0);
		MeditorCommand meditorCommand = editorCommands.get(command);
		
		if (meditorCommand == null) {
			throw new MeditorCommandException("Command does not exist");
		}
		if (meditorCommand.getArity() != parts.size()) {
			// -1 indicates variable amount of arguments
			if (meditorCommand.getArity() >= 0) {
				throw new MeditorCommandException("Bad arity, expected " + meditorCommand.getArity()  + " arguments but actually got " + parts.size());
			}
		}
		return meditorCommand.execute(parts.toArray());
	}

}
