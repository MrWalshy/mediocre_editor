package dev.morganwalsh.meditor.editor.command;

import java.util.stream.Stream;

import javax.swing.Timer;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.editor.components.CommandBar;

public class BufferMessage extends MeditorCommand {

	public BufferMessage(UI display) {
		super("bufferMessage", display);
	}

	@Override
	public Object execute(Object[] args) {
		StringBuilder outputBuilder = new StringBuilder();
		for (Object arg : args) outputBuilder.append(arg.toString());
		
		final String output = outputBuilder.toString();
		runTask(() -> {
			CommandBar bar = display.getCommandBar();
			runUITask(() -> {
				bar.setText(output);
				bar.setEnabled(false);
			});
			Timer timer = new Timer(3000, actionEvent -> {
				bar.setText("");
				bar.setEnabled(true);
			});
			timer.setRepeats(false);
			timer.start();
		});
		       
		return output;
	}

	@Override
	public int getArity() {
		return -1;
	}

}
