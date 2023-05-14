package dev.morganwalsh.meditor.vm;

import dev.morganwalsh.meditor.editor.UI;
import dev.morganwalsh.meditor.vm.data_types.*;

import javax.swing.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.*;
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

    private Stack<MeditorObject> valueStack;

    private MeditorObject globalObject;
    private int programCounter;
//    private

    public MeditorVM(UI ui, BlockingQueue<String> commpandInputQueue) {
        this.ui = ui;
        this.commandInputQueue = commpandInputQueue;
    }

    public MeditorVM(UI ui, BlockingQueue<String> commpandInputQueue, LanguageCompiler compiler) {
        this.ui = ui;
        this.compiler = compiler;
        this.commandInputQueue = commpandInputQueue;
        programCounter = 0;
        valueStack = new Stack<>();
        globalObject = new MeditorObject();
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning == true) {
            try {
                // Block until command arrives
                String command = commandInputQueue.take();
                System.out.println("RECEIVED COMMAND ON THREAD: " + Thread.currentThread().getName());
                // using a high-level language, compile down to bytecode
                // - this high-level language could be human-readable bytecode
                //   or a more general-purpose language
                Instruction[] instructions = compiler.compile(command);
                interpret(instructions);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (EmptyStackException e) {
                e.printStackTrace();
            }
            // Will need to catch possible compilation exceptions
            // and VM instruction execution exceptions
            // - as we don't want a VM to crash, recover to a good state
            //   on error
        }
    }

    public void interpret(Instruction[] instructions) {
        while (programCounter < instructions.length) {
            Instruction instruction = instructions[programCounter++];

            switch (instruction.getOpCode()) {
                case OpCode.PUSH_NUMBER:
                    double numValue = ByteBuffer.wrap(instruction.getArgs()).getDouble();
                    valueStack.push(new MeditorNumber(numValue));
                    break;
                case OpCode.PUSH_STRING:
                    String stringValue = decodeString(instruction.getArgs());
                    valueStack.push(new MeditorString(stringValue));
                    break;
                case OpCode.PUSH_BOOL:
                    byte boolValue = instruction.getArgs()[0];
                    valueStack.push(new MeditorBoolean(boolValue >= 0x01));
                    break;
                case OpCode.PUSH_SYMBOL:
                    String symbolValue = decodeString(instruction.getArgs());
                    valueStack.push(new Symbol(symbolValue));
                    break;
                case OpCode.PUSH_OBJECT:
                    // just puts a new object on the value stack, which
                    // can then be decorated
                    valueStack.push(new MeditorObject());
                    break;
                case OpCode.SET_PROTOTYPE:
                    // requires two objects to be on the stack
                    // - at the top (first to be popped, is the object whose
                    //   prototype is being set)
                    // - at the bottom is the prototype (second to be popped and last)
                    MeditorObject childObj = valueStack.pop();
                    MeditorObject parentObj = valueStack.pop();
                    childObj.setPrototype(parentObj);
                    break;
                case OpCode.SET_PROPERTY:
                    // requires three objects on the stack
                    // - first to be popped: object who is having a property set
                    // - second to be popped: object which is the property
                    // - third: the symbol which represents the property name
                    // puts the object back on the stack after setting its properties
                    // changed the order from above, makes it easier to keep
                    // setting properties as the object will then always
                    // be the last thing popped from the stack
                    Symbol setPropertySymbol = (Symbol) valueStack.pop();
                    MeditorObject newProperty = valueStack.pop();
                    MeditorObject propertyTarget = valueStack.pop();

                    propertyTarget.setProperty(setPropertySymbol, newProperty);
                    valueStack.push(propertyTarget);
                    break;
                case OpCode.GET_PROPERTY:
                    // requires two objects on the stack
                    // - first to pop: symbol
                    // - second: obj to get property from
                    // property is loaded onto the value stack
//                    MeditorObject retrievedObjProperty = valueStack.pop().getProperty((Symbol) valueStack.pop());
                    Symbol getPropSymbol = (Symbol) valueStack.pop();
                    MeditorObject getPropObj = valueStack.pop();
                    valueStack.push(getPropObj.getProperty(getPropSymbol));
                    break;
                case OpCode.POP:
                    break;
                case OpCode.DUP:
                    break;
                case OpCode.SWAP:
                    break;
                case OpCode.ADD_NUM:
                    // pop two values, try adding them together and putting result back on stack
                    MeditorNumber num1 = (MeditorNumber) valueStack.pop();
                    MeditorNumber num2 = (MeditorNumber) valueStack.pop();
                    valueStack.push(new MeditorNumber(num1.getValue() + num2.getValue()));
                    break;
                case OpCode.CON_STR:
                    // pop two values, concat the strings together and put result on stack
                    MeditorString str1 = (MeditorString) valueStack.pop();
                    MeditorString str2 = (MeditorString) valueStack.pop();
                    valueStack.push(new MeditorString(str1.getValue() + str2.getValue()));
                    break;
                case OpCode.PRINT:
                    MeditorObject obj = valueStack.pop();
                    System.out.println(obj.toString());
                    break;
                case OpCode.STORE_GLOBAL:
                    // the instructions args will be a symbol representing it on the global object
                    // the value should already be on the stack
                    Symbol newGlobalPropSymbol = (Symbol) valueStack.pop();
                    MeditorObject newGlobalProperty = valueStack.pop();
                    newGlobalProperty.setPrototype(globalObject);

                    globalObject.setProperty(
                            newGlobalPropSymbol,
                            newGlobalProperty
                    );
                    break;
                case OpCode.LOAD_GLOBAL:
                    valueStack.push(globalObject.getProperty((Symbol) valueStack.pop()));
                    break;
            }
        }
        programCounter = 0;
    }

    private String decodeString(byte[] args) {
        return new String(args, Charset.forName("UTF-8"));
    }

    private void invokeUI(Runnable uiTask) {
        SwingUtilities.invokeLater(uiTask);
    }
}
