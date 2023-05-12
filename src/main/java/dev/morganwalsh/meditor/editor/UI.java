package dev.morganwalsh.meditor.editor;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLEditorKit;

import com.formdev.flatlaf.FlatDarculaLaf;

import dev.morganwalsh.meditor.editor.command.BufferMessage;
import dev.morganwalsh.meditor.editor.command.buffer.CloseBuffer;
import dev.morganwalsh.meditor.editor.command.buffer.LoadBuffer;
import dev.morganwalsh.meditor.editor.command.caret.GetCaretPosition;
import dev.morganwalsh.meditor.editor.components.CommandBar;
import dev.morganwalsh.meditor.editor.components.MeditorDisplay;
import dev.morganwalsh.meditor.interpreter.fox.FoxEditorCommandInterpreter;
import dev.morganwalsh.meditor.interpreter.meditor_basic.MeditorBasicCommandInterpreter;

public class UI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7655011263910918711L;
	private CommandBar commandBar;
	private MeditorDisplay display;
	private MeditorCommandController commandController;
	private MeditorCommandBridge commandInterpreter;

	public UI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Meditor");
		setSize(800, 600);
		setLocationRelativeTo(null); // open in middle of screen

		try {
			UIManager.setLookAndFeel(new FlatDarculaLaf());
		} catch (Exception e) {
			System.out.println("Failed to initialise FlatLaf");
		}
		initComponents();
//		initResizeable();
		
		setVisible(true);
	}

	// remove this once done
	// - buggy
	private void initResizeable() {
		JTextArea area1 = new JTextArea();
		JTextArea area2 = new JTextArea();
		GridBagResizePane pane = new GridBagResizePane();
		
		// y acts like a row, x acts like a column
		pane.add(area1, 0, 0, 1, 1);
		pane.add(area2, 1, 0, 1, 1);
		getContentPane().add(pane);
	}

	private void initComponents() {
		display = new MeditorDisplay();
		commandBar = new CommandBar();
//		commandInterpreter = new FoxEditorCommandInterpreter();
//		commandInterpreter = new MeditorBasicCommandInterpreter();
		commandInterpreter = EditorOptions.COMMAND_INTERPRETER.get();
		commandController = new MeditorCommandController(display, commandBar, commandInterpreter);
		
		initEditorCommands();
		
		commandController.setOpenCommandBarBinding(
				KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

		add(display, BorderLayout.CENTER);
		add(commandBar, BorderLayout.SOUTH);
	}

	private void initEditorCommands() {
		commandInterpreter.addCommand(new BufferMessage(this));
		commandInterpreter.addCommand(new GetCaretPosition(this));
		commandInterpreter.addCommand(new LoadBuffer(this));
		commandInterpreter.addCommand(new CloseBuffer(this));
	}

	public CommandBar getCommandBar() {
		return commandBar;
	}

	public void setCommandBar(CommandBar commandBar) {
		this.commandBar = commandBar;
	}

	public MeditorDisplay getDisplay() {
		return display;
	}

	public void setDisplay(MeditorDisplay display) {
		this.display = display;
	}

	public MeditorCommandController getCommandController() {
		return commandController;
	}

	public void setCommandController(MeditorCommandController commandController) {
		this.commandController = commandController;
	}

	public MeditorCommandBridge getCommandInterpreter() {
		return commandInterpreter;
	}

	public void setCommandInterpreter(MeditorCommandBridge commandInterpreter) {
		this.commandInterpreter = commandInterpreter;
		initEditorCommands(); // reset the commands in case the new command bridge isn't aware of the built-ins
	}

}



//class Grid extends JPanel {
//	private List<JSplitPane> rowPanes;
//	
//	private List<GridRow> rows;
//	private JSplitPane rootRow;
//
//	public Grid() {
//		this.rows = new ArrayList<>(1);
//		rowPanes = new ArrayList<>(1);
//		rootRow = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//		
//		GridRow initialRow = new GridRow();
//		rootRow.setTopComponent(initialRow);
//	
//		rows.add(initialRow);
//		rowPanes.add(rootRow);
//		
//		setLayout(new BorderLayout());
//		add(rootRow, BorderLayout.CENTER);
//	}
//
//	public void setGridComponent(int row, int col, JComponent component) {
//		if (row < rows.size()) {
//			// row exists to add to
//			rows.get(row).setColumn(col, component);
//			rowPanes.get(row).setDividerSize(8);
//		} else {
//			// row doesn't exist and needs creating
//			for (int i = rows.size(); i <= row; i++) {
//				JSplitPane newRowPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
//				GridRow newRow = new GridRow();
//				rows.add(newRow);
//				rowPanes.add(newRowPane);
//				
//				// link previous row to new row
////				rows.get(i - 1).set
//				rowPanes.get(i - 1).setBottomComponent(newRowPane);
//				
//				if (i == row) {
//					// at the row, insert the content into the column
//					newRowPane.setTopComponent(newRow);
//					newRowPane.setBottomComponent(null);
//					newRow.setColumn(col, component);
//					newRowPane.setDividerSize(8);
//				} else {
//					newRowPane.setTopComponent(null);
//					newRowPane.setDividerSize(0);
//				}
//			}
//		}
//	}
//}
//
//// for my editor, I'll need a new version of this
//// - create a MeditorFrame class which extends JTextPanel to represent
////   the content of a column, each frame has an associated buffer
//// - create a MeditorWindow extends JSplitPane class
////   which will be stored in the GridRow.columns list, 
////   this will allow retrieving a specific MeditorFrame instead
////   of a Component if I override the getLeft and getRightComponent
////   methods
//// - my GridRow impl will be called MeditorGridRow
//class GridRow extends JPanel {
//	private List<JSplitPane> columns;
//	private JSplitPane rowRoot;
//
//	// always starts with one column, even if there is no
//	// content
//	public GridRow() {
//		super();
//		this.columns = new ArrayList<>(1);
//		rowRoot = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
//		columns.add(rowRoot);
//
//		setLayout(new BorderLayout());
//		add(rowRoot, BorderLayout.CENTER);
//	}
//
//	public JComponent getColumn(int index) {
//		return (JComponent) columns.get(index).getLeftComponent();
//	}
//
//	public void setColumn(int index, JComponent component) {
//		if (index < columns.size()) {
//			columns.get(index).setLeftComponent(component);
//			columns.get(index).setDividerSize(8);
//		} else {
//			// no such column exists, create columns until it does
//			for (int i = columns.size(); i <= index; i++) {
//				JSplitPane newColumn = new JSplitPane();
//				columns.add(newColumn);
//				// link previous column to new column
//				columns.get(i - 1).setRightComponent(newColumn);
//
//				if (i == index) {
//					newColumn.setLeftComponent(component);
//					newColumn.setRightComponent(null);
//				} else {
//					newColumn.setLeftComponent(null);
//					newColumn.setDividerSize(0);
//				}
//			}
//		}
//	}
//
//	public int getColumnCount() {
//		return columns.size();
//	}
//
//	public List<JSplitPane> getColumns() {
//		return columns;
//	}
//
//	public void setColumns(List<JSplitPane> columns) {
//		this.columns = columns;
//	}
//}
