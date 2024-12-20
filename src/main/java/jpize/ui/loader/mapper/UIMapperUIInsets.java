package jpize.ui.loader.mapper;

import jpize.ui.common.Constraint;
import jpize.ui.common.UIInsets;
import jpize.ui.loader.UILoader;

public class UIMapperUIInsets {

    // "constraint"
    // "constraint, constraint"
    // "constraint, constraint, constraint, constraint"

    public static void modify(UILoader loader, Object object, String values) {
        final UIInsets insets = (UIInsets) object;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch(arguments.length) {
            case 1 -> insets.set(Constraint.parseConstraint(arguments[0]));
            case 2 -> insets.set(Constraint.parseConstraint(arguments[0]), Constraint.parseConstraint(arguments[1]));
            case 4 -> insets.set(
                    Constraint.parseConstraint(arguments[0]),
                    Constraint.parseConstraint(arguments[1]),
                    Constraint.parseConstraint(arguments[0]),
                    Constraint.parseConstraint(arguments[1])
            );
            default -> throw new IllegalArgumentException("Invalid insets format, allowed: [all, (vertival)]");
        }
    }

}
