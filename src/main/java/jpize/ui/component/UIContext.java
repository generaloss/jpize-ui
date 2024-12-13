package jpize.ui.component;

import jpize.app.Jpize;
import jpize.glfw.callback.GlfwMouseButtonCallback;
import jpize.glfw.callback.GlfwWindowSizeCallback;
import jpize.glfw.input.GlfwAction;
import jpize.glfw.input.GlfwMods;
import jpize.glfw.input.MouseBtn;
import jpize.glfw.window.GlfwWindow;
import jpize.util.math.vector.Vec2f;

import java.util.ArrayList;
import java.util.List;

public class UIContext {

    private boolean active;
    private UIComponent root;
    private final UIRenderer renderer;
    private final Vec2f scale;
    private UIComponent hovered, hold;
    private MouseBtn holdMouseButton;

    private final GlfwWindowSizeCallback onResize = (this::onResize);
    private final GlfwMouseButtonCallback onClick = (this::onClick);

    private final List<Runnable> enableCallbacks, disableCallbacks;

    public UIContext() {
        this.renderer = new UIRenderer();
        this.scale = Jpize.window().getContentScale();

        this.enableCallbacks = new ArrayList<>();
        this.disableCallbacks = new ArrayList<>();
    }

    public UIComponent getRoot() {
        return root;
    }

    public UIContext setRoot(UIComponent root) {
        this.root = root;
        return this;
    }

    public UIRenderer getRenderer() {
        return renderer;
    }

    public UIComponent getHovered() {
        return hovered;
    }

    public UIComponent getHold() {
        return hold;
    }

    public MouseBtn getHoldMouseButton() {
        return holdMouseButton;
    }

    public Vec2f scale() {
        return scale;
    }


    public boolean isActive() {
        return active;
    }

    public UIContext setActive(boolean active) {
        if(active){
            return this.enable();
        }else{
            return this.disable();
        }
    }

    public UIContext enable() {
        if(active) return this;
        active = true;
        for(Runnable callback: enableCallbacks)
            callback.run();
        Jpize.callbacks().addWindowSizeCallback(onResize);
        Jpize.callbacks().addMouseButtonCallback(onClick);
        this.onResize(null, Jpize.getWidth(), Jpize.getHeight());
        return this;
    }

    public UIContext disable() {
        if(!active) return this;
        active = false;
        for(Runnable callback: disableCallbacks)
            callback.run();
        Jpize.callbacks().removeWindowSizeCallback(onResize);
        Jpize.callbacks().removeMouseButtonCallback(onClick);
        this.onClick(null, holdMouseButton, GlfwAction.RELEASE, null);
        return this;
    }

    public UIContext addOnEnable(Runnable callback) {
        this.enableCallbacks.add(callback);
        if(active)
            callback.run();
        return this;
    }

    public UIContext addOnDisable(Runnable callback) {
        this.disableCallbacks.add(callback);
        return this;
    }


    public <T extends UIComponent> T findByID(String ID) {
        if(ID.equals(root.getID()))
            // noinspection unchecked
            return (T) root;
        return root.findByID(ID);
    }

    public void render() {
        if(!active)
            return;
        // require update
        root.foreach((component) -> component.getState().setUpdateRequest(true));
        // update
        root.foreach((component) -> {
            component.getState().tryUpdate(false);
            component.onUpdate();
        });
        this.updateHovered();
        this.updateHold();
        // render
        renderer.setup();
        renderer.render(root);
        renderer.flush();
    }


    private void onResize(GlfwWindow window, int width, int height) {
        renderer.resize(width, height);
    }

    private void updateHovered() {
        final UIComponent hovered = getHovered(root, Jpize.getX(), Jpize.getY());
        if(this.hovered == hovered)
            return;

        if(this.hovered != null)
            this.hovered.callbacks().invokeOnHover(false);

        this.hovered = hovered;
        if(hovered != null)
            hovered.callbacks().invokeOnHover(true);
    }

    private UIComponent getHovered(UIComponent component, float x, float y) {
        for(UIComponent child: component.getChildren()){
            final UIComponent hovered = this.getHovered(child, x, y);
            if(hovered != null)
                return hovered;
        }
        if(component.getHoverable() && component.getState().isPointOnComponent(x, y))
            return component;
        return null;
    }


    private void onClick(GlfwWindow window, MouseBtn button, GlfwAction action, GlfwMods mods) {
        if(action.isDown()) {
            if(holdMouseButton != null || hovered == null)
                return;

            holdMouseButton = button;
            hold = hovered;
            hold.callbacks().invokeOnClick(button);

        }else if(action.isRelease()) {
            if(holdMouseButton == null || button != holdMouseButton || hold == null)
                return;

            hold.callbacks().invokeOnRelease(button, (hovered == hold));
            hold = null;
            holdMouseButton = null;
        }
    }

    private void updateHold() {
        if(holdMouseButton != null && holdMouseButton.pressed())
            hold.callbacks().invokeOnHold(holdMouseButton);
    }

}
