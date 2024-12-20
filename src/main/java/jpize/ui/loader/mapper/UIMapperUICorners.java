package jpize.ui.loader.mapper;

import jpize.ui.common.Constraint;
import jpize.ui.common.UICorners;
import jpize.ui.loader.UILoader;

public class UIMapperUICorners {

    // "constraint"
    // "constraint, constraint, constraint, constraint"

    public static void modify(UILoader loader, Object object, String value){
        final UICorners corners = (UICorners) object;

        final String[] arguments = value.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch(arguments.length) {
            case 1 -> corners.round(Constraint.parseConstraint(arguments[0]));
            case 4 -> corners.round(
                    Constraint.parseConstraint(arguments[0]),
                    Constraint.parseConstraint(arguments[1]),
                    Constraint.parseConstraint(arguments[2]),
                    Constraint.parseConstraint(arguments[3])
            );
        }
    }

}
