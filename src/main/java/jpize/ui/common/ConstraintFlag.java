package jpize.ui.common;

public class ConstraintFlag extends Constraint {

    public enum Type {
        MATCH_PARENT,
        WRAP_CONTENT,
    }

    private Type type;

    public ConstraintFlag(Type type) {
        this.setType(type);
    }

    public Type getType() {
        return type;
    }

    private ConstraintFlag setType(Type type) {
        this.type = type;
        return this;
    }

}