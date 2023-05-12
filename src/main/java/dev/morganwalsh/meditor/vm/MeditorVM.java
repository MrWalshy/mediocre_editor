package dev.morganwalsh.meditor.vm;

import dev.morganwalsh.meditor.editor.UI;

import javax.swing.*;
import java.util.concurrent.BlockingQueue;

/**
 * The MeditorVM is a Runnable machine which by design will
 * run until the program closes. It is intended that by being Runnable,
 * new VM's can be spawned from the main VM.
 */
public class MeditorVM implements Runnable {

    // TODO: Implement VM primitive features for language implementations
    // TODO: Implement VM Meditor built-ins for editing the editor, i.e., opening new windows, closing, moving the caret, etc...

    // If I give the same command queue to the command bar,
    // and send input to the queue when the enter key is pressed,
    // the command bar, display area and vm need know nothing about each other.
    private BlockingQueue<String> commandInputQueue;
    /**
     * The UI acts as the gateway to controlling GUI related
     * functionality.
     */
    private UI ui;
    // When the VM is called to interpret some instructions, if
    // a compiler is set - use that to transform the high-level
    // code into VM instructions, then run the VM instructions
    private LanguageCompiler compiler;
    private boolean isRunning;

    public MeditorVM(UI ui, BlockingQueue<String> commpandInputQueue) {
        this.ui = ui;
        this.commandInputQueue = commpandInputQueue;
    }

    public MeditorVM(UI ui, LanguageCompiler compiler, BlockingQueue<String> commpandInputQueue) {
        this.ui = ui;
        this.compiler = compiler;
        this.commandInputQueue = commpandInputQueue;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning == true) {
            try {
                // Block until command arrives
                String command = commandInputQueue.take();
                System.out.println("RECEIVED COMMAND: " + command);
                if (compiler != null) {
                    String[] instructions = compiler.compile(command);
                    execute(instructions);
                } else {
                    execute(command);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Will need to catch possible compilation exceptions
            // and VM instruction execution exceptions
        }
    }

    public void execute(String[] instructions) {

    }

    public void execute(String instruction) {

    }

    private void invokeUI(Runnable uiTask) {
        SwingUtilities.invokeLater(uiTask);
    }
}
