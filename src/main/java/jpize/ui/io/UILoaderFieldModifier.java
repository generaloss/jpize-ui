package jpize.ui.io;

import jpize.gl.texture.Texture2D;
import jpize.ui.common.UIBind;
import jpize.util.color.Color;
import jpize.util.math.vector.Vec2f;
import jpize.util.res.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class UILoaderFieldModifier {

    private final Map<Class<?>, BiConsumer<Object, String>> setterMap;

    public UILoaderFieldModifier() {
        this.setterMap = new HashMap<>();

        this.register(UIBind.class, this::processUIBind);
        this.register(Vec2f.class, this::processVec2f);
        this.register(Color.class, this::processColor);
        this.register(Texture2D.class, this::processTexture2D);
    }

    public UILoaderFieldModifier register(Class<?> fieldClass, BiConsumer<Object, String> setter) {
        setterMap.put(fieldClass, setter);
        return this;
    }

    public void process(Class<?> fieldClass, Object fieldObject, String stringValue) {
        if(stringValue.isBlank())
            throw new IllegalArgumentException("Value is blank");

        final BiConsumer<Object, String> setter = setterMap.get(fieldClass);
        if(setter == null)
            throw new IllegalArgumentException("Unknown field class '" + fieldClass.getName() + "'");

        setter.accept(fieldObject, stringValue);
    }


    private void processUIBind(Object object, String stringValue) {
        // "component_id, directory"
        final UIBind bind = (UIBind) object;

        final String[] arguments = stringValue.split(",");
        if(arguments.length != 2)
            throw new IllegalArgumentException("Illegal binding fonrmat");

        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        // bind.setComponent ... a kak zdes?
    }

    private void processVec2f(Object object, String stringValue) {
        // "1.0, 1.0"
        // "1.0"
        final Vec2f vector = (Vec2f) object;

        final String[] components = stringValue.split(",");
        for(int i = 0; i < components.length; i++)
            components[i] = components[i].trim();

        switch(components.length) {
            case 1 -> vector.set(Float.parseFloat(components[0]));
            case 2 -> vector.set(Float.parseFloat(components[0]), Float.parseFloat(components[1]));
            default -> throw new IllegalArgumentException("Illegal vector format");
        }
    }

    private void processColor(Object object, String stringValue) {
        // "#ffffff"            - lowercase or uppercase
        // "#ffffffff"          - with alpha
        // "1.0, 1.0, 1.0, 1.0"
        // "1.0, 1.0, 1.0"
        // "1.0, 1.0"           - grayscale, alpha
        // "1.0"                - only set alpha channel
        final Color color = (Color) object;
        if(stringValue.startsWith("#")) {
            //! Color.set(stringValue); !update engine
        }else{
            final String[] channels = stringValue.split(",");
            for(int i = 0; i < channels.length; i++)
                channels[i] = channels[i].trim();

            switch(channels.length) {
                case 1 -> color.setA(Float.parseFloat(channels[0])); //! setAlpha !update engine
                case 2 -> {
                    final float grayscale = Float.parseFloat(channels[0]);
                    final float alpha = Float.parseFloat(channels[1]);
                    color.set(grayscale, grayscale, grayscale, alpha);
                }
                case 3 -> {
                    final float red = Float.parseFloat(channels[0]);
                    final float green = Float.parseFloat(channels[1]);
                    final float blue = Float.parseFloat(channels[2]);
                    color.set(red, green, blue);
                }
                case 4 -> {
                    final float red = Float.parseFloat(channels[0]);
                    final float green = Float.parseFloat(channels[1]);
                    final float blue = Float.parseFloat(channels[2]);
                    final float alpha = Float.parseFloat(channels[3]);
                    color.set(red, green, blue, alpha);
                }
                default -> throw new IllegalArgumentException("Illegal color format");
            }
        }
    }

    private void processTexture2D(Object object, String stringValue) {
        // "registry:texture"
        // "internal:/texture.png"
        // "external:/texture.png"
        //      "url:link"
        final Texture2D texture2D = (Texture2D) object;
        final String[] split = stringValue.split(":");
        if(split.length == 1)
            throw new IllegalArgumentException("Illegal resource format");

        final String resourceType = split[0];
        final String path = stringValue.replace(resourceType + ":", "");
        switch(resourceType) {
            case "registry" -> texture2D.setImage(path);
            case "internal" -> texture2D.setImage(path);
            case "external" -> texture2D.setImage(Resource.external(path));
            case "url" -> texture2D.setImage(Resource.url(path));
            default -> throw new IllegalArgumentException("Invalid resource type '" + resourceType + "', allowed: [registry, internal, external, url].");
        }
    }

}
