package jpize.ui.common;

public class UIInsets {

    private Constraint top, left, bottom, right;

    public UIInsets(Constraint top, Constraint left, Constraint bottom, Constraint right) {
        this.set(top, left, bottom, right);
    }

    public UIInsets(Constraint all) {
        this.set(all);
    }

    public UIInsets() {
        this.set(Constraint.zero);
    }


    public Constraint top() {
        return top;
    }

    public Constraint left() {
        return left;
    }

    public Constraint bottom() {
        return bottom;
    }

    public Constraint right() {
        return right;
    }


    public UIInsets setTop(Constraint top) {
        this.top = top;
        return this;
    }

    public UIInsets setLeft(Constraint left) {
        this.left = left;
        return this;
    }

    public UIInsets setBottom(Constraint bottom) {
        this.bottom = bottom;
        return this;
    }

    public UIInsets setRight(Constraint right) {
        this.right = right;
        return this;
    }

    public UIInsets set(Constraint top, Constraint left, Constraint bottom, Constraint right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        return this;
    }

    public UIInsets set(Constraint vertical, Constraint horizontal) {
        this.top = vertical;
        this.left = horizontal;
        this.bottom = vertical;
        this.right = horizontal;
        return this;
    }

    public UIInsets set(Constraint all) {
        return this.set(all, all, all, all);
    }

}
