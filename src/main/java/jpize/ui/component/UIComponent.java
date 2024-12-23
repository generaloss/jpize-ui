package jpize.ui.component;

import jpize.ui.callback.UICallbacks;
import jpize.ui.common.*;
import jpize.util.Disposable;
import jpize.util.color.Color;
import jpize.util.math.vector.Vec2f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class UIComponent implements Disposable {

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
    private final UICorners corners;

    private UIDrawable background;
    private Constraint borders_width;
    private final Color borderColor;

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
        this.corners = new UICorners();
        this.borders_width = Constraint.pixel(0);
        this.borderColor = new Color(0.1, 0.11, 0.15); //! default border color
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

    public <C extends UIComponent> C get(String ID) {
        for(UIComponent child: children){
            System.out.println(child.ID + " : " + ID + " : " + ID.equals(child.ID));
            if(ID.equals(child.ID))
                // noinspection unchecked
                return (C) child;}
        return null;
    }

    public <C extends UIComponent> C findByID(String ID) {
        final C found = this.get(ID);
        if(found == null)
            for(UIComponent child: children)
                return child.findByID(ID);
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

    public UICorners corners() {
        return corners;
    }


    public UIDrawable background() {
        return background;
    }

    public UIComponent setBackground(UIDrawable background) {
        this.background = background;
        return this;
    }


    public Constraint getBorderWidth() {
        return borders_width;
    }

    public UIComponent setBorderWidth(Constraint borderWidth) {
        this.borders_width = borderWidth;
        return this;
    }

    public Color borderColor() {
        return borderColor;
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


    @Override
    public void dispose() {
        if(background != null)
            background.dispose();
    }

    // override in component
    protected void onUpdate() { }
    protected void onRenderBegin(UIRenderer renderer) { }
    protected void onRenderEnd(UIRenderer renderer) { }

}
