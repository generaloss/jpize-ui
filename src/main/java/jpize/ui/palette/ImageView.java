package jpize.ui.palette;

import jpize.gl.texture.Texture2D;
import jpize.ui.component.*;
import jpize.util.color.AbstractColor;
import jpize.util.pixmap.Pixmap;
import jpize.util.region.TextureRegion;

public class ImageView extends UIComponent {

    private final UIDrawableImage image;

    public ImageView(UIContext context) {
        super(context);
        this.image = new UIDrawableImage();
        this.setBackground(image);
    }


    public ImageView(UIContext context, TextureRegion textureRegion) {
        this(context);
        image.setImage(textureRegion);
    }

    public ImageView(UIContext context, Texture2D texture) {
        this(context);
        image.setImage(texture);
    }

    public ImageView(UIContext context, Pixmap pixmap) {
        this(context);
        image.setImage(pixmap);
    }

    public ImageView(UIContext context, String internalPath) {
        this(context);
        image.setImage(internalPath);
    }


    public ImageView(UIContext context, TextureRegion textureRegion, float r, float g, float b, float a) {
        this(context);
        image.setImage(textureRegion);
        image.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, TextureRegion textureRegion, float r, float g, float b) {
        this(context, textureRegion, r, g, b, 1F);
    }

    public ImageView(UIContext context, TextureRegion textureRegion, float alpha) {
        this(context, textureRegion, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, TextureRegion textureRegion, AbstractColor color) {
        this(context, textureRegion, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public ImageView(UIContext context, Texture2D texture, float r, float g, float b, float a) {
        this(context);
        image.setImage(texture);
        image.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, Texture2D texture, float r, float g, float b) {
        this(context, texture, r, g, b, 1F);
    }

    public ImageView(UIContext context, Texture2D texture, float alpha) {
        this(context, texture, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, Texture2D texture, AbstractColor color) {
        this(context, texture, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public ImageView(UIContext context, Pixmap pixmap, float r, float g, float b, float a) {
        this(context);
        image.setImage(pixmap);
        image.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, Pixmap pixmap, float r, float g, float b) {
        this(context, pixmap, r, g, b, 1F);
    }

    public ImageView(UIContext context, Pixmap pixmap, float alpha) {
        this(context, pixmap, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, Pixmap pixmap, AbstractColor color) {
        this(context, pixmap, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public ImageView(UIContext context, String internalPath, float r, float g, float b, float a) {
        this(context);
        image.setImage(internalPath);
        image.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, String internalPath, float r, float g, float b) {
        this(context, internalPath, r, g, b, 1F);
    }

    public ImageView(UIContext context, String internalPath, float alpha) {
        this(context, internalPath, 1F, 1F, 1F, alpha);
    }

    public ImageView(UIContext context, String internalPath, AbstractColor color) {
        this(context, internalPath, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public ImageView(UIContext context, float r, float g, float b, float a) {
        this(context);
        image.color().set(r, g, b, a);
    }

    public ImageView(UIContext context, float r, float g, float b) {
        this(context, r, g, b, 1F);
    }

    public ImageView(UIContext context, AbstractColor color) {
        this(context, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public UIDrawableImage background() {
        return image;
    }

}
