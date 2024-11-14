package jpize.ui;

import jpize.util.function.FloatSupplier;
import jpize.util.math.vector.Vec2f;
import java.util.function.Supplier;

public class Constraint {

    public static class Number extends Constraint {

        public enum Type {
            PIXEL             , // pixels
            RELATIVE_AUTO     , // relative to width or height depending on the context of use
            RELATIVE_TO_WIDTH , // relative to width
            RELATIVE_TO_HEIGHT, // relative to height
            ASPECT            , // relative to the side on the other axis
        }

        private Type type;
        private FloatSupplier value;

        public Number(Type type, FloatSupplier value) {
            this.setType(type);
            this.setValue(value);
        }

        public Number(Type type, float value) {
            this.setType(type);
            this.setValue(value);
        }

        public Type getType() {
            return type;
        }

        private Number setType(Type type) {
            this.type = type;
            return this;
        }

        public float getValue() {
            return value.getAsFloat();
        }

        private Number setValue(FloatSupplier value) {
            this.value = value;
            return this;
        }

        private Number setValue(float value) {
            this.value = () -> value;
            return this;
        }

    }

    public static class Flag extends Constraint {

        public enum Type {
            MATCH_PARENT,
            WRAP_CONTENT,
        }

        private Type type;

        public Flag(Type type) {
            this.setType(type);
        }

        public Type getType() {
            return type;
        }

        private Flag setType(Type type) {
            this.type = type;
            return this;
        }

    }

    public boolean isNumber() {
        return this instanceof Number;
    }

    public boolean isFlag() {
        return this instanceof Flag;
    }

    public Number asNumber() {
        return (Number) this;
    }

    public Flag asFlag() {
        return (Flag) this;
    }


    public boolean isPixel() {
        return (isNumber() && asNumber().getType() == Number.Type.PIXEL);
    }

    public boolean isRelativeAuto() {
        return (isNumber() && asNumber().getType() == Number.Type.RELATIVE_AUTO);
    }

    public boolean isRelativeToWidth() {
        return (isNumber() && asNumber().getType() == Number.Type.RELATIVE_TO_WIDTH);
    }

    public boolean isRelativeToHeight() {
        return (isNumber() && asNumber().getType() == Number.Type.RELATIVE_TO_HEIGHT);
    }

    public boolean isAspect() {
        return (isNumber() && asNumber().getType() == Number.Type.ASPECT);
    }

    public boolean isWrapContent() {
        return (isFlag() && asFlag().getType() == Flag.Type.WRAP_CONTENT);
    }


    public float getInPixels(boolean isX, Supplier<Vec2f> parentSize, FloatSupplier selfWidth, FloatSupplier selfHeight, FloatSupplier contentSize) {
        if(this.isNumber()){
            final Number number = this.asNumber();
            return switch(number.getType()) {
                case PIXEL              -> number.getValue();
                case RELATIVE_AUTO      -> number.getValue() * (isX ? parentSize.get().x : parentSize.get().y);
                case RELATIVE_TO_WIDTH  -> number.getValue() * parentSize.get().x;
                case RELATIVE_TO_HEIGHT -> number.getValue() * parentSize.get().y;
                case ASPECT             -> number.getValue() * (isX ? selfHeight.getAsFloat() : selfWidth.getAsFloat());
            };
        }else{
            final Flag flag = this.asFlag();
            return switch(flag.getType()) {
                case MATCH_PARENT -> isX ? parentSize.get().x : parentSize.get().y;
                case WRAP_CONTENT -> contentSize.getAsFloat();
            };
        }
    }


    public static Constraint pixel(double pixels) {
        return new Constraint.Number(Number.Type.PIXEL, (float) pixels);
    }

    public static Constraint pixel(FloatSupplier pixels) {
        return new Constraint.Number(Number.Type.PIXEL, pixels);
    }

    public static Constraint rel(double ratio) {
        return new Constraint.Number(Number.Type.RELATIVE_AUTO, (float) ratio);
    }

    public static Constraint rel(FloatSupplier ratio) {
        return new Constraint.Number(Number.Type.RELATIVE_AUTO, ratio);
    }

    public static Constraint relw(double ratio) {
        return new Constraint.Number(Number.Type.RELATIVE_TO_WIDTH, (float) ratio);
    }

    public static Constraint relw(FloatSupplier ratio) {
        return new Constraint.Number(Number.Type.RELATIVE_TO_WIDTH, ratio);
    }

    public static Constraint relh(double ratio) {
        return new Constraint.Number(Number.Type.RELATIVE_TO_HEIGHT, (float) ratio);
    }

    public static Constraint relh(FloatSupplier ratio) {
        return new Constraint.Number(Number.Type.RELATIVE_TO_HEIGHT, ratio);
    }

    public static Constraint aspect(double ratio) {
        return new Constraint.Number(Number.Type.ASPECT, (float) ratio);
    }

    public static Constraint aspect(FloatSupplier ratio) {
        return new Constraint.Number(Number.Type.ASPECT, ratio);
    }

    private static Constraint wrapContent() {
        return new Constraint.Flag(Flag.Type.WRAP_CONTENT);
    }

    public static Constraint zero = pixel(0F);
    public static Constraint match_parent = rel(1F);
    public static Constraint wrap_content = wrapContent();

}
