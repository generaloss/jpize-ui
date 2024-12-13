package jpize.ui.palette;

import jpize.app.Jpize;
import jpize.glfw.input.Key;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentState;
import jpize.ui.component.UIContext;
import jpize.ui.component.UIRenderer;
import jpize.ui.common.UIDir;
import jpize.util.math.vector.Vec2f;

public class ScrollView extends UIComponent {

    private final Vec2f step;
    private final Vec2f scroll_position, scroll_point;
    private final Vec2f prev_view_size, prev_scrollable_size;
    private float lerpTime;

    public ScrollView(UIContext context) {
        super(context);
        this.step = new Vec2f(100F);
        this.scroll_point = new Vec2f();
        this.scroll_position = new Vec2f();
        this.prev_view_size = new Vec2f();
        this.prev_scrollable_size = new Vec2f();
    }


    public Vec2f step() {
        return step;
    }


    @Override
    public ScrollView add(UIComponent component) {
        super.clear();
        super.add(component);
        component.bindings().top().set(UIDir.TOP, this);
        return this;
    }

    @Override
    protected void onUpdate() {
        // scrollable component
        if(!getChildren().iterator().hasNext())
            return;
        final UIComponent scrollable_component = getChildren().iterator().next();

        final UIComponentState view_state = super.getState();
        final UIComponentState scrollable_state = scrollable_component.getState();
        final Vec2f view_size = view_state.size();
        final Vec2f scrollable_size = scrollable_state.size();

        // scroll
        if(view_state.isPointOnComponent(Jpize.input().getCursorPos(new Vec2f()))){
            final boolean for_x = Key.pressedAny(Key.LSHIFT, Key.RSHIFT);
            // final Vec2f scale = super.getContext().scale();
            float steps = (for_x ? step.x : step.y) * Jpize.getScroll();// / (for_x ? scale.x : scale.y);
            if(steps != 0){
                if(for_x){
                    scroll_point.x -= steps;
                }else{
                    scroll_point.y -= steps;
                }
            }
        }

        // correct position
        if(!prev_view_size.equals(view_size) && !scrollable_size.isZero()){
            scroll_point.mul(prev_scrollable_size).div(scrollable_size);
            scroll_position.set(scroll_point);
        }

        prev_view_size.set(view_size);
        prev_scrollable_size.set(scrollable_size);

        // interpolate
        final Vec2f step = scroll_point.copy().sub(scroll_position);
        if(!step.isZero()){
            scroll_position.add(step.mul(Jpize.getDeltaTime() * 10));
        }

        // set
        scrollable_state.scrollOffset().set(-scroll_position.x, scroll_position.y);
    }

    // @Override
    // protected void onUpdate() {
    //     // scrollable component
    //     final UIComponent scrollable = getChildren().iterator().next();
    //     if(scrollable == null)
    //         return;

    //     final UIComponentState state = super.getState();
    //     final Vec2f scrollSpace = new Vec2f(scrollable.getState().size()).sub(state.size());
    //     scrollSpace.setMaxComps(scrollSpace, 0F);

    //     if(scrollSpace.x != 0 && prevSize.x != 0)
    //         scroll.x *= (prevScrollSpace.x / scrollSpace.x) * (state.size().x / prevSize.x);
    //     if(scrollSpace.y != 0 && prevSize.y != 0)
    //         scroll.y *= (prevScrollSpace.y / scrollSpace.y) * (state.size().y / prevSize.y);

    //     prevScrollSpace.set(scrollSpace);
    //     prevSize.set(state.size());

    //     // scroll steps
    //     if(state.isPointOnComponent(Jpize.input().getCursorPos(new Vec2f()))){
    //         final boolean for_x = Key.pressedAny(Key.LSHIFT, Key.RSHIFT);
    //         final Vec2f scale = super.getContext().scale();
    //         float steps = Jpize.getScroll() * step;// / (for_x ? scale.x : scale.y);
    //         if(steps != 0){
    //             if(for_x){
    //                 if(scrollSpace.x != 0) steps /= scrollSpace.x;
    //                 scrollQueue.x -= steps;
    //             }else{
    //                 if(scrollSpace.y != 0) steps /= scrollSpace.y;
    //                 scrollQueue.y -= steps;
    //             }
    //         }
    //     }

    //     // unqueue and interpolate
    //     if(!scrollQueue.isZero()){
    //         final Vec2f step = scrollQueue.copy()
    //             .nor()
    //             .mul(Jpize.getDT() * scrollQueue.len() * 10);

    //         scroll.add(step);
    //         scrollQueue.sub(step).zeroCompsThatLess(step);
    //     }

    //     // clamp
    //     if(scroll.x < 0){
    //         scroll.x = 0;
    //         scrollQueue.x = 0;
    //     }else{
    //         if(scroll.x > -1F){
    //             scroll.x = -1F;
    //             scrollQueue.x = 0;
    //         }
    //     }
    //     if(scroll.y < 0){
    //         scroll.y = 0;
    //         scrollQueue.y = 0;
    //     }else{
    //         if(scroll.y > 1F){
    //             scroll.y = 1F;
    //             scrollQueue.y = 0;
    //         }
    //     }

    //     // set scroll offset
    //     scrollable.getState().scrollOffset().set(-scroll.x, scroll.y).mul(scrollSpace);
    // }

    @Override
    protected void onRenderBegin(UIRenderer renderer) {
        if(!getChildren().iterator().hasNext())
            return;

        final UIComponentState state = super.getState();

        renderer.batch().render();
        renderer.scissor().put(this, state.position().x, state.position().y, state.size().x, state.size().y);
        renderer.scissor().apply();

        // hide
        final UIComponent scrollable = getChildren().iterator().next();
        scrollable.foreach((component) -> {
            final UIComponentState comp_state = component.getState();
            component.setVisibility(
                (comp_state.position().y + comp_state.size().y) > state.position().y &&
                (comp_state.position().y) < (state.position().y + state.size().y) &&
                (comp_state.position().x + comp_state.size().x) > state.position().x &&
                (comp_state.position().x) < (state.position().x + state.size().x)
            );
        });
    }

    @Override
    protected void onRenderEnd(UIRenderer renderer) {
        renderer.batch().render();
        renderer.scissor().remove(this);
        renderer.scissor().apply();
    }


}
