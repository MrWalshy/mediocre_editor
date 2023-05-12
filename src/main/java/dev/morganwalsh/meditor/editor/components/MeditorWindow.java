package dev.morganwalsh.meditor.editor.components;

import java.awt.BorderLayout;

import javax.swing.*;

import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.model.Buffer;

public class MeditorWindow extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2615384318999246788L;
	private JTextPane content;
	private Buffer activeBuffer;
	private int row;
	private int column;
	
	public MeditorWindow() {
		setLayout(new BorderLayout());
		content = new JTextPane();
		add(new JScrollPane(content), BorderLayout.CENTER);
	}

	public Buffer getActiveBuffer() {
		return activeBuffer;
	}

	public void setActiveBuffer(Buffer activeBuffer) {
		this.activeBuffer = activeBuffer;
		content.setStyledDocument(activeBuffer.getDocument());
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public JTextPane getContent() {
		return content;
	}

	public void setContent(JTextPane content) {
		this.content = content;
	}

}

class FooWindow<T extends JComponent> extends JComponent {

}

class BarUI extends AbstractGrid<FooWindow<?>> {

}