package dev.morganwalsh.meditor.editor.editor_kits;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.GapContent;
import javax.swing.text.StyleContext;

public class MarkdownDocument extends DefaultStyledDocument {

	public MarkdownDocument() {
		super(new GapContent(BUFFER_SIZE_DEFAULT), new StyleContext());
	}

	public MarkdownDocument(Content c, StyleContext styles) {
		super(c, styles);
	}

	public MarkdownDocument(StyleContext styles) {
		super(new GapContent(BUFFER_SIZE_DEFAULT), styles);
	}

	
}
