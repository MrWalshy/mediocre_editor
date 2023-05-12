package dev.morganwalsh.meditor.editor.components.grid;

import javax.swing.JComponent;
import javax.swing.JSplitPane;

public class BasicGrid<T extends JComponent> extends AbstractGrid<T> {

//	@Override
//	public void setGridComponent(int row, int col, T component) {
//		if (row < rows.size()) {
//			// row already exists to set
////			rows.get(row).setDividerSize(8);
//			rowData.get(row).setColumn(col, component);
//			return;
//		}
//		// row doesn't exist, create rows until it does
//		for (int i = rows.size(); i <= row; i++) {
//			JSplitPane newRow = new JSplitPane();
//			GridRow<T> newRowData = new GridRow<T>();
//			
//			rows.add(newRow);
//			rowData.add(newRowData);
//			
//			// link previous row to new row
//			rows.get(i - 1).setBottomComponent(newRow);
//			
//			if (i == row) {
//				// at specified row, insert content
//				newRow.setTopComponent(newRowData);
//				newRow.setBottomComponent(null);
//				newRow.setDividerSize(8);
//				
//				newRowData.setColumn(col, component);
//			} else {
//				newRow.setTopComponent(null);
//				newRow.setDividerSize(0);
//			}
//		}
//		revalidateDividers();
//	}

}
