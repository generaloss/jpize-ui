package jpize.ui.component;

import jpize.gl.texture.Texture2D;
import jpize.ui.common.Constraint;
import jpize.ui.common.UICorner;
import jpize.util.color.Color;
import jpize.util.pixmap.Pixmap;
import jpize.util.region.TextureRegion;

import java.util.Arrays;

public class UIBackground {

    private TextureRegion image;
    private final Color color;
    private final Constraint[] corners_radius;
    private Constraint borders_width;
    private final Color borderColor;

    public UIBackground() {
        this.color = new Color(0.2, 0.21, 0.24); //! default color
        this.corners_radius = new Constraint[4];
        Arrays.fill(corners_radius, Constraint.zero);
        this.borders_width = Constraint.pixel(2);
        this.borderColor = new Color(0.1, 0.11, 0.15); //! default border color
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
        return corners_radius;
    }

    public UIBackground roundCorner(UICorner dir, Constraint radius) {
        corners_radius[dir.ordinal()] = radius;
        return this;
    }

    public UIBackground roundCorners(Constraint radiusLT, Constraint radiusLB, Constraint radiusRB, Constraint radiusRT) {
        corners_radius[UICorner.LEFT_TOP    .ordinal()] = radiusLT;
        corners_radius[UICorner.LEFT_BOTTOM .ordinal()] = radiusLB;
        corners_radius[UICorner.RIGHT_BOTTOM.ordinal()] = radiusRB;
        corners_radius[UICorner.RIGHT_TOP   .ordinal()] = radiusRT;
        return this;
    }

    public UIBackground roundCorners(Constraint radius) {
        return this.roundCorners(radius, radius, radius, radius);
    }

    public UIBackground roundCornerLeftTop(Constraint radius) {
        return this.roundCorner(UICorner.LEFT_TOP, radius);
    }

    public UIBackground roundCornerLeftBottom(Constraint radius) {
        return this.roundCorner(UICorner.LEFT_BOTTOM, radius);
    }

    public UIBackground roundCornerRightBottom(Constraint radius) {
        return this.roundCorner(UICorner.RIGHT_BOTTOM, radius);
    }

    public UIBackground roundCornerRightTop(Constraint radius) {
        return this.roundCorner(UICorner.RIGHT_TOP, radius);
    }


    public Constraint getBorderWidth() {
        return borders_width;
    }

    public UIBackground setBorderWidth(Constraint borderWidth) {
        this.borders_width = borderWidth;
        return this;
    }


    public Color borderColor() {
        return borderColor;
    }

}
