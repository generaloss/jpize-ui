package jpize.ui;

import jpize.util.math.vector.Vec2f;

public class UIBindInsets {

    private final UIBind top, left, bottom, right;
    private final Vec2f bias;

    public UIBindInsets() {
        this.top = new UIBind();
        this.left = new UIBind();
        this.bottom = new UIBind();
        this.right = new UIBind();
        this.bias = new Vec2f(0.5F);
    }

    public UIBind top() {
        return top;
    }

    public UIBind left() {
        return left;
    }

    public UIBind bottom() {
        return bottom;
    }

    public UIBind right() {
        return right;
    }

    public UIBindInsets toCenter() {
        top.set(UIDir.TOP);
        left.set(UIDir.LEFT);
        bottom.set(UIDir.BOTTOM);
        right.set(UIDir.RIGHT);
        return this;
    }

    public Vec2f bias() {
        return bias;
    }

}
