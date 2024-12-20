package jpize.ui.loader.mapper;

import jpize.ui.common.UIDrawableImage;
import jpize.ui.loader.UILoader;

public class UIMapperUIDrawableImage {

    // "image(texture_region)"
    // "image(texture_region; color)"

    private static final String EX_MESSAGE = "Invalid image drawable format, allowed: [image(texture_region), image(texture_region; color)]";

    public static void modify(UILoader loader, Object object, String value) {
        final UIDrawableImage drawableImage = (UIDrawableImage) object;

        if(!value.startsWith("image(") || !value.endsWith(")"))
            throw new IllegalArgumentException(EX_MESSAGE);

        value = value.substring(6, value.length() - 1);

        final String[] args = value.split(";");
        for(int i = 0; i < args.length; i++)
            args[i] = args[i].trim();

        drawableImage.setImage(UIMapperTextureRegion.load(loader, args[0]));
        if(args.length > 1)
            UIMapperColor.modify(loader, drawableImage.color(), args[1]);
    }

    public static UIDrawableImage load(UILoader loader, String value) {
        final UIDrawableImage drawableImage = new UIDrawableImage();
        modify(loader, drawableImage, value);
        return drawableImage;
    }

}
