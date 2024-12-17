package jpize.ui.component;

import jpize.gl.texture.Texture2D;
import jpize.util.color.Color;
import jpize.util.pixmap.Pixmap;

public class UIDrawableNinePatchImage implements UIDrawable {

    private static class Patch {



    }


    private Texture2D image;
    private final Color color;
    private Patch[][] patches;

    public UIDrawableNinePatchImage() {

    }


    public Texture2D image() {
        return image;
    }

    public Color color() {
        return color;
    }


    public UIDrawableNinePatchImage setImage(Texture2D texture) {
        this.image = texture;



        return this;
    }

    public UIDrawableNinePatchImage setImage(Pixmap pixmap) {
        return this.setImage(new Texture2D(pixmap));
    }

    public UIDrawableNinePatchImage setImage(String internalPath) {
        return this.setImage(new Texture2D(internalPath));
    }


    @Override
    public void draw(UIRenderer renderer, float x, float y, float width, float height) {

    }

}
