package jpize.ui.common;

import jpize.util.function.FloatSupplier;
import jpize.util.math.vector.Vec2f;
import java.util.function.Supplier;

public class Constraint {

    public boolean isNumber() {
        return this instanceof ConstraintNumber;
    }

    public boolean isFlag() {
        return this instanceof ConstraintFlag;
    }

    public ConstraintNumber asNumber() {
        return (ConstraintNumber) this;
    }

    public ConstraintFlag asFlag() {
        return (ConstraintFlag) this;
    }


    public boolean isPixel() {
        return (isNumber() && asNumber().getType() == ConstraintNumber.Type.PIXEL);
    }

    public boolean isRelativeAuto() {
        return (isNumber() && asNumber().getType() == ConstraintNumber.Type.RELATIVE_AUTO);
    }

    public boolean isRelativeToWidth() {
        return (isNumber() && asNumber().getType() == ConstraintNumber.Type.RELATIVE_TO_WIDTH);
    }

    public boolean isRelativeToHeight() {
        return (isNumber() && asNumber().getType() == ConstraintNumber.Type.RELATIVE_TO_HEIGHT);
    }

    public boolean isAspect() {
        return (isNumber() && asNumber().getType() == ConstraintNumber.Type.ASPECT);
    }

    public boolean isWrapContent() {
        return (isFlag() && asFlag().getType() == ConstraintFlag.Type.WRAP_CONTENT);
    }


    public float getInPixels(boolean isX, Supplier<Vec2f> parentSize, FloatSupplier selfWidth, FloatSupplier selfHeight, FloatSupplier contentSize) {
        if(this.isNumber()){
            final ConstraintNumber number = this.asNumber();
            return switch(number.getType()) {
                case PIXEL              -> number.getValue();
                case RELATIVE_AUTO      -> number.getValue() * (isX ? parentSize.get().x : parentSize.get().y);
                case RELATIVE_TO_WIDTH  -> number.getValue() * parentSize.get().x;
                case RELATIVE_TO_HEIGHT -> number.getValue() * parentSize.get().y;
                case ASPECT             -> number.getValue() * (isX ? selfHeight.getAsFloat() : selfWidth.getAsFloat());
            };
        }else{
            final ConstraintFlag flag = this.asFlag();
            return switch(flag.getType()) {
                case MATCH_PARENT -> isX ? parentSize.get().x : parentSize.get().y;
                case WRAP_CONTENT -> contentSize.getAsFloat();
            };
        }
    }


    public static Constraint pixel(double pixels) {
        return new ConstraintNumber(ConstraintNumber.Type.PIXEL, (float) pixels);
    }

    public static Constraint pixel(FloatSupplier pixels) {
        return new ConstraintNumber(ConstraintNumber.Type.PIXEL, pixels);
    }

    public static Constraint rel(double ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.RELATIVE_AUTO, (float) ratio);
    }

    public static Constraint rel(FloatSupplier ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.RELATIVE_AUTO, ratio);
    }

    public static Constraint relw(double ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.RELATIVE_TO_WIDTH, (float) ratio);
    }

    public static Constraint relw(FloatSupplier ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.RELATIVE_TO_WIDTH, ratio);
    }

    public static Constraint relh(double ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.RELATIVE_TO_HEIGHT, (float) ratio);
    }

    public static Constraint relh(FloatSupplier ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.RELATIVE_TO_HEIGHT, ratio);
    }

    public static Constraint aspect(double ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.ASPECT, (float) ratio);
    }

    public static Constraint aspect(FloatSupplier ratio) {
        return new ConstraintNumber(ConstraintNumber.Type.ASPECT, ratio);
    }

    private static Constraint matchParent() {
        return new ConstraintFlag(ConstraintFlag.Type.MATCH_PARENT);
    }

    private static Constraint wrapContent() {
        return new ConstraintFlag(ConstraintFlag.Type.WRAP_CONTENT);
    }

    public static Constraint zero = pixel(0F);
    public static Constraint match_parent = matchParent();
    public static Constraint wrap_content = wrapContent();


    public static Constraint parseConstraint(String string) {
        string = string.trim().toLowerCase();

        if(string.equals("match_parent")) return match_parent;
        if(string.equals("wrap_content")) return wrap_content;

        if(string.endsWith("%rel")) {
            final String numberString = string.substring(0, string.length() - 4);
            final float numberPart = Float.parseFloat(numberString);
            return rel(numberPart * 0.01F);
        }
        if(string.endsWith("rel")) {
            final String numberString = string.substring(0, string.length() - 3);
            final float numberPart = Float.parseFloat(numberString);
            return rel(numberPart);
        }
        if(string.endsWith("%relh")) {
            final String numberString = string.substring(0, string.length() - 5);
            final float numberPart = Float.parseFloat(numberString);
            return relh(numberPart * 0.01F);
        }
        if(string.endsWith("relh")) {
            final String numberString = string.substring(0, string.length() - 4);
            final float numberPart = Float.parseFloat(numberString);
            return relh(numberPart);
        }
        if(string.endsWith("%relw")) {
            final String numberString = string.substring(0, string.length() - 5);
            final float numberPart = Float.parseFloat(numberString);
            return relw(numberPart * 0.01F);
        }
        if(string.endsWith("relw")) {
            final String numberString = string.substring(0, string.length() - 4);
            final float numberPart = Float.parseFloat(numberString);
            return relw(numberPart);
        }
        if(string.endsWith("%asp")) {
            final String numberString = string.substring(0, string.length() - 4);
            final float numberPart = Float.parseFloat(numberString);
            return aspect(numberPart * 0.01F);
        }
        if(string.endsWith("asp")) {
            final String numberString = string.substring(0, string.length() - 3);
            final float numberPart = Float.parseFloat(numberString);
            return aspect(numberPart);
        }
        if(string.endsWith("px")) {
            final String numberString = string.substring(0, string.length() - 2);
            final float numberPart = Float.parseFloat(numberString);
            return pixel(numberPart);
        }
        throw new IllegalArgumentException("Cannot parse string: '" + string + "'");
    }

}
