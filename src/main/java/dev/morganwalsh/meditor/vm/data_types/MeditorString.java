package dev.morganwalsh.meditor.vm.data_types;

import java.util.Objects;

public class MeditorString extends MeditorObject {
    private String value;

    public MeditorString(String value) {
        super();
        this.value = value;
        setProperty(new Symbol("value"), this);
        type = "string";
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeditorString that = (MeditorString) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "MeditorString{" +
                "value='" + value + '\'' +
                '}';
    }
}
