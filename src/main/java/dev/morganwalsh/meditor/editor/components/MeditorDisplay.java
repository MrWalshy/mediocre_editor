package dev.morganwalsh.meditor.editor.components;

import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.components.grid.GridRow;
import dev.morganwalsh.meditor.editor.model.Buffer;
import dev.morganwalsh.meditor.editor.util.BufferPool;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class MeditorDisplay extends AbstractGrid<MeditorWindow<?>> {

    /**
     *
     */
    private static final long serialVersionUID = -1999779788247320853L;
    private MeditorWindow<?> activeWindow;

    public MeditorDisplay() {
        createWindow("root", 0, 0);
    }

    /**
     * Creates a new MeditorWindow backed by a buffer with the given name
     * and content, positioned at the indicated row and column.
     * @param name
     * @param content
     * @param row
     * @param col
     * @param <T>
     * @return the new MeditorWindow
     */
    public <T extends JComponent> MeditorWindow<T> createWindow(String name, T content, int row, int col) {
        MeditorWindow<T> window = new MeditorWindow<T>(content);
        Buffer existingBuffer = BufferPool.getBuffer(name, false);
        if (existingBuffer != null) throw new RuntimeException("Buffer already exists with name " + name);

        Buffer newBuffer = BufferPool.getBuffer(name);
        window.setActiveBuffer(newBuffer);
        activeWindow = window;

        window.setRow(row);
        window.setColumn(col);

        setGridComponent(row, col, window);

        window.getContent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                activeWindow = window;
            }
        });

        return window;
    }

    /**
     * Creates a new text window backed by a text buffer.
     * @param name
     * @param row
     * @param col
     * @return
     */
    public MeditorWindow<JTextPane> createWindow(String name, int row, int col) {
        JTextPane textPane = new JTextPane();
        Buffer textBuffer = BufferPool.getBuffer(name);
        textPane.setStyledDocument(textBuffer.getDocument());
        MeditorWindow<JTextPane> window = new MeditorWindow<JTextPane>(textPane);
        activeWindow = window;

        window.setActiveBuffer(textBuffer);
        window.setRow(row);
        window.setColumn(col);

        setGridComponent(row, col, window);

        window.getContent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                activeWindow = window;
            }
        });

        return window;
    }

    /**
     * Closes the window at the given row and column without closing the underlying buffer.
     * @param row
     * @param col
     */
    public void closeWindow(int row, int col) {

    }

    // TODO: Do I need this?
    public <T extends JComponent> void loadBuffer(String name, T content, int row, int col) {
        MeditorWindow<T> window = new MeditorWindow<>(content);
        Buffer buffer = BufferPool.getBuffer(name);
        activeWindow = window;

        window.setActiveBuffer(buffer);
        window.setRow(row);
        window.setColumn(col);

        setGridComponent(row, col, window);

        window.getContent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                activeWindow = window;
            }
        });
    }

    // TODO: Do I need this?
    public void loadBuffer(String name, int row, int col) {
        MeditorWindow<?> window = new MeditorWindow<>(null);
        Buffer buffer = BufferPool.getBuffer(name);
        activeWindow = window;

        window.setActiveBuffer(buffer);
        window.setRow(row);
        window.setColumn(col);

        setGridComponent(row, col, window);

        window.getContent().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                activeWindow = window;
            }
        });
    }

    /**
     * Closes the buffer with the given name or throws a RuntimeException
     * if it does not exist. Note, all columns backed by the buffer will close.
     * If a row has no columns left, that row will also be closed.
     * @param name
     * @return
     */
    public boolean closeBuffer(String name) {
        Buffer buffer = BufferPool.getBuffer(name);
        if (buffer == null) throw new RuntimeException("Buffer with name '" + name + "' does not exist");

        // need to iterate over the rows
        // - over each column
        //   - if the column contains the given buffer:
        //     - set its content to null
        for (GridRow<MeditorWindow<?>> row : rowData) {
            List<MeditorWindow<?>> columns = row.getColumnData();
            for (int i = 0; i < columns.size(); i++) {
                MeditorWindow<?> column = columns.get(i);
                if (column.getActiveBuffer() == buffer) {
                    row.removeColumn(i);
                    i--; // move i back one otherwise we will check the wrong column next
                }
            }
            row.revalidateDividers();
        }
        removeEmptyRows();
        return BufferPool.deleteBuffer(name);
    }

    /**
     * Returns the currently active MeditorWindow.
     *
     * @return
     */
    public MeditorWindow<?> getActiveWindow() {
        return activeWindow;
    }

}
