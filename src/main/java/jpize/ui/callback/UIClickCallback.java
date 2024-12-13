package jpize.ui.callback;

import jpize.glfw.input.MouseBtn;
import jpize.ui.component.UIComponent;

@FunctionalInterface
public interface UIClickCallback {

    void invoke(UIComponent component, MouseBtn button);

}
