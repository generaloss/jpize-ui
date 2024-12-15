package jpize.ui.common;

public class UICorners {

    private Constraint left_top;
    private Constraint left_bottom;
    private Constraint right_bottom;
    private Constraint right_top;

    public UICorners() {
        left_top = Constraint.zero;
        left_bottom = Constraint.zero;
        right_bottom = Constraint.zero;
        right_top = Constraint.zero;
    }

    public Constraint getLeftTop() {
        return left_top;
    }

    public Constraint getLeftBottom() {
        return left_bottom;
    }

    public Constraint getRightBottom() {
        return right_bottom;
    }

    public Constraint getRightTop() {
        return right_top;
    }

    public Constraint[] constraints() {
        return new Constraint[]{ left_top, left_bottom, right_bottom, right_top };
    }


    public UICorners round(Constraint radiusLT, Constraint radiusLB, Constraint radiusRB, Constraint radiusRT) {
        left_top = radiusLT;
        left_bottom = radiusLB;
        right_bottom = radiusRB;
        right_top = radiusRT;
        return this;
    }

    public UICorners round(Constraint radius) {
        return this.round(radius, radius, radius, radius);
    }

    public UICorners roundLeftTop(Constraint radius) {
        left_top = radius;
        return this;
    }

    public UICorners roundLeftBottom(Constraint radius) {
        left_bottom = radius;
        return this;
    }

    public UICorners roundRightBottom(Constraint radius) {
        right_bottom = radius;
        return this;
    }

    public UICorners roundRightTop(Constraint radius) {
        right_top = radius;
        return this;
    }

}
