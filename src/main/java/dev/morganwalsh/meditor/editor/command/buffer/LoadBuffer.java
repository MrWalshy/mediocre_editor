package dev.morganwalsh.meditor.editor.command.buffer;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.editor.command.MeditorCommand;
import dev.morganwalsh.meditor.editor.command.MeditorCommandException;
import dev.morganwalsh.meditor.editor.util.BufferPool;

public class LoadBuffer extends MeditorCommand {

	public LoadBuffer(UI display) {
		super("loadBuffer", display);
	}

	// 0 args = error
	// 1 args = if string name, load in activeWindow
	// 2 args = error
	// 3 args = name, row, col
	// >3 = error
	@Override
	public Object execute(Object[] args) {
		if (args.length == 0 || args.length == 2 || args.length > 3) {
			throw new MeditorCommandException(
					"\nExpected usage: \n"
					+ "1) loadBuffer name \n"
					+ "- Loads the buffer with the given name in the current active window \n"
					+ "2) loadBuffer name row col \n"
					+ "- Loads the buffer with the given name into a window at the given row and column \n"
			);
		}
		if (args.length == 1) return loadIntoActiveWindow(args[0]);
		else return loadIntoSpecifiedWindow(args[0], args[1], args[2]);
	}

	private Object loadIntoSpecifiedWindow(Object object, Object object2, Object object3) {
		try {
			String bufferName = object.toString();
			int row = Integer.parseInt(object2.toString());
			int col = Integer.parseInt(object3.toString());
			
			display.getDisplay()
				   .loadBuffer(bufferName, row, col);
		} catch (Exception e) {
			throw new MeditorCommandException("Row and column parameters must be integers");
		}
		return true;
	}

	private Object loadIntoActiveWindow(Object object) {
		display.getDisplay()
			   .getActiveWindow()
			   .setActiveBuffer(BufferPool.getBuffer(object.toString()));
			
		return true;
	}

	@Override
	public int getArity() {
		return -1;
	}

}
