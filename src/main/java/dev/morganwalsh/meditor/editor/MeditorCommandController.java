package dev.morganwalsh.meditor.editor;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import dev.morganwalsh.meditor.editor.components.CommandBar;
import dev.morganwalsh.meditor.editor.components.MeditorDisplay;

public class MeditorCommandController {
	private MeditorDisplay display;
	private CommandBar commandBar;
	private MeditorCommandBridge commandBridge;

	public MeditorCommandController(MeditorDisplay display, CommandBar commandBar, MeditorCommandBridge commandBridge) {
		super();
		this.display = display;
		this.commandBar = commandBar;
		this.commandBridge = commandBridge;
		initCommandListener();
	}

	private void initCommandListener() {
		commandBar.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == 10) {
					commandBridge.interpret(commandBar.getText());
					commandBar.setText("");
				}
			}
		});
	}

	public void setOpenCommandBarBinding(KeyStroke keyStroke) {
		display.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "open-command-bar");
		display.getActionMap().put("open-command-bar", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				commandBar.setEnabled(true);
				commandBar.requestFocus();
			}
		});
		commandBar.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				commandBar.setEnabled(false);
			}

			@Override
			public void focusGained(FocusEvent e) {
			}
		});
	}
		
}
