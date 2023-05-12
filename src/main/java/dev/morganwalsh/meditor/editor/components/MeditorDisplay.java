package dev.morganwalsh.meditor.editor.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.JSplitPane;

import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.components.grid.GridRow;
import dev.morganwalsh.meditor.editor.model.Buffer;
import dev.morganwalsh.meditor.editor.util.BufferPool;

public class MeditorDisplay extends AbstractGrid<MeditorWindow> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1999779788247320853L;
	private MeditorWindow activeWindow;
	
	public MeditorDisplay() {
		loadBuffer("root", 0, 0);
	}

	public void loadBuffer(String name, int row, int col) {
		MeditorWindow window = new MeditorWindow();
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
		for (GridRow<MeditorWindow> row : rowData) {
			List<MeditorWindow> columns = row.getColumnData();
			for (int i = 0; i < columns.size(); i++) {
				MeditorWindow column = columns.get(i);
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
	public void removeEmptyRows() {
		for (int index = 0; index < rowData.size(); index++) {
			GridRow<MeditorWindow> row = rowData.get(index);
			if (row.getColumnCount() != 0) continue;

			if (index == 0) {
				super.remove(rootRow);
				rootRow = null;
				if (index + 1 < rowData.size()) {
					rootRow = rows.get(index + 1);
					add(rootRow);
					rows.remove(index);
					rowData.remove(index);
					index--;
					continue;
				} else {
					rows.remove(index);
					rowData.remove(index);
					break;
				}
			} else {
				GridRow<MeditorWindow> previousRow = rowData.get(index - 1);
				JSplitPane previousRowPane = rows.get(index - 1);
				if (index + 1 < rowData.size()) {
					GridRow<MeditorWindow> nextRow = rowData.get(index + 1);
					JSplitPane nextRowPane = rows.get(index + 1);

					previousRowPane.setBottomComponent(nextRowPane);
					rows.remove(index);
					rowData.remove(index);
					index--;
				} else {
					previousRowPane.setBottomComponent(null);
					rows.remove(index);
					rowData.remove(index);
				}
			}
		}


		// iterate over rowData
		//   if a row has 0 columns:
		//     get the previous row if any
		//     get the next row if any
		//     
//		for (int i = 0; i < rowData.size(); i++) {
//			GridRow<MeditorWindow> currentRow = rowData.get(i);
//			if (currentRow.getColumnCount() > 0) continue; // no point in trying to remove as it has data
//
//			GridRow<MeditorWindow> previousRow = null;
//			GridRow<MeditorWindow> nextRow = null;
//
//			// check for prior row, no row means index 0
//			if (i > 0) previousRow = rowData.get(i - 1);
//			// check for next row, no row means at end of rows
//			if (i + 1 < rows.size()) nextRow = rowData.get(i + 1);
//
//			// if there is a previous row and next row, set its bottom component to the one after the current
//			if (previousRow != null && nextRow != null) {
//				rows.get(i - 1).setBottomComponent(rows.get(i + 1));
//			}
//			// if there is not a previous row or next row, i is 0 and this is the only row left
//			if (previousRow == null && nextRow == null) {
//				remove(rootRow);
//				rootRow = null;
//			}
//			// at index 0, but there is a next row
//			if (previousRow == null && nextRow != null) {
//				rootRow = rows.get(i + 1);
//				add(rootRow);
//			}
//			// there is a previous row but not a next row
//			// - remove its bottom component which represents this empty row
//			if (previousRow != null && nextRow == null) {
//				rows.get(i - 1).setBottomComponent(null);
//			}
//
//			rows.remove(i);
//			rowData.remove(i);
//			i--;
//		}
		revalidate();
		repaint();
		revalidateDividers();
	}

	public MeditorWindow getActiveWindow() {
		return activeWindow;
	}
	
}
