package jpize.ui.loader.mapper;

import jpize.ui.common.Constraint;
import jpize.ui.common.UIDrawableNinePatch;
import jpize.ui.loader.UILoader;
import jpize.util.ninepatch.NinePatch;
import jpize.util.ninepatch.StretchMode;

public class UIMapperUIDrawableNinePatch {

    // "ninepatch(restype:texture, scale_x, scale_y, stretch_mode_x, stretch_mode_y; color)"
    // "ninepatch(restype:texture, scale_x, scale_y; color)"
    // "ninepatch(restype:texture; color)"
    // "ninepatch(restype:texture)"

    private static final String EX_MESSAGE = "Invalid ninepatch drawable format, allowed: [ninepatch(restype:texture), ninepatch(restype:texture; color), ninepatch(restype:texture, scale_x, scale_y; color), ninepatch(restype:texture, scale_x, scale_y, stretch_mode_x, stretch_mode_y; color)]";
    
    public static void modify(UILoader loader, Object object, String value) {
        final UIDrawableNinePatch drawableImage = (UIDrawableNinePatch) object;

        if(!value.startsWith("ninepatch(") || !value.endsWith(")"))
            throw new IllegalArgumentException(EX_MESSAGE);

        value = value.substring(10, value.length() - 1);

        final String[] args = value.split(";");
        if(args.length > 2)
            throw new IllegalArgumentException(EX_MESSAGE);
        for(int i = 0; i < args.length; i++)
            args[i] = args[i].trim();

        final String[] ninepatchArgs = args[0].split(",");
        if(ninepatchArgs.length < 1 || ninepatchArgs.length == 2 || ninepatchArgs.length == 4 || ninepatchArgs.length > 5)
            throw new IllegalArgumentException(EX_MESSAGE);
        for(int i = 0; i < ninepatchArgs.length; i++)
            ninepatchArgs[i] = ninepatchArgs[i].trim();

        final NinePatch ninePatch = new NinePatch();
        ninePatch.load(UIMapperTexture2D.load(loader, ninepatchArgs[0]));
        drawableImage.setNinepatch(ninePatch);

        if(args.length == 2)
            UIMapperColor.modify(loader, drawableImage.color(), args[1]);

        if(ninepatchArgs.length >= 3){
            final Constraint scaleX = Constraint.parseConstraint(ninepatchArgs[1]);
            final Constraint scaleY = Constraint.parseConstraint(ninepatchArgs[2]);
            drawableImage.scale().set(scaleX, scaleY);
        }
        if(ninepatchArgs.length == 5){
            final StretchMode stretchModeX = StretchMode.valueOf(ninepatchArgs[3].toUpperCase());
            final StretchMode stretchModeY = StretchMode.valueOf(ninepatchArgs[4].toUpperCase());
            ninePatch.setStretchMode(stretchModeX, stretchModeY);
        }
    }

    public static UIDrawableNinePatch load(UILoader loader, String value) {
        final UIDrawableNinePatch drawableNinePatch = new UIDrawableNinePatch();
        modify(loader, drawableNinePatch, value);
        return drawableNinePatch;
    }

}
