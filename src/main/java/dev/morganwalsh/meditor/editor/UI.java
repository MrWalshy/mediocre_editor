package dev.morganwalsh.meditor.editor;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.*;

import com.formdev.flatlaf.FlatDarculaLaf;

import dev.morganwalsh.meditor.editor.command.BufferMessage;
import dev.morganwalsh.meditor.editor.command.buffer.CloseBuffer;
import dev.morganwalsh.meditor.editor.command.buffer.LoadBuffer;
import dev.morganwalsh.meditor.editor.command.caret.GetCaretPosition;
import dev.morganwalsh.meditor.editor.components.CommandBar;
import dev.morganwalsh.meditor.editor.components.MeditorDisplay;

public class UI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7655011263910918711L;
	private CommandBar commandBar;
	private MeditorDisplay display;
	private BlockingQueue<String> commandQueue;

	public UI(BlockingQueue<String> commandQueue) {
		this.commandQueue = commandQueue;
	}

	public void initialise() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Meditor");
		setSize(800, 600);
		setLocationRelativeTo(null); // open in middle of screen

		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (Exception e) {
			System.out.println("Failed to initialise FlatLaf");
		}
		initComponents();

		setVisible(true);
	}

	private void initComponents() {
		display = new MeditorDisplay();
		commandBar = new CommandBar();

		commandBar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (e.getKeyCode() == 10) {
					try {
						commandQueue.put(commandBar.getText());
						System.out.println("Command sent from thread: " + Thread.currentThread().getName());
						commandBar.setText("");
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		// CTRL SHIFT M to open command bar by default
		setOpenCommandBarBinding(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

		add(display, BorderLayout.CENTER);
		add(commandBar, BorderLayout.SOUTH);
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

	public CommandBar getCommandBar() {
		return commandBar;
	}

	public void setCommandBar(CommandBar commandBar) {
		this.commandBar = commandBar;
	}

	public MeditorDisplay getDisplay() {
		return display;
	}

	public void setDisplay(MeditorDisplay display) {
		this.display = display;
	}

}
