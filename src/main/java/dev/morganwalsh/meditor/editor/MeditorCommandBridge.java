package dev.morganwalsh.meditor.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.morganwalsh.meditor.editor.command.MeditorCommand;

/**
 * Acts as a bridge between an external interpreter 
 * and the editor. The MeditorCommand abstracts the 
 * communication so that the interpreter inheriting 
 * from this bridge does not need to know anything 
 * about the editor itself.
 * @author morga
 *
 */
public abstract class MeditorCommandBridge {

	protected Map<String, MeditorCommand> editorCommands;
	
	public MeditorCommandBridge() {
		this.editorCommands = new HashMap<>();
	}
	
	public MeditorCommandBridge(List<MeditorCommand> editorCommands) {
		this();
		for (MeditorCommand command : editorCommands) {
			this.editorCommands.put(command.getName(), command);
		}
	}
	
	public abstract Object interpret(String input);
	
	public void addCommand(MeditorCommand command) {
		editorCommands.put(command.getName(), command);
	}
	
	public void removeCommand(String command) {
		editorCommands.remove(command);
	}
}
