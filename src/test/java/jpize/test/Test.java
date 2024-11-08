package jpize.test;

import jpize.app.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.gl.texture.Texture2D;
import jpize.glfw.Glfw;
import jpize.glfw.init.GlfwPlatform;
import jpize.glfw.input.Key;
import jpize.ui.*;
import jpize.util.math.Mathc;

public class Test extends JpizeApplication {

    private final UIContext ctx = new UIContext().enable();

    @Override
    public void init() {
        Gl.clearColor(0.3, 0.3, 0.3);

        ImageView root = new ImageView(ctx, "/background.png", 0.5F);
        ctx.setRoot(root);
        root.setID("root");
        root.size().set(Constraint.match_parent, Constraint.match_parent);
        root.margin().set(Constraint.pixel(50));

        ScrollView scrollview = new ScrollView(ctx);
        root.add(scrollview);
        scrollview.setID("scrollview");
        scrollview.size().set(Constraint.aspect(0.7), Constraint.rel(0.75));
        scrollview.background().color().set(1, 1, 1, 0.5);
        scrollview.bindings().toCenter();
        scrollview.background().roundCorners(Constraint.pixel(30));
        scrollview.background().setBorderWidth(Constraint.pixel(0));

        VBox layout = new VBox(ctx);
        layout.size().set(Constraint.wrap_content, Constraint.wrap_content);
        layout.bindings().left().set(UIDir.LEFT);
        layout.bindings().right().set(UIDir.RIGHT);
        scrollview.add(layout);

        for(int i = 0; i < 10; i++)
            this.addRecursiveButton(layout);

        final int links = 5;
        final float f = 8F;
        UIComponent prevlink = null;
        for(int i = 0; i < links; i++){
            ImageView chainlink = new ImageView(ctx, Mathc.sin(((float) i / links) * f) * 0.5F + 0.5F, Mathc.sin(((float) i / links + 0.3F) * f) * 0.5F + 0.5F, Mathc.sin(((float) i / links + 0.6F) * f) * 0.5F + 0.5F);
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

    Texture2D button1 = new Texture2D("/button1.png");
    Texture2D button2 = new Texture2D("/button2.png");
    Texture2D button3 = new Texture2D("/button3.png");
    int item_number = 1;
    private void addRecursiveButton(UIComponent layout) {
        ImageView item = new ImageView(ctx, button1, 0.9F);
        layout.add(item);
        item.setID("item_" + item_number);
        item.size().set(Constraint.relh(0.45), Constraint.aspect(0.3));
        item.margin().setBottom(Constraint.rel(0.005));
        item.callbacks().addOnHover((component, hovered) -> component.background().setImage(hovered ? button2 : button1));
        item.callbacks().addOnClick((component, clicked) -> component.background().setImage(button3));
        item.callbacks().addOnRelease((component, clicked, onComponent) -> {
            component.background().setImage(button2);
            addRecursiveButton(layout);
        });
        item_number++;
    }

    float scale = 1F;
    float time = 0;
    @Override
    public void render() {
        //System.out.println("FPS: " + Jpize.getFPS());
        Gl.clearColorBuffer();
        ctx.render();

        // time += Jpize.getDT();
        // for(int i = 0; i < 10; i++)
        //     ctx.findByID("chainlink_" + i).bindings().bias().x = (Mathc.sin(time) * 0.5F + 0.5F);

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
