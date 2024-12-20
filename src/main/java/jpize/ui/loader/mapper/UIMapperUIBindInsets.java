package jpize.ui.loader.mapper;

import jpize.ui.common.UIBindInsets;
import jpize.ui.loader.UILoader;

public class UIMapperUIBindInsets {

    // "center"
    // "centerX"
    // "centerY"

    public static void modify(UILoader loader, Object object, String values) {
        final UIBindInsets bindInsets = (UIBindInsets) object;
        if(values.equalsIgnoreCase("center")){
            bindInsets.toCenter();
        }else if(values.equalsIgnoreCase("centerX")){
            bindInsets.toCenterX();
        }else if(values.equalsIgnoreCase("centerY")){
            bindInsets.toCenterY();
        }else{
            throw new IllegalArgumentException("Invalid bindings option '" + values + "', allowed: center");
        }
    }

}
