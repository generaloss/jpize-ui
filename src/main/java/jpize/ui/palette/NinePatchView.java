package jpize.ui.palette;

import jpize.ui.component.UIComponent;
import jpize.ui.component.UIContext;
import jpize.ui.common.UIDrawableNinePatch;
import jpize.util.color.AbstractColor;
import jpize.util.ninepatch.NinePatch;

public class NinePatchView extends UIComponent {
    
    private final UIDrawableNinePatch image;

    public NinePatchView(UIContext context) {
        super(context);
        this.image = new UIDrawableNinePatch();
        this.setBackground(image);
    }

    public NinePatchView(UIContext context, NinePatch ninepatch) {
        this(context);
        image.setNinepatch(ninepatch);
    }


    public NinePatchView(UIContext context, NinePatch ninepatch, float r, float g, float b, float a) {
        this(context);
        image.setNinepatch(ninepatch);
        image.color().set(r, g, b, a);
    }

    public NinePatchView(UIContext context, NinePatch ninepatch, float r, float g, float b) {
        this(context, ninepatch, r, g, b, 1F);
    }

    public NinePatchView(UIContext context, NinePatch ninepatch, float alpha) {
        this(context, ninepatch, 1F, 1F, 1F, alpha);
    }

    public NinePatchView(UIContext context, NinePatch ninepatch, AbstractColor color) {
        this(context, ninepatch, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
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
