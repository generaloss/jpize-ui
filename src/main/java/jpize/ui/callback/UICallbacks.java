package jpize.ui.callback;

import jpize.glfw.input.MouseBtn;
import jpize.ui.component.UIComponent;

import java.util.ArrayList;
import java.util.List;

public class UICallbacks {

    private final UIComponent component;
    private final List<UIHoverCallback> hover;
    private final List<UIClickCallback> click;
    private final List<UIHoldCallback> hold;
    private final List<UIReleaseCallback> release;

    public UICallbacks(UIComponent component) {
        this.component = component;
        this.hover = new ArrayList<>();
        this.click = new ArrayList<>();
        this.hold = new ArrayList<>();
        this.release = new ArrayList<>();
    }


    public void addOnHover(UIHoverCallback hover) {
        this.hover.add(hover);
    }

    public void removeOnHover(UIHoverCallback hover) {
        this.hover.remove(hover);
    }

    public void invokeOnHover(boolean value) {
        for(UIHoverCallback callback: hover)
            callback.invoke(component, value);
    }


    public void addOnClick(UIClickCallback hover) {
        this.click.add(hover);
    }

    public void removeOnClick(UIClickCallback hover) {
        this.click.remove(hover);
    }

    public void invokeOnClick(MouseBtn button) {
        for(UIClickCallback callback: click)
            callback.invoke(component, button);
    }


    public void addOnHold(UIHoldCallback hover) {
        this.hold.add(hover);
    }

    public void removeOnHold(UIHoldCallback hover) {
        this.hold.remove(hover);
    }

    public void invokeOnHold(MouseBtn button) {
        for(UIHoldCallback callback: hold)
            callback.invoke(component, button);
    }


    public void addOnRelease(UIReleaseCallback hover) {
        this.release.add(hover);
    }

    public void removeOnRelease(UIReleaseCallback hover) {
        this.release.remove(hover);
    }

    public void invokeOnRelease(MouseBtn button, boolean onComponent) {
        for(UIReleaseCallback callback: release)
            callback.invoke(component, button, onComponent);
    }

}
