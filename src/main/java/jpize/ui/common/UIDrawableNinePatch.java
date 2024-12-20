package jpize.ui.common;

import jpize.ui.component.UIRenderer;
import jpize.util.color.Color;
import jpize.util.mesh.TextureBatch;
import jpize.util.ninepatch.NinePatch;

public class UIDrawableNinePatch implements UIDrawable {

    private NinePatch ninepatch;
    private final Color color;

    public UIDrawableNinePatch() {
        this.color = new Color();
    }

    public UIDrawableNinePatch(NinePatch ninepatch) {
        this();
        this.setNinepatch(ninepatch);
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
