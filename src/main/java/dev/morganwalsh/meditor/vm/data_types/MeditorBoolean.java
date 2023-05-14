package dev.morganwalsh.meditor.vm.data_types;

import java.util.Objects;

public class MeditorBoolean extends MeditorObject {
    private boolean value;

    public MeditorBoolean(boolean value) {
        super();
        this.value = value;
        type = "boolean";
        setProperty(new Symbol("value"), this);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeditorBoolean that = (MeditorBoolean) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MeditorBoolean{" +
                "value=" + value +
                '}';
    }
}