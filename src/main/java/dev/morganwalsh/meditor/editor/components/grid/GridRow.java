package dev.morganwalsh.meditor.editor.components.grid;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import dev.morganwalsh.meditor.editor.model.Buffer;

public class GridRow<T extends JComponent> extends JPanel {
	
	// When a buffer is closed using bufferClose, its 
	// SplitPane remains behind, which means existing windows with 
	// open buffers do not resize automatically to take up remaining space
	// - think I need to remove a column when its columnData is null and then 
	//   connect the splitPanel before the null data to the one after null (if any)
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6962567062951929116L;
	protected List<JSplitPane> columns;
	protected List<T> columnData;
	protected JSplitPane rootColumn;
	protected int dividerSize;
	
	public GridRow() {
		dividerSize = 4;
		columns = new ArrayList<>();
		columnData = new ArrayList<>();
//		rootColumn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//		rootColumn.setDividerSize(0);
//		rootColumn.setResizeWeight(0.5);
//		
//		columns.add(rootColumn);
//		columnData.add(null);
		
		setLayout(new BorderLayout());
//		add(rootColumn, BorderLayout.CENTER);
	}
	
	public T getColumn(int index) {
		return columnData.get(index);
	}
	
	public void revalidateDividers() {
		for (int i = 0; i < columns.size(); i++) {
			JSplitPane column = columns.get(i);
			if (column.getRightComponent() != null) {
				// only set divider size if the next split pane
				// has a left component
				column.setDividerSize(dividerSize);
			} else column.setDividerSize(0);
		}
	}

	// Things to check:
	//   IF INDEX = 0:
	//     REMOVE CURRENT ROOT COLUMN
	//     IF THERE IS A NEXT COLUMN:
	//       SET THAT AS ROOT AND ADD TO THIS PANEL
	//     IF THERE IS NO NEXT COLUMN:
	//       SET THIS PANEL INVISIBLE <- this could be a good signifier for empty rows
	//   ELSE IF INDEX > 0 AND LESS THAN AMOUNT OF COLUMNS:
	//     THERE IS ALWAYS A PREVIOUS_COLUMN
	//     REMOVE COLUMN AT INDEX
	//     IF COLUMN AT INDEX + 1:
	//        SET PREVIOUS_COLUMN->RIGHT TO COLUMN AT INDEX + 1
	//     ELSE:
	//        SET PREVIOUS_COLUMN->RIGHT TO NULL
	public void removeColumn(int index) {	
		if (index >= columns.size()) throw new IllegalArgumentException("Index " + index + " out of bounds for row of size " + columns.size());
		if (index < 0) throw new IllegalArgumentException("Index " + index + " out of bounds for row of size " + columns.size());

		if (index == 0) {
			super.remove(rootColumn);
			rootColumn = null;

			if (index + 1 < columns.size()) {
				rootColumn = columns.get(index + 1);
				add(rootColumn);
			} else setVisible(false);
		} else {
			JSplitPane previousColumn = columns.get(index - 1);
			if (index + 1 < columns.size()) previousColumn.setRightComponent(columns.get(index + 1));
			else previousColumn.setRightComponent(null);
		}

		// remove column data and revalidate
		columns.remove(index);
		columnData.remove(index);
		revalidate();
		repaint();
		revalidateDividers();
		// THIS WAS BUGGY, THINK I WAS MISSING SOMETHING SO I REDID IT ABOVE
//		JSplitPane previousColumn = null;
//		JSplitPane nextColumn = null;
//		// check for prior column, no column means index is 0
//		if (index - 1 >= 0) previousColumn = columns.get(index - 1);
//		// check for next column, no column means at end of columns
//		if (index + 1 < columns.size()) nextColumn = columns.get(index + 1);
//		// if there is a previous column, set its right component to the component after the one being removed or null
//		if (previousColumn != null) previousColumn.setRightComponent(nextColumn);
//		// if there isn't any previous columns or next columns, index is 0 and rootColumn is the only column left
//		if (previousColumn == null && nextColumn == null) {
//			remove(rootColumn);
//			revalidate();
//			rootColumn = null;
//		}
//		// at index 0, but there is columns to replace this one
//		if (previousColumn == null && nextColumn != null) {
//			rootColumn = nextColumn;
//			add(rootColumn);
//			revalidate();
//		}
		// remove column split pane

	}

	// NEW LOGIC
	// - If an index specified is greater than the 
	//   number of columns, it just does an append column
	//   operation internally
	public int setColumn(int index, T component) {
		if (index < columns.size()) {
			verifyIntegrity();
			columnData.set(index, component);
			JSplitPane pane = columns.get(index);
			pane.setLeftComponent(component);
			revalidateDividers();
			
			return index;
		}
		
		// specified parameter index was too large
		// - append operation instead
		return append(component);
	}
	
	/**
	 * Appends a new column to the grid row.
	 * @param component
	 * @return the index of the appended component in the row
	 */
	public int append(T component) {
		verifyIntegrity();
		int index = columns.size();
		
		JSplitPane newColumn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		newColumn.setLeftComponent(component);
		newColumn.setRightComponent(null);
		newColumn.setResizeWeight(0.5);
		
		columns.add(newColumn);
		columnData.add(component);
		
		if (index == 0 && rootColumn == null) {
			rootColumn = newColumn;
			add(rootColumn);
		}
		if (index - 1 >= 0) {
			JSplitPane parentColumn = columns.get(index - 1);
			parentColumn.setRightComponent(newColumn);
			parentColumn.setResizeWeight(0.5);
		}
		
		revalidateDividers();
		
		return index;
	}
	
	private void verifyIntegrity() {
		if (columnData.size() != columns.size()) 
			throw new RuntimeException("Column data and pane alignment integrity loss");
	}
	
	// OLD LOGIC
//	public void setColumn(int index, T component) {
//		if (index < columns.size()) {
//			JSplitPane pane = columns.get(index);
//			pane.setLeftComponent(component);
//			columnData.set(index, component);
//			revalidateDividers();
//			
//			return;
//		}
//		
//		// no column exists with given index
//		// - create columns until it does
//		for (int i = columns.size(); i <= index; i++) {
//			JSplitPane newColumn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
////			newColumn.setResizeWeight(0.5);
//			columns.add(newColumn);
//			
//			// link previous column to new column
//			columns.get(i - 1).setRightComponent(newColumn);
//			
//			if (i == index) {
//				newColumn.setLeftComponent(component);
//				newColumn.setRightComponent(null);
//				columnData.add(component);
//			} else {
//				newColumn.setLeftComponent(null);
//				newColumn.setDividerSize(0);
//				columnData.add(null);
//			}
//		}
//		revalidateDividers();
//	}
	
	public int getColumnCount() {
		return columns.size();
	}

	public List<JSplitPane> getColumns() {
		return columns;
	}
	
	public List<T> getColumnData() {
		return columnData;
	}
	
	public void setDividerSize(int dividerSize) {
		this.dividerSize = dividerSize;
		revalidateDividers();
	}
}
