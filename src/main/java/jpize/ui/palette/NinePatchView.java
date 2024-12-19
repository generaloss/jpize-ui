package jpize.ui.palette;

import jpize.gl.texture.Texture2D;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIContext;
import jpize.ui.component.UIDrawableNinePatch;
import jpize.util.color.AbstractColor;
import jpize.util.pixmap.Pixmap;

public class NinePatchView extends UIComponent {
    
    private final UIDrawableNinePatch image;

    public NinePatchView(UIContext context) {
        super(context);
        this.image = new UIDrawableNinePatch();
        this.setBackground(image);
    }


    public NinePatchView(UIContext context, Texture2D texture) {
        this(context);
        image.setImage(texture);
    }

    public NinePatchView(UIContext context, Pixmap pixmap) {
        this(context);
        image.setImage(pixmap);
    }

    public NinePatchView(UIContext context, String internalPath) {
        this(context);
        image.setImage(internalPath);
    }


    public NinePatchView(UIContext context, Texture2D texture, float r, float g, float b, float a) {
        this(context);
        image.setImage(texture);
        image.color().set(r, g, b, a);
    }

    public NinePatchView(UIContext context, Texture2D texture, float r, float g, float b) {
        this(context, texture, r, g, b, 1F);
    }

    public NinePatchView(UIContext context, Texture2D texture, float alpha) {
        this(context, texture, 1F, 1F, 1F, alpha);
    }

    public NinePatchView(UIContext context, Texture2D texture, AbstractColor color) {
        this(context, texture, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public NinePatchView(UIContext context, Pixmap pixmap, float r, float g, float b, float a) {
        this(context);
        image.setImage(pixmap);
        image.color().set(r, g, b, a);
    }

    public NinePatchView(UIContext context, Pixmap pixmap, float r, float g, float b) {
        this(context, pixmap, r, g, b, 1F);
    }

    public NinePatchView(UIContext context, Pixmap pixmap, float alpha) {
        this(context, pixmap, 1F, 1F, 1F, alpha);
    }

    public NinePatchView(UIContext context, Pixmap pixmap, AbstractColor color) {
        this(context, pixmap, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public NinePatchView(UIContext context, String internalPath, float r, float g, float b, float a) {
        this(context);
        image.setImage(internalPath);
        image.color().set(r, g, b, a);
    }

    public NinePatchView(UIContext context, String internalPath, float r, float g, float b) {
        this(context, internalPath, r, g, b, 1F);
    }

    public NinePatchView(UIContext context, String internalPath, float alpha) {
        this(context, internalPath, 1F, 1F, 1F, alpha);
    }

    public NinePatchView(UIContext context, String internalPath, AbstractColor color) {
        this(context, internalPath, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public NinePatchView(UIContext context, float r, float g, float b, float a) {
        this(context);
        image.color().set(r, g, b, a);
    }

    public NinePatchView(UIContext context, float r, float g, float b) {
        this(context, r, g, b, 1F);
    }

    public NinePatchView(UIContext context, AbstractColor color) {
        this(context, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }


    public UIDrawableNinePatch background() {
        return image;
    }
    
}
