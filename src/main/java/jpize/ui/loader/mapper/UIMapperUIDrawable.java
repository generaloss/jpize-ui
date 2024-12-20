package jpize.ui.loader.mapper;

import jpize.ui.common.UIDrawable;
import jpize.ui.loader.UILoader;

public class UIMapperUIDrawable {

    // "image(...)"
    // "ninepatch(...)"

    public static UIDrawable load(UILoader loader, String values) {
        if(values.startsWith("image("))
            return UIMapperUIDrawableImage.load(loader, values);

        if(values.startsWith("ninepatch("))
            return UIMapperUIDrawableNinePatch.load(loader, values);

        throw new IllegalArgumentException("Invalid drawable format, allowed: [image(...), ninepatch(...)]");
    }

}
