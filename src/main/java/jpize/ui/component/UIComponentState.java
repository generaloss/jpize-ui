package jpize.ui.component;

import jpize.app.Jpize;
import jpize.ui.common.Constraint;
import jpize.ui.common.UIBind;
import jpize.ui.common.UIDir;
import jpize.ui.palette.VBox;
import jpize.util.Insets;
import jpize.util.math.Intersector;
import jpize.util.math.vector.Vec2f;

public class UIComponentState { // calculations class

    private final UIComponent component;
    private boolean update_request;

    private final Vec2f parent_size;
    private final Vec2f parent_position;
    private final Insets parent_padding;
    private final Insets parent_margin;
    private final Vec2f parent_size_paddinged;

    private final Vec2f size;
    private final Vec2f position;
    private final Vec2f scroll_offset;
    private final Insets padding;
    private final Insets margin;
    private final Vec2f size_paddinged;
    private boolean update_content_size_request;
    private final Vec2f content_size;

    private final float[] round_corners;
    private float border_width;

    public UIComponentState(UIComponent component) {
        this.component = component;

        this.parent_size = new Vec2f();
        this.parent_position = new Vec2f();
        this.parent_padding = new Insets();
        this.parent_margin = new Insets();
        this.parent_size_paddinged = new Vec2f();

        this.size = new Vec2f();
        this.position = new Vec2f();
        this.scroll_offset = new Vec2f();
        this.padding = new Insets();
        this.margin = new Insets();
        this.size_paddinged = new Vec2f();
        this.content_size = new Vec2f();

        this.round_corners = new float[4];
    }

    public void setUpdateRequest(boolean updateRequest) {
        this.update_request = updateRequest;
        this.update_content_size_request = updateRequest;
    }

    public void tryUpdate(boolean force) {
        if(!force){
            if(!update_request)
                return;
            update_request = false;
        }
        // update
        this.updateParent();
        this.updateSize();
        this.updateMarginAndPadding();
        this.updatePosition();
        this.updateBackground();
    }

    private void updateParent() {
        final UIComponent parent = component.getParent();
        final boolean has_parent = (parent != null);

        if(has_parent) {
            parent_size.set(this.updateParentWidth(parent, Jpize.getWidth()), this.updateParentHeight(parent, Jpize.getHeight()));
            parent_size_paddinged.set(parent.getState().size_paddinged);
            parent_position.set(parent.getState().position());
            parent_padding.set(parent.getState().padding());
            parent_margin.set(parent.getState().margin());
        }else{
            parent_size.set(Jpize.getWidth(), Jpize.getHeight());
            parent_size_paddinged.set(parent_size);
            parent_position.set(0, 0);
            parent_padding.set(0, 0);
            parent_margin.set(0, 0);
        }
    }

    private float updateParentWidth(UIComponent parent, float defalut) {
        if(parent == null)
            return defalut;
        if(parent.size().width().isWrapContent())
            return this.updateParentWidth(parent.getParent(), defalut);
        return parent.getState().size().x;
    }

    private float updateParentHeight(UIComponent parent, float defalut) {
        if(parent == null)
            return defalut;
        if(parent.size().height().isWrapContent())
            return this.updateParentHeight(parent.getParent(), defalut);
        return parent.getState().size().y;
    }

    private void updateSize() {
        final Constraint width = component.size().width();
        final Constraint height = component.size().height();

        // width
        final boolean width_type_is_aspect = width.isAspect();
        if(width_type_is_aspect){
            this.updateSizeY(height);
            size.x = width.asNumber().getValue() * size.y;
        }else{
            size.x = width.getInPixels(true, () -> parent_size, size::getX, size::getY, () -> {
                this.calculateContentSize();
                return content_size.x;
            });
        }
        //! scale width
        // if(width.isNumber())
        //     size.x *= component.scale().x;// * component.getContext().scale().x;

        // height
        if(!width_type_is_aspect)
            this.updateSizeY(height);
    }

    private void updateSizeY(Constraint height) {
        if(height.isAspect()){
            size.y = height.asNumber().getValue() * size.x;
        }else{
            size.y = height.getInPixels(false, () -> parent_size, size::getX, size::getY, () -> {
                this.calculateContentSize();
                return content_size.y;
            });
        }

        //! scale height
        // if(height.isNumber())
        //     size.y *= component.scale().y;// * component.getContext().scale().y;
    }

    private void calculateContentSize() {
        // prevent unnecessary calculations
        if(!update_content_size_request)
            return;
        update_content_size_request = false;
        // { minX, maxX, minY, maxY }
        final float[] minmax = new float[]{
            Float.MAX_VALUE, -Float.MIN_VALUE,
            Float.MAX_VALUE, -Float.MIN_VALUE
        };
        // iterate children
        if(component instanceof VBox vbox){
            this.getMinmax(vbox.getChildren().iterator().next(), minmax);
            this.getMinmax(vbox.getLast(), minmax);
        }else{
            for(UIComponent child: component.getChildren())
                this.getMinmax(child, minmax);
        }
        // set content size
        content_size.set((minmax[1] - minmax[0]), (minmax[3] - minmax[2]));
        size.set((minmax[1] - minmax[0]), (minmax[3] - minmax[2]));
        size_paddinged.set((minmax[1] - minmax[0]), (minmax[3] - minmax[2]));
    }

    private void getMinmax(UIComponent component, float[] minmax) {
        // state
        final UIComponentState state = component.getState();
        state.tryUpdate(true);
        // min max
        final float minX = state.position.x;
        minmax[0] = Math.min(minmax[0], minX);
        minmax[1] = Math.max(minmax[1], minX + state.size.x);
        final float minY = state.position.y;
        minmax[2] = Math.min(minmax[2], minY);
        minmax[3] = Math.max(minmax[3], minY + state.size.y);
        // iterate children
        for(UIComponent child: component.getChildren())
            this.getMinmax(child, minmax);
    }


    private void updateMarginAndPadding() {
        padding.top    = component.padding().top()   .getInPixels(false, () -> parent_size, size::getX, size::getY, () -> 0F);
        padding.left   = component.padding().left()  .getInPixels(true , () -> parent_size, size::getX, size::getY, () -> 0F);
        padding.bottom = component.padding().bottom().getInPixels(false, () -> parent_size, size::getX, size::getY, () -> 0F);
        padding.right  = component.padding().right() .getInPixels(true , () -> parent_size, size::getX, size::getY, () -> 0F);

        margin.top    = component.margin().top()   .getInPixels(false, () -> parent_size, size::getX, size::getY, () -> 0F);
        margin.left   = component.margin().left()  .getInPixels(true , () -> parent_size, size::getX, size::getY, () -> 0F);
        margin.bottom = component.margin().bottom().getInPixels(false, () -> parent_size, size::getX, size::getY, () -> 0F);
        margin.right  = component.margin().right() .getInPixels(true , () -> parent_size, size::getX, size::getY, () -> 0F);

        size_paddinged.set(size).sub(padding.left, padding.bottom).sub(padding.right, padding.top);
    }


    private void updatePosition() {
        // bindings
        final boolean has_bind_top    = (!component.bindings().top()   .directory().isNone() && component.bindings().top()   .directory().isAxisY());
        final boolean has_bind_left   = (!component.bindings().left()  .directory().isNone() && component.bindings().left()  .directory().isAxisX());
        final boolean has_bind_bottom = (!component.bindings().bottom().directory().isNone() && component.bindings().bottom().directory().isAxisY());
        final boolean has_bind_right  = (!component.bindings().right() .directory().isNone() && component.bindings().right() .directory().isAxisX());

        final float bind_top    = (!has_bind_top    ? 0F : this.updateBind(UIDir.TOP   , component.bindings().top()   )) - size.y;
        final float bind_left   = (!has_bind_left   ? 0F : this.updateBind(UIDir.LEFT  , component.bindings().left()  ));
        final float bind_bottom = (!has_bind_bottom ? 0F : this.updateBind(UIDir.BOTTOM, component.bindings().bottom()));
        final float bind_right  = (!has_bind_right  ? 0F : this.updateBind(UIDir.RIGHT , component.bindings().right() )) - size.x;

        final Vec2f bias = component.bindings().bias();
        // x
        if(has_bind_left && has_bind_right) {
            position.x = (bind_left * bias.x + bind_right * (1F - bias.x));
        }else if(has_bind_left) {
            position.x = bind_left;
        }else if(has_bind_right) {
            position.x = bind_right;
        }else{
            position.x = parent_position.x + parent_margin.left;
        }
        // y
        if(has_bind_bottom && has_bind_top) {
            position.y = bind_top * bias.y + bind_bottom * (1F - bias.y);
        }else if(has_bind_bottom) {
            position.y = bind_bottom;
        }else if(has_bind_top) {
            position.y = bind_top;
        }else{
            position.y = parent_position.y + parent_margin.bottom;
        }

        position.add(scroll_offset);
    }

    private float updateBind(UIDir from, UIBind bind) {
        // data
        final Vec2f size;
        final Vec2f position;
        final Insets padding;
        final Insets margin;

        final UIComponent component = bind.component();
        if(component == null){
            size = parent_size;
            position = parent_position;
            padding = parent_padding;
            margin = parent_margin;
        }else{
            final UIComponentState state = component.getState();
            state.tryUpdate(false);

            size = state.size();
            position = state.position();
            padding = state.padding();
            margin = state.margin();
        }

        // important vars
        final UIDir to = bind.directory();
        final boolean internal = (from == to);

        // xy
        if(to.isTop()){
            final float y = position.y + size.y;
            final float correction = (internal ?
                -(padding.top + this.margin.top) :
                 (margin.bottom + this.margin.top)
            );
            return (y + correction);
        }
        if(to.isLeft()) {
            final float x = position.x;
            final float correction = (internal ?
                 (padding.left + this.margin.left) :
                -(margin.right + this.margin.left)
            );
            return (x + correction);
        }
        if(to.isBottom()) {
            final float y = position.y;
            final float correction = (internal ?
                 (padding.bottom + this.margin.bottom) :
                -(margin.top + this.margin.bottom)
            );
            return (y + correction);
        }
        if(to.isRight()){
            final float x = position.x + size.x;
            final float correction = (internal ?
                -(padding.right + this.margin.right) :
                 (margin.left + this.margin.right)
            );
            return (x + correction);
        }

        // impossible return statement
        return 0F;
    }


    private void updateBackground() {
        // corners
        final Constraint[] constraints = component.corners().constraints();
        for(int i = 0; i < 4; i++){
            round_corners[i] = constraints[i].getInPixels(false,
                () -> size,
                size::getX, size::getY,
                () -> (size.minComp() * 0.5F)
            );
        }

        // border
        border_width = component.getBorderWidth().getInPixels(false,
            () -> size,
            size::getX, size::getY,
            () -> (size.minComp() * 0.5F)
        );
    }


    public boolean isPointOnComponent(float x, float y) {
        final Vec2f position = component.getState().position();
        final Vec2f size = component.getState().size();
        return Intersector.isPointOnRect(x, y, position.x, position.y, size.x, size.y);
    }

    public boolean isPointOnComponent(Vec2f point) {
        return this.isPointOnComponent(point.x, point.y);
    }


    public Vec2f size() {
        return size;
    }

    public Vec2f position() {
        return position;
    }

    public Insets padding() {
        return padding;
    }

    public Insets margin() {
        return margin;
    }

    public float[] roundCorners() {
        return round_corners;
    }

    public float borderWidth() {
        return border_width;
    }

    public Vec2f scrollOffset() {
        return scroll_offset;
    }

}
