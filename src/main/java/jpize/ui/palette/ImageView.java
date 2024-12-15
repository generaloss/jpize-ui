package jpize.ui.palette;

import jpize.gl.texture.Texture2D;
import jpize.ui.component.*;
import jpize.util.color.Color;
import jpize.util.pixmap.Pixmap;
import jpize.util.region.TextureRegion;

public class ImageView extends UIComponent {

    private final UIDrawableImage background;

    public ImageView(UIContext context) {
        super(context);
        this.background = new UIDrawableImage();
        this.setBackground(background);
    }


    public ImageView(UIContext context, TextureRegion textureRegion) {
        this(context);
        background.setImage(textureRegion);
    }

    public ImageView(UIContext context, Texture2D texture) {
        this(context);
        background.setImage(texture);
    }

    public ImageView(UIContext context, Pixmap pixmap) {
        this(context);
        background.setImage(pixmap);
    }

    public ImageView(UIContext context, String internalPath) {
        this(context);
        background.setImage(internalPath);
    }


    public ImageView(UIContext context, TextureRegion textureRegion, float r, float g, float b, float a) {
        this(context);
        background.setImage(textureRegion);
        background.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, TextureRegion textureRegion, float r, float g, float b) {
        this(context, textureRegion, r, g, b, 1F);
    }

    public ImageView(UIContext context, TextureRegion textureRegion, float alpha) {
        this(context, textureRegion, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, TextureRegion textureRegion, Color color) {
        this(context, textureRegion, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, Texture2D texture, float r, float g, float b, float a) {
        this(context);
        background.setImage(texture);
        background.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, Texture2D texture, float r, float g, float b) {
        this(context, texture, r, g, b, 1F);
    }

    public ImageView(UIContext context, Texture2D texture, float alpha) {
        this(context, texture, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, Texture2D texture, Color color) {
        this(context, texture, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, Pixmap pixmap, float r, float g, float b, float a) {
        this(context);
        background.setImage(pixmap);
        background.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, Pixmap pixmap, float r, float g, float b) {
        this(context, pixmap, r, g, b, 1F);
    }

    public ImageView(UIContext context, Pixmap pixmap, float alpha) {
        this(context, pixmap, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, Pixmap pixmap, Color color) {
        this(context, pixmap, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, String internalPath, float r, float g, float b, float a) {
        this(context);
        background.setImage(internalPath);
        background.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, String internalPath, float r, float g, float b) {
        this(context, internalPath, r, g, b, 1F);
    }

    public ImageView(UIContext context, String internalPath, float alpha) {
        this(context, internalPath, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, String internalPath, Color color) {
        this(context, internalPath, color.r, color.g, color.b, color.a);
    }


    public ImageView(UIContext context, float r, float g, float b, float a) {
        this(context);
        background.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, float r, float g, float b) {
        this(context, r, g, b, 1F);
    }

    public ImageView(UIContext context, Color color) {
        this(context, color.r, color.g, color.b, color.a);
    }


    public UIDrawableImage background() {
        return background;
    }

}
