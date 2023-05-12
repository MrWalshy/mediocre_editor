package dev.morganwalsh.meditor.editor;

import javax.swing.text.Caret;

import dev.morganwalsh.meditor.editor.model.EditorCaret;

public interface TextAreaCommandBindings {

	void moveCaretPosition(int pos);
	void setCaretPosition(int pos);
	
	int getCaretPosition();
	int getSelectionStart();
	int getSelectionEnd();
	
	String getSelectedText();
	EditorCaret getEditorCaret();
	Caret getCaret();
	
	void setLineWrap(boolean wrap);
	boolean getLineWrap();
	void setWrapStyleWord(boolean wrapOnWord);
	boolean getWrapStyleWord();
	
	void setText(String text);
	String getText();
	
	void setScroll(boolean isScrollable);
}
