package dev.morganwalsh.meditor.interpreter.fox;

import java.util.List;

import dev.morganwalsh.meditor.editor.MeditorCommandBridge;

public class FoxEditorCommandInterpreter extends MeditorCommandBridge implements Interpreter {
	
	@Override
	public Object interpret(String input) {
		System.out.println(input);
		// just testing, will be removed
		if (input.startsWith("bufferMessage ")) {
			String[] command = input.split("bufferMessage ");
			System.out.println(command[1]);
			editorCommands.get("bufferMessage").execute(new Object[]{ command[1]});
		}
		return null;
	}

	@Override
	public Object interpret(List<Expression> expressions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object interpret(Expression expression) {
		// TODO Auto-generated method stub
		return null;
	}

}
