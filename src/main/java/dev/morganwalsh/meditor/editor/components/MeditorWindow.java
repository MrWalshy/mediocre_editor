package dev.morganwalsh.meditor.editor.components;

import dev.morganwalsh.meditor.editor.command.MeditorCommand;
import dev.morganwalsh.meditor.editor.model.Buffer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MeditorWindow<T extends JComponent> extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 2615384318999246788L;
    private T content;
    private Buffer activeBuffer;
    private int row;
    private int column;
    /**
     * Holds a map of commands which can be executed against
     * this window. The key is the name of the command, represented
     * as a String. The value is a MeditorCommand which can be called.
     */
    private final HashMap<String, MeditorCommand<T>> meditorCommandMap;

    public MeditorWindow(T content) {
        setLayout(new BorderLayout());
        this.content = content;
        add(new JScrollPane(content), BorderLayout.CENTER);
        meditorCommandMap = new HashMap<>();
    }

    /**
     * Retrieves a MeditorCommand<T> or null if it does not exist.
     * @param name
     * @return
     */
    public MeditorCommand<T> getCommand(String name) {
        return meditorCommandMap.get(name);
    }

    /**
     * Adds the new MeditorCommand to this window. Returns this window
     * to allow chained calls.
     *
     * @param name
     * @param command
     * @return
     */
    public MeditorWindow<T> addCommand(String name, MeditorCommand<T> command) {
        // TODO: Illegal arg handling
        meditorCommandMap.put(name, command);
        return this;
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
