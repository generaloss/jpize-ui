package jpize.ui.callback;

import jpize.ui.component.UIComponent;

@FunctionalInterface
public interface UIHoverCallback {

    void invoke(UIComponent component, boolean hovered);

}
