package dev.morganwalsh.meditor.editor.command.caret;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.editor.command.MeditorCommand;
import dev.morganwalsh.meditor.editor.command.MeditorCommandException;

public class GetCaretPosition extends MeditorCommand {

	public GetCaretPosition(UI display) {
		super("getCaret", display);
	}

	@Override
	public Object execute(Object[] args) {
		if (args.length != getArity()) throw new MeditorCommandException("'getCaret' expects 0 arguments but got '" + args.length + "'");
		return display.getDisplay()
					  .getActiveWindow()
					  .getContent()
					  .getCaretPosition();
	}

	@Override
	public int getArity() {
		return 0;
	}

}
