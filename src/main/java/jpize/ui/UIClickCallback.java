package jpize.ui;

import jpize.glfw.input.MouseBtn;

@FunctionalInterface
public interface UIClickCallback {

    void invoke(UIComponent component, MouseBtn button);

}
