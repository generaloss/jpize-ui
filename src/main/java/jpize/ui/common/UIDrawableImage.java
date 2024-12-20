package jpize.ui.common;

import jpize.gl.texture.Texture2D;
import jpize.ui.component.UIRenderer;
import jpize.util.color.Color;
import jpize.util.mesh.TextureBatch;
import jpize.util.pixmap.Pixmap;
import jpize.util.region.TextureRegion;

public class UIDrawableImage implements UIDrawable {

    private TextureRegion image;
    private final Color color;

    public UIDrawableImage() {
        this.color = new Color();
    }

    public UIDrawableImage(TextureRegion textureRegion) {
        this();
        this.image = textureRegion;
    }

    public TextureRegion image() {
        return image;
    }

    public Color color() {
        return color;
    }


    public UIDrawableImage setImage(TextureRegion textureRegion) {
        this.dispose();
        this.image = textureRegion;
        return this;
    }

    public UIDrawableImage setImage(Texture2D texture) {
        return this.setImage(new TextureRegion(texture));
    }

    public UIDrawableImage setImage(Pixmap pixmap) {
        return this.setImage(new Texture2D(pixmap));
    }

    public UIDrawableImage setImage(String internalPath) {
        return this.setImage(new Texture2D(internalPath));
    }


    @Override
    public void draw(UIRenderer renderer, float x, float y, float width, float height) {
        final TextureBatch batch = renderer.batch();
        if(image != null){
            batch.setColor(color);
            batch.draw(image, x, y, width, height);
        }else{
            batch.drawRect(x, y, width, height, color);
        }
    }

    @Override
    public void dispose() {
        if(image != null)
            image.getTexture().dispose();
    }

}
