package jpize.ui.component;

import jpize.ui.callback.UICallbacks;
import jpize.ui.common.UIBindInsets;
import jpize.ui.common.UIDimensions;
import jpize.ui.common.UIInsets;
import jpize.util.math.vector.Vec2f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class UIComponent {

    private final UIContext context;
    private UIComponent parent;
    private final List<UIComponent> children;

    private String ID;
    private final UIComponentState state;
    private final UIDimensions size;
    private final Vec2f scale;
    private final UIBindInsets bindings;
    private final UIInsets margin;
    private final UIInsets padding;

    private final UIBackground background;
    private boolean visibility;
    private boolean hoverable;

    private final UICallbacks callbacks;

    public UIComponent(UIContext context) {
        this.context = context;
        this.children = new ArrayList<>();
        this.state = new UIComponentState(this);
        this.size = new UIDimensions();
        this.scale = new Vec2f(1F);
        this.bindings = new UIBindInsets();
        this.margin = new UIInsets();
        this.padding = new UIInsets();
        this.background = new UIBackground();
        this.visibility = true;
        this.hoverable = true;
        this.callbacks = new UICallbacks(this);
        //! this.state.setUpdateRequest(true);
    }

    public UIContext getContext() {
        return context;
    }


    public UIComponent getParent() {
        return parent;
    }


    public Iterable<UIComponent> getChildren() {
        return children;
    }

    public UIComponent add(UIComponent child) {
        if(child == null)
            return this;
        child.parent = this;
        children.add(child);
        return this;
    }

    public UIComponent remove(UIComponent child) {
        child.parent = null;
        children.remove(child);
        return this;
    }

    public UIComponent clear() {
        children.clear();
        return this;
    }

    private void foreach(UIComponent component, Consumer<UIComponent> action) {
        action.accept(component);
        for(UIComponent child: component.getChildren())
            this.foreach(child, action);
    }

    public void foreach(Consumer<UIComponent> action) {
        this.foreach(this, action);
    }

    public <T extends UIComponent> T get(String ID) {
        for(UIComponent child: children)
            if(ID.equals(child.ID))
                // noinspection unchecked
                return (T) child;
        return null;
    }

    public <T extends UIComponent> T findByID(String ID) {
        T found = this.get(ID);
        if(found == null)
            for(UIComponent child: children)
                child.findByID(ID);
        return found;
    }


    public String getID() {
        return ID;
    }

    public UIComponent setID(String ID) {
        this.ID = ID;
        return this;
    }


    public UIComponentState getState() {
        return state;
    }

    public Vec2f getPosition() {
        return state.position();
    }

    public Vec2f getSize() {
        return state.size();
    }


    public UIDimensions size() {
        return size;
    }

    public Vec2f scale() {
        return scale;
    }

    public UIBindInsets bindings() {
        return bindings;
    }

    public UIInsets margin() {
        return margin;
    }

    public UIInsets padding() {
        return padding;
    }

    public UIBackground background() {
        return background;
    }


    public boolean getVisibility() {
        return visibility;
    }

    public UIComponent setVisibility(boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public UIComponent hide() {
        return this.setVisibility(false);
    }

    public UIComponent show() {
        return this.setVisibility(true);
    }


    public boolean getHoverable() {
        return hoverable;
    }

    public UIComponent setHoverable(boolean hoverable) {
        this.hoverable = hoverable;
        return this;
    }


    public UICallbacks callbacks() {
        return callbacks;
    }


    // override in component
    protected void onUpdate() { }
    protected void onRenderBegin(UIRenderer renderer) { }
    protected void onRenderEnd(UIRenderer renderer) { }

}
