package jpize.ui;

import jpize.app.Jpize;
import jpize.glfw.input.Key;
import jpize.util.math.vector.Vec2f;

public class ScrollView extends UIComponent {

    private final Vec2f scroll, scrollQueue, prevScrollSpace, prevSize;
    private float step;
    private float lerpTime;

    public ScrollView(UIContext context) {
        super(context);
        this.scroll = new Vec2f();
        this.scrollQueue = new Vec2f();
        this.prevScrollSpace = new Vec2f();
        this.prevSize = new Vec2f();
        this.step = 100F;
    }


    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
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
        final UIComponent scrollable = getChildren().iterator().next();
        if(scrollable == null)
            return;

        final UIComponentState state = super.getState();
        final Vec2f scrollSpace = new Vec2f(scrollable.getState().size()).sub(state.size());
        scrollSpace.setMaxComps(scrollSpace, 0F);

        if(scrollSpace.x != 0) scroll.x *= prevScrollSpace.x / scrollSpace.x;
        if(scrollSpace.y != 0) scroll.y *= prevScrollSpace.y / scrollSpace.y;
        if(prevSize.x != 0) scroll.x *= state.size().x / prevSize.x;
        if(prevSize.y != 0) scroll.y *= state.size().y / prevSize.y;
        prevScrollSpace.set(scrollSpace);
        prevSize.set(state.size());

        // scroll steps
        if(state.isPointOnComponent(Jpize.input().getCursorPos())){
            final boolean for_x = Key.pressedAny(Key.LSHIFT, Key.RSHIFT);
            final Vec2f scale = super.getContext().scale();
            float steps = Jpize.getScroll() * step;// / (for_x ? scale.x : scale.y);
            if(steps != 0){
                if(for_x){
                    if(scrollSpace.x != 0) steps /= scrollSpace.x;
                    scrollQueue.x -= steps;
                }else{
                    if(scrollSpace.y != 0) steps /= scrollSpace.y;
                    scrollQueue.y -= steps;
                }
            }
        }

        // unqueue and interpolate
        if(!scrollQueue.isZero()){
            final Vec2f step = scrollQueue.copy()
                .nor()
                .mul(Jpize.getDT() * scrollQueue.len() * 10);

            scroll.add(step);
            scrollQueue.sub(step).zeroThatLess(step);
        }

        //System.out.println(scroll);

        // clamp
        if(scroll.x < 0){
            scroll.x = 0;
            scrollQueue.x = 0;
        }else{
            if(scroll.x > -1F){
                scroll.x = -1F;
                scrollQueue.x = 0;
            }
        }
        if(scroll.y < 0){
            scroll.y = 0;
            scrollQueue.y = 0;
        }else{
            if(scroll.y > 1F){
                scroll.y = 1F;
                scrollQueue.y = 0;
            }
        }

        // set scroll offset
        scrollable.getState().scrollOffset().set(-scroll.x, scroll.y).mul(scrollSpace);
    }

    @Override
    protected void onRenderBegin(UIRenderer renderer) {
        final UIComponentState state = super.getState();
        renderer.batch().getScissor().begin(super.hashCode(),
            state.position().x, state.position().y, state.size().x, state.size().y);

        // hide
        final UIComponent scrollable = getChildren().iterator().next();
        if(scrollable != null){
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
    }

    @Override
    protected void onRenderEnd(UIRenderer renderer) {
        renderer.batch().getScissor().end(super.hashCode());
    }


}
