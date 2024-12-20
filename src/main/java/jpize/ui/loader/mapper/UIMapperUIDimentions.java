package jpize.ui.loader.mapper;

import jpize.ui.common.Constraint;
import jpize.ui.common.UIDimensions;
import jpize.ui.loader.UILoader;

public class UIMapperUIDimentions {

    // "xy"
    // "x, y"

    public static void modify(UILoader loader, Object object, String values) {
        final UIDimensions dimensions = (UIDimensions) object;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch(arguments.length) {
            case 1 -> dimensions.set(Constraint.parseConstraint(arguments[0]));
            case 2 -> dimensions.set(Constraint.parseConstraint(arguments[0]), Constraint.parseConstraint(arguments[1]));
            default -> throw new IllegalArgumentException("Invalid dimensions format, allowed: [xy, (x, y)]");
        }
    }

}
