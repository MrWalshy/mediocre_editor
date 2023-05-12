package dev.morganwalsh.meditor.editor.model;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class EditorCaret implements CaretListener {
	
	private int position;
	private int anchor;
	// anchor used for selections, same as pos when no
	// selection being made

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getAnchor() {
		return anchor;
	}

	public void setAnchor(int anchor) {
		this.anchor = anchor;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		position = e.getDot();
		anchor = e.getMark();
	}
	
	
}
