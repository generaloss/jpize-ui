package jpize.ui;

import jpize.glfw.input.MouseBtn;

@FunctionalInterface
public interface UIHoldCallback {

    void invoke(UIComponent component, MouseBtn button);

}
