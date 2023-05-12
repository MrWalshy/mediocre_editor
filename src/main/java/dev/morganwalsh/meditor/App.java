package dev.morganwalsh.meditor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.*;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.components.grid.GridRow;
import dev.morganwalsh.meditor.vm.MeditorVM;

public class App {

	public static void main(String[] args) throws IOException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();
		UI ui = new UI(queue);
		MeditorVM vm = new MeditorVM(ui, queue);
		SwingUtilities.invokeLater(() -> ui.initialise());
		// VM is running on its own thread, commands hit the queue
		// and then executed by the VM
		new Thread(vm).start();
	}

}