package dev.morganwalsh.meditor.editor.command.buffer;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.editor.command.MeditorCommand;
import dev.morganwalsh.meditor.editor.command.MeditorCommandException;
import dev.morganwalsh.meditor.editor.util.BufferPool;

public class CloseBuffer extends MeditorCommand {

	public CloseBuffer(UI display) {
		super("closeBuffer", display);
	}

	@Override
	public Object execute(Object[] args) {
		if (args.length != getArity()) throw new MeditorCommandException(
				"Expected 1 argument but actually got '" + args.length + "'\n"
				+ "Expected usage: closeBuffer name"
		);
//		if (BufferPool.deleteBuffer(args[0].toString())) {
//			return true;
//		}
//		return false;
		return display.getDisplay().closeBuffer(args[0].toString());
	}

	@Override
	public int getArity() {
		return 1;
	}

}
