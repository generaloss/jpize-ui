package jpize.ui.common;

public class UIDimensions {

    private Constraint width, height;

    public UIDimensions() {
        this.set(Constraint.zero);
    }


    public Constraint width() {
        return width;
    }

    public Constraint height() {
        return height;
    }


    public UIDimensions setWidth(Constraint width) {
        this.width = width;
        return this;
    }

    public UIDimensions setHeight(Constraint height) {
        this.height = height;
        return this;
    }

    public UIDimensions set(Constraint width, Constraint height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public UIDimensions set(Constraint all) {
        return this.set(all, all);
    }

}
