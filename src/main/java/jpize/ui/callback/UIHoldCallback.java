package jpize.ui.callback;

import jpize.glfw.input.MouseBtn;
import jpize.ui.component.UIComponent;

@FunctionalInterface
public interface UIHoldCallback {

    void invoke(UIComponent component, MouseBtn button);

}
