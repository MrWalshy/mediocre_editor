package dev.morganwalsh.meditor.vm.data_types;

import java.util.HashMap;
import java.util.Map;

public class MeditorObject {

    // Each Object is its own environment... A function is an object,
    // so can be stored alongside other properties
    // - when looking for a property (data or function), we search this object
    //   and its prototype, which may also check its prototype, until we either
    //   find the correct property or get an error
    // - could i represent the global environment as an object?
    //   - if I do this, the object itself will represent its scope, the same with a function
    //     object which will have its own scope and the parent scope being its parent object
    protected Map<Symbol, MeditorObject> properties;
    protected MeditorObject prototype;
    protected String type;

    public MeditorObject() {
        properties = new HashMap<>();
        prototype = null;
        type = "object";
    }

    public MeditorObject clone() {
        MeditorObject clone = new MeditorObject();

        // copy properties
        for (Symbol key : properties.keySet()) {
            clone.setProperty(key, properties.get(key));
        }

        // set prototype
        clone.setPrototype(prototype);

        return clone;
    }

    public MeditorObject getPrototype() {
        return prototype;
    }

    public void setPrototype(MeditorObject prototype) {
        this.prototype = prototype;
    }

    public MeditorObject getProperty(Symbol name) {
        if (properties.containsKey(name)) return properties.get(name);
        else if (prototype != null) return prototype.getProperty(name);
        else throw new RuntimeException("Could not find property: " + name);
    }

    public void setProperty(Symbol name, MeditorObject value) {
        properties.put(name, value);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}