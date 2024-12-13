package jpize.ui.common;

import jpize.util.function.FloatSupplier;

public class ConstraintNumber extends Constraint {

    public enum Type {
        PIXEL             , // pixels
        RELATIVE_AUTO     , // relative to width or height depending on the context of use
        RELATIVE_TO_WIDTH , // relative to width
        RELATIVE_TO_HEIGHT, // relative to height
        ASPECT            , // relative to the side on the other axis
    }

    private Type type;
    private FloatSupplier value;

    public ConstraintNumber(Type type, FloatSupplier value) {
        this.setType(type);
        this.setValue(value);
    }

    public ConstraintNumber(Type type, float value) {
        this.setType(type);
        this.setValue(value);
    }

    public Type getType() {
        return type;
    }

    private ConstraintNumber setType(Type type) {
        this.type = type;
        return this;
    }

    public float getValue() {
        return value.getAsFloat();
    }

    private ConstraintNumber setValue(FloatSupplier value) {
        this.value = value;
        return this;
    }

    private ConstraintNumber setValue(float value) {
        this.value = () -> value;
        return this;
    }

}