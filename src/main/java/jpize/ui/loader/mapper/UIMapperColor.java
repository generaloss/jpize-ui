package jpize.ui.loader.mapper;

import jpize.ui.loader.UILoader;
import jpize.util.color.Color;

public class UIMapperColor {

    // "#fff"               - rgb (short)
    // "#ffff"              - rgba (short)
    // "#ffffff"            - rgb
    // "#ffffffff"          - rgba
    // "1.0, 1.0, 1.0"      - rgb
    // "1.0, 1.0, 1.0, 1.0" - rgba
    // "1.0, 1.0"           - grayscale, alpha
    // "1.0"                - alpha

    public static void modify(UILoader loader, Object object, String value) {
        final Color color = (Color) object;

        if(value.startsWith("#")) {
            color.set(value);
        }else{
            final String[] channels = value.split(",");
            for(int i = 0; i < channels.length; i++)
                channels[i] = channels[i].trim();

            switch(channels.length) {
                case 1 -> color.setAlpha(Float.parseFloat(channels[0]));
                case 2 -> {
                    final float grayscale = Float.parseFloat(channels[0]);
                    final float alpha = Float.parseFloat(channels[1]);
                    color.set(grayscale, grayscale, grayscale, alpha);
                }
                case 3 -> {
                    final float red = Float.parseFloat(channels[0]);
                    final float green = Float.parseFloat(channels[1]);
                    final float blue = Float.parseFloat(channels[2]);
                    color.set(red, green, blue);
                }
                case 4 -> {
                    final float red = Float.parseFloat(channels[0]);
                    final float green = Float.parseFloat(channels[1]);
                    final float blue = Float.parseFloat(channels[2]);
                    final float alpha = Float.parseFloat(channels[3]);
                    color.set(red, green, blue, alpha);
                }
                default -> throw new IllegalArgumentException("Invalid color format, allowed: [(r, g, b, a), (r, g, b), (grayscale, alpha), alpha]");
            }
        }
    }

    public static Color load(UILoader loader, String value) {
        final Color color = new Color();
        modify(loader, color, value);
        return color;
    }

}
