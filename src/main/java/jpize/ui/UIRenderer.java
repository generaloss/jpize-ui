package jpize.ui;

import jpize.gl.shader.Shader;
import jpize.gl.tesselation.GlScissor;
import jpize.util.Disposable;
import jpize.util.camera.OrthographicCamera;
import jpize.util.color.Color;
import jpize.util.font.Font;
import jpize.util.font.FontLoader;
import jpize.util.mesh.TextureBatch;
import jpize.util.res.Resource;

public class UIRenderer implements Disposable {

    private final OrthographicCamera camera;
    private final TextureBatch batch;
    private final GlScissor<UIComponent> scissor;
    private final Shader shader;
    private final Font font;

    public UIRenderer() {
        this.camera = new OrthographicCamera();
        this.batch = new TextureBatch();
        this.shader = new Shader(Resource.internal("/shader/ui/ui.vert"), Resource.internal("/shader/ui/ui.frag"));
        this.batch.setShader(shader);
        this.scissor = new GlScissor<>();
        this.font = FontLoader.loadDefault();
        this.font.getRenderOptions().scale().set(0.4F);
        this.font.getRenderOptions().color().set(0F, 0F, 0F);
    }

    public OrthographicCamera camera() {
        return camera;
    }

    public TextureBatch batch() {
        return batch;
    }

    public GlScissor<UIComponent> scissor() {
        return scissor;
    }


    public void setCornerSoftness(float softness){
        this.shader.uniform("u_corner_softness", softness);
    }

    public void setBorderSoftness(float softness){
        this.shader.uniform("u_border_softness", softness);
    }


    public void setup() {
        batch.setup(camera);
    }

    public void render(UIComponent component) {
        // render if visible
        final UIComponentState state = component.getState();
        if(component.getVisibility()){

            final float x = state.position().x;
            final float y = state.position().y;
            final float width = state.size().x;
            final float height = state.size().y;

            final float[] roundCorners = state.roundCorners();
            final float borderWidth = state.borderWidth();

            final UIBackground background = component.background();
            final Color borderColor = background.borderColor();

            // shader
            this.beginRect(x, y, width, height, roundCorners, borderWidth, borderColor);
            // render
            if(background.image() != null){
                batch.setColor(background.color());
                batch.draw(background.image(), x, y, width, height);
            }else{
                batch.drawRect(x, y, width, height, background.color());
            }
            batch.render();
            component.onRenderBegin(this);
            if(component.getID() != null)
                font.drawText(component.getID(), x, y);
        }

        // iterate children
        for(UIComponent child: component.getChildren())
            this.render(child);

        if(component.getVisibility())
            component.onRenderEnd(this);
    }

    public void beginRect(float x, float y, float width, float height, float[] cornerRadius, float borderSize, Color borderColor){
        shader.bind();
        shader.uniform("u_corner_radius", cornerRadius[0], cornerRadius[1], cornerRadius[2], cornerRadius[3]);
        shader.uniform("u_border_width", borderSize);
        shader.uniform("u_border_color", borderColor);
        shader.uniform("u_center", x + width / 2 + batch.position().x, y + height / 2 + batch.position().y);
        shader.uniform("u_size", width, height);
    }

    public void flush() {
        batch.render();
    }


    public void resize(int width, int height) {
        camera.resize(width, height);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shader.dispose();
    }

}