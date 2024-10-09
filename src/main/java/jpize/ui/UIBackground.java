package jpize.ui;

import jpize.gl.texture.Texture2D;
import jpize.util.color.Color;
import jpize.util.pixmap.Pixmap;
import jpize.util.region.TextureRegion;

import java.util.Arrays;

public class UIBackground {

    private TextureRegion image;
    private final Color color;
    private final Constraint[] round;
    private Constraint borderWidth;
    private final Color borderColor;

    public UIBackground() {
        this.color = new Color(); //! default color
        this.round = new Constraint[4];
        Arrays.fill(round, Constraint.zero);
        this.borderWidth = Constraint.zero;
        this.borderColor = new Color(); //! default border color
    }


    public TextureRegion image() {
        return image;
    }

    public UIBackground setImage(TextureRegion image) {
        this.image = image;
        return this;
    }

    public UIBackground setImage(Texture2D image) {
        return this.setImage(new TextureRegion(image));
    }

    public UIBackground setImage(Pixmap image) {
        return this.setImage(new Texture2D(image));
    }

    public UIBackground setImage(String image) {
        return this.setImage(new Texture2D(image));
    }


    public Color color() {
        return color;
    }


    public Constraint[] roundCornerConstraints() {
        return round;
    }

    public UIBackground roundCorner(UICornerDir dir, Constraint radius) {
        round[dir.ordinal()] = radius;
        return this;
    }

    public UIBackground roundCorners(Constraint radiusLT, Constraint radiusLB, Constraint radiusRB, Constraint radiusRT) {
        round[UICornerDir.LEFT_TOP    .ordinal()] = radiusLT;
        round[UICornerDir.LEFT_BOTTOM .ordinal()] = radiusLB;
        round[UICornerDir.RIGHT_BOTTOM.ordinal()] = radiusRB;
        round[UICornerDir.RIGHT_TOP   .ordinal()] = radiusRT;
        return this;
    }

    public UIBackground roundCorners(Constraint radius) {
        return this.roundCorners(radius, radius, radius, radius);
    }

    public UIBackground roundCornerLeftTop(Constraint radius) {
        return this.roundCorner(UICornerDir.LEFT_TOP, radius);
    }

    public UIBackground roundCornerLeftBottom(Constraint radius) {
        return this.roundCorner(UICornerDir.LEFT_BOTTOM, radius);
    }

    public UIBackground roundCornerRightBottom(Constraint radius) {
        return this.roundCorner(UICornerDir.RIGHT_BOTTOM, radius);
    }

    public UIBackground roundCornerRightTop(Constraint radius) {
        return this.roundCorner(UICornerDir.RIGHT_TOP, radius);
    }


    public Constraint getBorderWidth() {
        return borderWidth;
    }

    public UIBackground setBorderWidth(Constraint borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }


    public Color borderColor() {
        return borderColor;
    }

}
