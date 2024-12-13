package jpize.ui.common;

public enum UIDir {

    NONE   (false),
    TOP    (false),
    LEFT   (true ),
    BOTTOM (false),
    RIGHT  (true );


    private final boolean axisX;

    UIDir(boolean axisX) {
        this.axisX = axisX;
    }

    public boolean isAxisX() {
        return axisX;
    }

    public boolean isAxisY() {
        return !axisX && !this.isNone();
    }


    public boolean isNone() {
        return (this == NONE);
    }

    public boolean isTop() {
        return (this == TOP);
    }

    public boolean isLeft() {
        return (this == LEFT);
    }

    public boolean isBottom() {
        return (this == BOTTOM);
    }

    public boolean isRight() {
        return (this == RIGHT);
    }

}
