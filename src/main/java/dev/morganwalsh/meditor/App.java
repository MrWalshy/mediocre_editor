package dev.morganwalsh.meditor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.*;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.editor.components.grid.AbstractGrid;
import dev.morganwalsh.meditor.editor.components.grid.GridRow;
import dev.morganwalsh.meditor.vm.Instruction;
import dev.morganwalsh.meditor.vm.MeditorVM;
import dev.morganwalsh.meditor.vm.OpCode;

public class App {

	public static void main(String[] args) throws IOException {
		BlockingQueue<String> queue = new LinkedBlockingQueue<>();
		UI ui = new UI(queue);
		MeditorVM vm = new MeditorVM(ui, queue, input -> new Instruction[] {
//			new Instruction(OpCode.PUSH_NUMBER, ByteBuffer.allocate(8).putDouble(20).array()),
//			new Instruction(OpCode.PUSH_NUMBER, ByteBuffer.allocate(8).putDouble(20).array()),
//			new Instruction(OpCode.ADD_NUM),
//			new Instruction(OpCode.PRINT)
			// string
//			new Instruction(OpCode.PUSH_STRING, " World".getBytes(StandardCharsets.UTF_8)),
//			new Instruction(OpCode.PUSH_STRING, "Hello ".getBytes(StandardCharsets.UTF_8)),
//			new Instruction(OpCode.CON_STR),
//			new Instruction(OpCode.PRINT)
			// store and load
//			new Instruction(OpCode.PUSH_STRING, "Hello".getBytes(StandardCharsets.UTF_8)),
//			new Instruction(OpCode.STORE_GLOBAL, "greeting".getBytes(StandardCharsets.UTF_8)),
//			new Instruction(OpCode.LOAD_GLOBAL, "greeting".getBytes(StandardCharsets.UTF_8)),
//			new Instruction(OpCode.PRINT)
			// objects, setting and getting properties
			new Instruction(OpCode.PUSH_OBJECT),
			new Instruction(OpCode.PUSH_STRING, "Hello".getBytes(StandardCharsets.UTF_8)),
			new Instruction(OpCode.PUSH_SYMBOL, "greeting".getBytes(StandardCharsets.UTF_8)),
			new Instruction(OpCode.SET_PROPERTY),
			// gonna try putting the object on the global object, then loading it
			// and then printing the property on the object
			new Instruction(OpCode.PUSH_SYMBOL, "greetingObject".getBytes(StandardCharsets.UTF_8)),
			new Instruction(OpCode.STORE_GLOBAL),
			new Instruction(OpCode.PUSH_SYMBOL, "greetingObject".getBytes(StandardCharsets.UTF_8)),
			new Instruction(OpCode.LOAD_GLOBAL),
			new Instruction(OpCode.PUSH_SYMBOL, "greeting".getBytes(StandardCharsets.UTF_8)),
			new Instruction(OpCode.GET_PROPERTY),
			new Instruction(OpCode.PRINT)
			// by only having the PUSH_XXX ops having args, it seems to make implementing
			// easier, but I presume at the sacrifice of performance due to extra opcodes being
			// required to do a task
			// - fewer opcodes need bytes decoding, which I'd think would lower the chance for error
		});
		SwingUtilities.invokeLater(() -> ui.initialise());
		// VM is running on its own thread, commands hit the queue
		// and then executed by the VM
		new Thread(vm).start();
	}

}