package dev.morganwalsh.meditor.vm;

public class OpCode {
    final public static byte PUSH_NUMBER = 0x01;
    final public static byte PUSH_STRING = 0x02;
    final public static byte PUSH_BOOL = 0x03;
    final public static byte PUSH_SYMBOL = 0x04;
    final public static byte PUSH_OBJECT = 0x05;
    final public static byte CREATE_OBJECT = 0x06;
    /**
     * Deletes an object at the specified address in memory. Everything
     * is an object, so this is a dangerous operation if misused.
     */
    final public static byte DELETE_OBJECT = 0x07;
    final public static byte SET_PROTOTYPE = 0x08;
    final public static byte GET_PROPERTY = 0x09;
    final public static byte SET_PROPERTY = 0x0A;
    final public static byte POP = 0x11;
    final public static byte DUP = 0x12;
    final public static byte SWAP = 0x13;
    final public static byte ADD_NUM = 0x20;
    final public static byte SUB_NUM = 0x21;
    final public static byte MUL_NUM = 0x22;
    final public static byte DIV_NUM = 0x23;
    final public static byte CON_STR = 0x24;
    final public static byte AND = 0x25;
    final public static byte OR = 0x26;
    final public static byte NOT = 0x27;
    /**
     * Use to jump to a specific area in code, specify the address
     * of the instruction to jump to.
     */
    final public static byte GOTO = 0x2A;
    /**
     * Like GOTO, but conditional. Only goes to the specified address
     * if value on top of the stack is true.
     */
    final public static byte GOTO_IF = 0x2B;
    /**
     * Calls a function by its address.
     */
    final public static byte FUNC_CALL = 0x30;
    /**
     * Returns from a function
     */
    final public static byte FUNC_RET = 0x31;
    /**
     * Loads an object at the specified address onto the stack..
     */
    final public static byte LOAD_GLOBAL = 0x32;
    /**
     * Stores an object at the specified address in memory.
     */
    final public static byte STORE_GLOBAL = 0x33;

    // util
    final public static byte PRINT = 0x40;
}
