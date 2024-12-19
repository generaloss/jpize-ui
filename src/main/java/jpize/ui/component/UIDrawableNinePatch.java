package jpize.ui.component;

import jpize.gl.texture.Texture2D;
import jpize.util.array.IntList;
import jpize.util.color.Color;
import jpize.util.pixmap.Pixmap;
import jpize.util.pixmap.PixmapRGBA;
import jpize.util.region.TextureRegion;

import java.util.Arrays;

public class UIDrawableNinePatch implements UIDrawable {

    private static class Patch {
        public final TextureRegion region;
        public final boolean stretchable;
        public Patch(TextureRegion region, boolean stretchable) {
            this.region = region;
            this.stretchable = stretchable;
        }
    }


    private Texture2D image;
    private final Color color;
    private Patch[][] patches;

    public UIDrawableNinePatch() {
        this.color = new Color();
    }

    public UIDrawableNinePatch(Texture2D texture) {
        this();
        this.setImage(texture);
    }

    public UIDrawableNinePatch(Pixmap pixmap) {
        this(new Texture2D(pixmap));
    }

    public UIDrawableNinePatch(String internalPath) {
        this(new Texture2D(internalPath));
    }


    public Texture2D image() {
        return image;
    }

    public Color color() {
        return color;
    }


    private boolean isBlackColor(int color) {
        return ((color & 0xFFFFFF) == 0 && (color & 0x000000FF) == 255);
    }

    private int[] getHorizontalParts(PixmapRGBA pixels) {
        final IntList partsList = new IntList();
        boolean prevStretchable = this.isBlackColor(pixels.getPixelARGB(1, 0));
        for(int x = 1; x < pixels.getWidth() - 1; x++){
            System.out.println(Integer.toHexString(pixels.getPixelRGBA(x, 0)));
            final boolean stretchable = this.isBlackColor(pixels.getPixelARGB(x, 0));
            System.out.println(stretchable);
            if(prevStretchable != stretchable){
                prevStretchable = stretchable;
                partsList.add(x - 2);
            }
        }
        return partsList.arrayTrimmed();
    }

    private int[] getVerticalParts(PixmapRGBA pixels) {
        final IntList partsList = new IntList();
        boolean prevStretchable = this.isBlackColor(pixels.getPixelARGB(1, 0));
        for(int y = 2; y < pixels.getHeight() - 1; y++){
            final boolean stretchable = this.isBlackColor(pixels.getPixelARGB(0, y));
            if(prevStretchable != stretchable){
                prevStretchable = stretchable;
                partsList.add(y - 2);
            }
        }
        return partsList.arrayTrimmed();
    }


    public UIDrawableNinePatch setImage(Texture2D texture) {
        this.image = texture;
        final PixmapRGBA pixels = new PixmapRGBA(texture.getWidth(), texture.getHeight());
        final int[] horizontalParts = this.getHorizontalParts(pixels);
        final int[] verticalParts = this.getVerticalParts(pixels);
        System.out.println("h: " + Arrays.toString(horizontalParts) + ", v: " + Arrays.toString(verticalParts));
        return this;
    }

    public UIDrawableNinePatch setImage(Pixmap pixmap) {
        return this.setImage(new Texture2D(pixmap));
    }

    public UIDrawableNinePatch setImage(String internalPath) {
        return this.setImage(new Texture2D(internalPath));
    }


    @Override
    public void draw(UIRenderer renderer, float x, float y, float width, float height) {

    }

}
