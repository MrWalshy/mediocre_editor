package dev.morganwalsh.meditor.editor.components;

import dev.morganwalsh.meditor.editor.model.Buffer;

import javax.swing.*;
import java.awt.*;

public class MeditorWindowNew<T extends JComponent> extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 2615384318999246788L;
    private T content;
    private Buffer activeBuffer;
    private int row;
    private int column;

    public MeditorWindowNew(T content) {
        setLayout(new BorderLayout());
        this.content = content;
        add(new JScrollPane(content), BorderLayout.CENTER);
    }

    public Buffer getActiveBuffer() {
        return activeBuffer;
    }

    public void setActiveBuffer(Buffer activeBuffer) {
        this.activeBuffer = activeBuffer;
//        content.setStyledDocument(activeBuffer.getDocument());
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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

}
