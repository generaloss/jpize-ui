package jpize.ui.loader.mapper;

import jpize.ui.loader.UILoader;
import jpize.util.math.vector.Vec2f;

public class UIMapperVec2f {

    // "1.0, 1.0"
    // "1.0"

    public static void modify(UILoader loader, Object object, String values) {
        final Vec2f vector = (Vec2f) object;

        final String[] components = values.split(",");
        for(int i = 0; i < components.length; i++)
            components[i] = components[i].trim();

        switch(components.length) {
            case 1 -> vector.set(Float.parseFloat(components[0]));
            case 2 -> vector.set(Float.parseFloat(components[0]), Float.parseFloat(components[1]));
            default -> throw new IllegalArgumentException("Invalid vector format, allowed: [xy, (x, y)]");
        }
    }

}
