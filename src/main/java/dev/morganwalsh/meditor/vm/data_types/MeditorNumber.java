package dev.morganwalsh.meditor.vm.data_types;

public class MeditorNumber extends MeditorObject {
    private double value;

    public MeditorNumber(double value) {
        super();
        this.value = value;
        this.setProperty(new Symbol("value"), this);
        type = "number";
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MeditorNumber{" +
                "value=" + value +
                '}';
    }
}