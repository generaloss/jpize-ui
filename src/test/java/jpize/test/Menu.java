package jpize.test;

import jpize.app.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.glfw.Glfw;
import jpize.glfw.init.GlfwPlatform;
import jpize.ui.component.UIContext;
import jpize.ui.loader.UILoader;

public class Menu extends JpizeApplication {

    private final UIContext ctx = new UILoader()
        .load("/menu.xml")
        .createContext()
        .enable();

    @Override
    public void init() {
        Gl.clearColor(0.3, 0.3, 0.3);
    }

    @Override
    public void render() {
        Gl.clearColorBuffer();
        ctx.render();
    }

    @Override
    public void dispose() {
        ctx.getRenderer().dispose();
    }


    public static void main(String[] args) {
        if(System.getProperty("os.name").equals("Linux"))
            Glfw.glfwInitHintPlatform(GlfwPlatform.X11);

        Jpize.create(1500, 900, "Interface Window").build().setApp(new Menu());
        Jpize.run();
    }

}
