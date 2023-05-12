package dev.morganwalsh.meditor.editor.components.grid;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Provides the basis for a Grid based component. The grid is two dimensional, built of 
 * rows and columns. The rows and columns can be resized. A column in this grid can not span 
 * multiple rows but can work with any JComponent.
 * @author morga
 *
 * @param <T>
 */
public abstract class AbstractGrid<T extends JComponent> extends JPanel implements Grid<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 489960829481308730L;
	protected List<JSplitPane> rows;
	protected List<GridRow<T>> rowData;
	protected JSplitPane rootRow;
	protected int dividerSize;

	public AbstractGrid() {
		dividerSize = 4;
		rows = new ArrayList<>();
		rowData = new ArrayList<>();
		setLayout(new BorderLayout());
	}
	
	protected void revalidateDividers() {
		rows.forEach(row -> {
			if (row.getBottomComponent() != null) row.setDividerSize(dividerSize);
			else row.setDividerSize(0);
		});
	}
	
	/**
	 * Sets a new component of type T on the grid.
	 */
	@Override
	public void setGridComponent(int row, int col, T component) {
		if (row < rows.size()) { // row already exists to set
			rowData.get(row).setColumn(col, component);
			revalidateDividers();
			return;
		}
		// row doesn't exist, append a new one - col no longer matters
		append(component);
	}
	
	public void append(T component) {
		verifyIntegrity();
		
		int index = rows.size();
		
		// Create new row panes and a grid row object for storing components
		JSplitPane newRow = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		GridRow<T> newRowData = new GridRow<>();
		newRowData.append(component); // add the component to the row data
		newRow.setResizeWeight(0.5);
		newRow.setTopComponent(newRowData); // add the row data to the new split pane row
		
		rows.add(newRow);
		rowData.add(newRowData);
		
		if (index == 0 && rootRow == null) {
			rootRow = newRow;
			add(rootRow);
		}
		if (index - 1 >= 0) {
			JSplitPane parentRow = rows.get(index - 1);
			parentRow.setBottomComponent(newRow);
			parentRow.setResizeWeight(0.5);
		}

		revalidate();
		repaint();
		revalidateDividers();
	}

	protected void verifyIntegrity() {
		if (rows.size() != rowData.size()) 
			throw new RuntimeException("Row data and pane alignment integrity loss");
	}
	
	public void setDividerSize(int dividerSize) {
		this.dividerSize = dividerSize;
		revalidateDividers();
	}

	public void removeEmptyRows() {
		for (int index = 0; index < rowData.size(); index++) {
			GridRow<?> row = rowData.get(index);
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
				GridRow<?> previousRow = rowData.get(index - 1);
				JSplitPane previousRowPane = rows.get(index - 1);
				if (index + 1 < rowData.size()) {
					GridRow<?> nextRow = rowData.get(index + 1);
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
	}
}