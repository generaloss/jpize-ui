package jpize.ui.loader.mapper;

import jpize.ui.loader.UILoader;
import jpize.util.region.TextureRegion;

public class UIMapperTextureRegion {

    // "restype:texture"
    // "restype:texture, x, y, width, height"

    public static void modify(UILoader loader, Object object, String value) {
        final TextureRegion textureRegion = (TextureRegion) object;

        final String[] args = value.split(",");
        for(int i = 0; i < args.length; i++)
            args[i] = args[i].trim();

        if(args.length != 1 && args.length != 5)
            throw new IllegalArgumentException("Invalid texture region format, allowed: [restype:texture, (restype:texture; x, y, width, height)]");

        textureRegion.setTexture(UIMapperTexture2D.load(loader, args[0]));

        if(args.length == 5) {
            final int x = Integer.parseInt(args[1]);
            final int y = Integer.parseInt(args[2]);
            final int width = Integer.parseInt(args[3]);
            final int height = Integer.parseInt(args[4]);
            textureRegion.set(x, y, width, height);
        }
    }

    public static TextureRegion load(UILoader loader, String value) {
        final TextureRegion textureRegion = new TextureRegion();
        modify(loader, textureRegion, value);
        return textureRegion;
    }

}
