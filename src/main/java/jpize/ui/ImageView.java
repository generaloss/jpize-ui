package jpize.ui;

import jpize.gl.texture.Texture2D;
import jpize.util.color.Color;
import jpize.util.pixmap.Pixmap;
import jpize.util.region.TextureRegion;

public class ImageView extends UIComponent {

    public ImageView(UIContext context) {
        super(context);
    }


    public ImageView(UIContext context, TextureRegion image) {
        super(context);
        super.background().setImage(image);
    }

    public ImageView(UIContext context, Texture2D image) {
        super(context);
        super.background().setImage(image);
    }

    public ImageView(UIContext context, Pixmap image) {
        super(context);
        super.background().setImage(image);
    }

    public ImageView(UIContext context, String image) {
        super(context);
        super.background().setImage(image);
    }


    public ImageView(UIContext context, TextureRegion image, float r, float g, float b, float a) {
        super(context);
        super.background().setImage(image);
        super.background().color().set(r, g, b, a);
    }

    public ImageView(UIContext context, TextureRegion image, float r, float g, float b) {
        this(context, image, r, g, b, 1F);
    }

    public ImageView(UIContext context, TextureRegion image, float alpha) {
        this(context, image, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, TextureRegion image, Color color) {
        this(context, image, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, Texture2D image, float r, float g, float b, float a) {
        super(context);
        super.background().setImage(image);
        super.background().color().set(r, g, b, a);
    }

    public ImageView(UIContext context, Texture2D image, float r, float g, float b) {
        this(context, image, r, g, b, 1F);
    }

    public ImageView(UIContext context, Texture2D image, float alpha) {
        this(context, image, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, Texture2D image, Color color) {
        this(context, image, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, Pixmap image, float r, float g, float b, float a) {
        super(context);
        super.background().setImage(image);
        super.background().color().set(r, g, b, a);
    }

    public ImageView(UIContext context, Pixmap image, float r, float g, float b) {
        this(context, image, r, g, b, 1F);
    }

    public ImageView(UIContext context, Pixmap image, float alpha) {
        this(context, image, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, Pixmap image, Color color) {
        this(context, image, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, String image, float r, float g, float b, float a) {
        super(context);
        super.background().setImage(image);
        super.background().color().set(r, g, b, a);
    }

    public ImageView(UIContext context, String image, float r, float g, float b) {
        this(context, image, r, g, b, 1F);
    }

    public ImageView(UIContext context, String image, float alpha) {
        this(context, image, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, String image, Color color) {
        this(context, image, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, float r, float g, float b, float a) {
        super(context);
        super.background().color().set(r, g, b, a);
    }

    public ImageView(UIContext context, float r, float g, float b) {
        this(context, r, g, b, 1F);
    }

    public ImageView(UIContext context, Color color) {
        this(context, color.r, color.g, color.b, color.a);
    }

}
