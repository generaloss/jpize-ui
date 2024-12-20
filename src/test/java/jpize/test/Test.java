package jpize.test;

import jpize.app.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.gl.texture.Texture2D;
import jpize.glfw.Glfw;
import jpize.glfw.init.GlfwPlatform;
import jpize.glfw.input.Key;
import jpize.ui.common.Constraint;
import jpize.ui.common.UIDir;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIContext;
import jpize.ui.loader.UILoader;
import jpize.ui.palette.ImageView;
import jpize.ui.palette.VBox;
import jpize.util.math.Mathc;

public class Test extends JpizeApplication {

    private final UIContext ctx = new UILoader()
            .load("/test.xml")
            .createContext()
            .enable();

    @Override
    public void init() {
        Gl.clearColor(0.3, 0.3, 0.3);

        final ImageView root = ctx.findByID("root");
        final VBox buttonLayout = ctx.findByID("button_layout");

        for(int i = 0; i < 10; i++)
            this.addRecursiveButton(buttonLayout);

        final int links = 5;
        final float f = 8F;
        UIComponent prevlink = null;
        for(int i = 0; i < links; i++){
            final ImageView chainlink = new ImageView(ctx, Mathc.sin(((float) i / links) * f) * 0.5F + 0.5F, Mathc.sin(((float) i / links + 0.3F) * f) * 0.5F + 0.5F, Mathc.sin(((float) i / links + 0.6F) * f) * 0.5F + 0.5F);
            root.add(chainlink);
            chainlink.setID("chainlink_" + i);
            chainlink.size().set(Constraint.pixel(50), Constraint.pixel(50));
            if(i == 0){
                chainlink.bindings().left().set(UIDir.LEFT);
            }else if(i == links - 1){
                chainlink.bindings().right().set(UIDir.RIGHT);
                chainlink.bindings().left().set(UIDir.RIGHT, prevlink);
                prevlink.bindings().right().set(UIDir.LEFT, chainlink);
            }else{
                chainlink.bindings().left().set(UIDir.RIGHT, prevlink);
                prevlink.bindings().right().set(UIDir.LEFT, chainlink);
            }
            prevlink = chainlink;
        }

        // Jpize.window().setSizeLimits(500, 500, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Glfw.enableVSync(false);
    }

    final Texture2D button1 = new Texture2D("/button1.png");
    final Texture2D button2 = new Texture2D("/button2.png");
    final Texture2D button3 = new Texture2D("/button3.png");
    int item_number = 1;
    private void addRecursiveButton(UIComponent layout) {
        final ImageView item = new ImageView(ctx, button1, 0.9F);
        layout.add(item);
        item.setID("item_" + item_number);
        item.size().set(Constraint.relh(0.45), Constraint.aspect(0.3));
        item.bindings().left().set(UIDir.LEFT);
        item.margin().setBottom(Constraint.rel(0.005));

        // input
        item.callbacks().addOnHover((component, hovered) -> ((ImageView) component).background().setImage(hovered ? button2 : button1));
        item.callbacks().addOnClick((component, clicked) -> ((ImageView) component).background().setImage(button3));
        item.callbacks().addOnRelease((component, clicked, onComponent) -> {
            ((ImageView) component).background().setImage(button2);
            this.addRecursiveButton(layout);
        });

        item_number++;
    }

    float scale = 1F;
    float time = 0;
    @Override
    public void render() {
        Gl.clearColorBuffer();
        ctx.render();

        if(Key.T.pressed())
            ctx.findByID("scrollview").size().set(Constraint.pixel(Jpize.getX()), Constraint.pixel(Jpize.getY()));

        if(Key.LCTRL.pressed() && Jpize.getScroll() != 0)
            scale *= Mathc.pow(1F + 0.1F * Math.abs(Jpize.getScroll()), Jpize.getScroll() > 0 ? 1 : -1);
        ctx.scale().add((scale - ctx.scale().x) / 10F);
    }

    @Override
    public void dispose() {
        ctx.getRenderer().dispose();
    }


    public static void main(String[] args) {
        if(System.getProperty("os.name").equals("Linux"))
            Glfw.glfwInitHintPlatform(GlfwPlatform.X11);

        Jpize.create(1500, 900, "Interface Window").build().setApp(new Test());
        Jpize.run();
    }

}
