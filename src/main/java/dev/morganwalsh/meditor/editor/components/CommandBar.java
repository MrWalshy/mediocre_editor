package dev.morganwalsh.meditor.editor.components;

import java.awt.Color;

import javax.swing.JTextField;

public class CommandBar extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8459893007346120028L;

	public CommandBar() {
		super();
		setEnabled(false);
		setDisabledTextColor(Color.RED);
	}
	
}
