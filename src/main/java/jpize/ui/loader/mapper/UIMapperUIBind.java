package jpize.ui.loader.mapper;

import jpize.ui.common.UIBind;
import jpize.ui.common.UIDir;
import jpize.ui.component.UIComponent;
import jpize.ui.loader.UILoader;

public class UIMapperUIBind {

    // "component_id, directory"

    public static void modify(UILoader loader, Object object, String values) {
        final UIBind bind = (UIBind) object;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        if(arguments.length > 2)
            throw new IllegalArgumentException("Invalid binding format, allowed: [directory, (component_id, directory)]");

        if(arguments.length == 2){
            final UIComponent component = loader.context().findByID(arguments[0]);
            if(component == null)
                throw new IllegalArgumentException("Component '" + arguments[0] + "' not found");
            bind.setComponent(component);
        }

        final int dirArgIndex = (arguments.length - 1);
        try{
            final UIDir directory = UIDir.valueOf(arguments[dirArgIndex].toUpperCase());
            bind.setDirectory(directory);
        }catch(Exception ignored){
            throw new IllegalArgumentException("Invalid directory '" + arguments[dirArgIndex] + "', allowed: [none, top, left, bottom, right]");
        }
    }

}
