package dev.morganwalsh.meditor.editor.command;

import javax.swing.*;

import dev.morganwalsh.meditor.editor.UI;

public abstract class MeditorCommand<T extends JComponent> {
	
	protected String name;
	protected UI display;
	
	/*
	 * EditorCommand instances have access to the display, so 
	 * they can manipulate it in some way.
	 */
	
	public MeditorCommand(String name, UI display) {
		this.name = name;
		this.display = display;
	}

	/**
	 * Executes the command with the given arguments.
	 * @param args
	 * @return
	 */
	public abstract Object execute(Object[] args);
	
	protected void runTask(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.run();
	}
	
	protected void runUITask(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}
	
	/**
	 * The number of bound parameters.
	 * @return
	 */
	public abstract int getArity();
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
