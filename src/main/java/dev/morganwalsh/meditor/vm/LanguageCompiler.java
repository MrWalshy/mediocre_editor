package dev.morganwalsh.meditor.vm;

public interface LanguageCompiler {
    Instruction[] compile(String input);
}
