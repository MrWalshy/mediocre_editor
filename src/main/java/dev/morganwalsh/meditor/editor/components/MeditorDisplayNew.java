package dev.morganwalsh.meditor.editor.components;

import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.components.grid.GridRow;
import dev.morganwalsh.meditor.editor.model.Buffer;
import dev.morganwalsh.meditor.editor.util.BufferPool;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class MeditorDisplayNew extends AbstractGrid<MeditorWindowNew<?>> {

    /**
     *
     */
    private static final long serialVersionUID = -1999779788247320853L;
    private MeditorWindowNew<?> activeWindow;

    public MeditorDisplayNew() {
        loadBuffer("root", 0, 0);
    }

    public <T extends JComponent> void createWindow(String name, T content, int row, int col) {
        MeditorWindowNew<T> window = new MeditorWindowNew<T>(content);
        Buffer existingBuffer = BufferPool.getBuffer(name, false);
//        if (existingBuffer != null)
    }

    public void closeWindow(int row, int col) {

    }

    public void loadBuffer(String name, int row, int col) {
        MeditorWindowNew<?> window = new MeditorWindowNew<>(null);
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

    // Closing a buffer also closes the column it is in
    public boolean closeBuffer(String name) {
        Buffer buffer = BufferPool.getBuffer(name);
        if (buffer == null) throw new RuntimeException("Buffer with name '" + name + "' does not exist");

        // need to iterate over the rows
        // - over each column
        //   - if the column contains the given buffer:
        //     - set its content to null
        for (GridRow<MeditorWindowNew<?>> row : rowData) {
            List<MeditorWindowNew<?>> columns = row.getColumnData();
            for (int i = 0; i < columns.size(); i++) {
                MeditorWindowNew<?> column = columns.get(i);
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

    // FOR INDEX IN ROWS:
    //   SET ROW TO ROWS[INDEX]
    //   IF ROW IS NOT EMPTY:
    //      CONTINUE TO NEXT ITERATION
    //   IF ROW INDEX = 0:
    //      REMOVE ROOT ROW
    //      IF THERE IS A NEXT ROW:
    //        SET ROOT ROW TO NEXT ROW AND ADD TO THIS PANEL
    //        SET INDEX TO INDEX - 1
    //        CONTINUE TO NEXT LOOP ITERATION
    //      ELSE:
    //	      SET PANEL INVISIBLE (maybe)
    //        BREAK OUT OF LOOP
    //   ELSE: <- INDEX IS GREATER THAN 0
    //      PREVIOUS_ROW = ROWS[INDEX - 1]
    //      IF THERE IS A NEXT ROW:
    //         SET PREVIOUS_ROW->BOTTOM TO THE NEXT ROW AT INDEX + 1
    //         SET INDEX TO INDEX - 1
    //      ELSE:
    //         SET PREVIOUS_ROW-> BOTTOM TO NULL
//    private void removeEmptyRows() {
//        for (int index = 0; index < rowData.size(); index++) {
//            GridRow<MeditorWindowNew<?>> row = rowData.get(index);
//            if (row.getColumnCount() != 0) continue;
//
//            if (index == 0) {
//                super.remove(rootRow);
//                rootRow = null;
//                if (index + 1 < rowData.size()) {
//                    rootRow = rows.get(index + 1);
//                    add(rootRow);
//                    rows.remove(index);
//                    rowData.remove(index);
//                    index--;
//                    continue;
//                } else {
//                    rows.remove(index);
//                    rowData.remove(index);
//                    break;
//                }
//            } else {
//                GridRow<MeditorWindowNew<?>> previousRow = rowData.get(index - 1);
//                JSplitPane previousRowPane = rows.get(index - 1);
//                if (index + 1 < rowData.size()) {
//                    GridRow<MeditorWindowNew<?>> nextRow = rowData.get(index + 1);
//                    JSplitPane nextRowPane = rows.get(index + 1);
//
//                    previousRowPane.setBottomComponent(nextRowPane);
//                    rows.remove(index);
//                    rowData.remove(index);
//                    index--;
//                } else {
//                    previousRowPane.setBottomComponent(null);
//                    rows.remove(index);
//                    rowData.remove(index);
//                }
//            }
//        }

//        revalidate();
//        repaint();
//        revalidateDividers();
//    }

    public MeditorWindowNew<?> getActiveWindow() {
        return activeWindow;
    }

}
