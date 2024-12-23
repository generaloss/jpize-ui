package jpize.ui.common;

import jpize.app.Jpize;
import jpize.ui.component.UIRenderer;
import jpize.util.color.Color;
import jpize.util.mesh.TextureBatch;
import jpize.util.ninepatch.NinePatch;

public class UIDrawableNinePatch implements UIDrawable {

    private final UIDimensions scale;
    private NinePatch ninepatch;
    private final Color color;

    public UIDrawableNinePatch() {
        this.scale = new UIDimensions();
        this.scale.set(Constraint.pixel(1));
        this.color = new Color();
    }

    public UIDrawableNinePatch(NinePatch ninepatch) {
        this();
        this.setNinepatch(ninepatch);
    }


    public UIDimensions scale() {
        return scale;
    }


    public NinePatch getNinepatch() {
        return ninepatch;
    }

    public UIDrawableNinePatch setNinepatch(NinePatch ninepatch) {
        if(this.ninepatch != null)
            this.ninepatch.dispose();
        this.ninepatch = ninepatch;
        return this;
    }

    public Color color() {
        return color;
    }


    @Override
    public void draw(UIRenderer renderer, float x, float y, float width, float height) {
        final TextureBatch batch = renderer.batch();
        if(ninepatch != null){
            batch.setColor(color);
            final float scaleX = scale.width().getInPixels(true, Jpize::getWidth, Jpize::getHeight, () -> width, () -> height, () -> width);
            final float scaleY = scale.height().getInPixels(false, Jpize::getWidth, Jpize::getHeight, () -> width, () -> height, () -> height);
            ninepatch.scale().set(scaleX, scaleY);
            ninepatch.draw(renderer.batch(), x, y, width, height);
        }else{
            batch.drawRect(x, y, width, height, color);
        }
    }

    @Override
    public void dispose() {
        ninepatch.dispose();
    }

}
