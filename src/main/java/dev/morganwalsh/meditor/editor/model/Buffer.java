package dev.morganwalsh.meditor.editor.model;

import java.io.File;

import javax.swing.text.StyledDocument;

public class Buffer {

	private String name;
	private File file;
	private StyledDocument document;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public StyledDocument getDocument() {
		return document;
	}

	public void setDocument(StyledDocument document) {
		this.document = document;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
