package dev.morganwalsh.meditor.vm.data_types;

import java.util.Objects;

public class Symbol extends MeditorObject {

    private String name;

    public Symbol(String name) {
        this.name = name;
        type = "symbol";
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(name, symbol.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                '}';
    }
}
