package dev.morganwalsh.meditor.vm;

public class Instruction {
    private byte opCode;
    private byte[] args;

    public Instruction(byte opCode) {
        this(opCode, new byte[] {});
    }

    public Instruction(byte opCode, byte[] args) {
        this.opCode = opCode;
        this.args = args;
    }

    public byte getOpCode() {
        return opCode;
    }

    public byte[] getArgs() {
        return args;
    }
}
