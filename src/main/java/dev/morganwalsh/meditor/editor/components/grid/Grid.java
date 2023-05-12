package dev.morganwalsh.meditor.editor.components.grid;

import javax.swing.JComponent;

public interface Grid<T extends JComponent> {

	void setGridComponent(int row, int col, T component);
}
