package jpize.ui;

import jpize.glfw.input.MouseBtn;

@FunctionalInterface
public interface UIReleaseCallback {

    void invoke(UIComponent component, MouseBtn button, boolean onComponent);

}
