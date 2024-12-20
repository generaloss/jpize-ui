package jpize.ui.loader;

import jpize.gl.texture.Texture2D;
import jpize.ui.common.*;
import jpize.ui.component.UIComponent;
import jpize.ui.common.UIDrawableImage;
import jpize.ui.common.UIDrawableNinePatch;
import jpize.util.color.Color;
import jpize.util.math.vector.Vec2f;
import jpize.util.ninepatch.NinePatch;
import jpize.util.region.TextureRegion;
import jpize.util.res.Resource;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UILoaderFieldModifierRegistry {

    private final Map<Type, UILoaderFieldModifier> modifierMap;

    public UILoaderFieldModifierRegistry() {
        this.modifierMap = new HashMap<>();

        this.register(UIDrawableNinePatch.class, this::modifyUIDrawableNinePatch);
        this.register(UIDrawableImage.class, this::modifyUIDrawableImage);
        this.register(UICorners.class, this::modifyUICorners);
        this.register(UIInsets.class, this::modifyUIInsets);
        this.register(UIDimensions.class, this::modifyUIDimensions);
        this.register(UIBindInsets.class, this::modifyUIBindInsets);
        this.register(UIBind.class, this::modifyUIBind);
        this.register(Vec2f.class, this::modifyVec2f);
        this.register(Color.class, this::modifyColor);
        this.register(Texture2D.class, this::modifyTexture2D);
        this.register(TextureRegion.class, this::modifyTextureRegion);
    }

    public UILoaderFieldModifierRegistry register(Type type, UILoaderFieldModifier modifier) {
        modifierMap.put(type, modifier);
        return this;
    }

    public void modifyField(UILoader loader, Object fieldObject, String values) {
        if(values.isBlank())
            throw new IllegalArgumentException("Value is blank");

        final Type type = fieldObject.getClass();
        final UILoaderFieldModifier modifier = modifierMap.get(type);
        if(modifier == null)
            throw new IllegalArgumentException("Unknown field class '" + type.getTypeName() + "'");

        modifier.accept(loader, fieldObject, values);
    }


    private void modifyUIDrawableNinePatch(UILoader loader, Object fieldObject, String values) {
        // "image(restype:texture)"
        // "image(restype:texture, r, g, b, a)"
        final UIDrawableNinePatch drawableImage = (UIDrawableNinePatch) fieldObject;

        if(!values.startsWith("image9("))
            throw new IllegalArgumentException("Invalid image drawable format, allowed: '[image9(restype:texture), image9(restype:texture), r, g, b, a]");
        values = values.substring(7);

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        arguments[0] = arguments[0].substring(0, arguments[0].length() - 1);

        final NinePatch ninepatch = new NinePatch();
        drawableImage.setNinepatch(ninepatch);
        switch (arguments.length) {
            case 1 -> ninepatch.load(UILoaderFieldSetterRegistry.loadTexture2D(loader, arguments[0]));
            case 5 -> {
                ninepatch.load(UILoaderFieldSetterRegistry.loadTexture2D(loader, arguments[0]));
                drawableImage.color().set(
                        Float.parseFloat(arguments[1]),
                        Float.parseFloat(arguments[2]),
                        Float.parseFloat(arguments[3]),
                        Float.parseFloat(arguments[4])
                );
            }
        }
    }

    private void modifyUIDrawableImage(UILoader loader, Object fieldObject, String values) {
        // "image(restype:texture)"
        // "image(restype:texture, r, g, b, a)"
        final UIDrawableImage drawableImage = (UIDrawableImage) fieldObject;

        if(!values.startsWith("image(") || !values.endsWith(")"))
            throw new IllegalArgumentException("Invalid image drawable format, allowed: '[image(restype:texture), image(restype:texture, r, g, b, a)]");
        values = values.substring(6, values.length() - 1);

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch (arguments.length) {
            case 1 -> drawableImage.setImage(UILoaderFieldSetterRegistry.loadTexture2D(loader, values));
            case 5 -> {
                drawableImage.setImage(UILoaderFieldSetterRegistry.loadTexture2D(loader, arguments[0]));
                drawableImage.color().set(
                        Float.parseFloat(arguments[1]),
                        Float.parseFloat(arguments[2]),
                        Float.parseFloat(arguments[3]),
                        Float.parseFloat(arguments[4])
                );
            }
        }
    }

    private void modifyUICorners(UILoader loader, Object fieldObject, String values){
        // "constraint, constraint, ..."
        final UICorners corners = (UICorners) fieldObject;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch(arguments.length) {
            case 1 -> corners.round(Constraint.parseConstraint(arguments[0]));
            case 4 -> corners.round(
                Constraint.parseConstraint(arguments[0]),
                Constraint.parseConstraint(arguments[1]),
                Constraint.parseConstraint(arguments[2]),
                Constraint.parseConstraint(arguments[3])
            );
        }
    }


    private void modifyUIInsets(UILoader loader, Object fieldObject, String values) {
        // ""
        final UIInsets insets = (UIInsets) fieldObject;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch(arguments.length) {
            case 1 -> insets.set(Constraint.parseConstraint(arguments[0]));
            case 2 -> insets.set(Constraint.parseConstraint(arguments[0]), Constraint.parseConstraint(arguments[1]));
            case 4 -> insets.set(
                Constraint.parseConstraint(arguments[0]),
                Constraint.parseConstraint(arguments[1]),
                Constraint.parseConstraint(arguments[0]),
                Constraint.parseConstraint(arguments[1])
            );
            default -> throw new IllegalArgumentException("Invalid insets format, allowed: [all, (vertival)]");
        }
    }

    private void modifyUIDimensions(UILoader loader, Object fieldObject, String values) {
        // "center" // to center
        final UIDimensions dimensions = (UIDimensions) fieldObject;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch(arguments.length) {
            case 1 -> dimensions.set(Constraint.parseConstraint(arguments[0]));
            case 2 -> dimensions.set(Constraint.parseConstraint(arguments[0]), Constraint.parseConstraint(arguments[1]));
            default -> throw new IllegalArgumentException("Invalid dimensions format, allowed: [xy, (x, y)]");
        }
    }

    private void modifyUIBindInsets(UILoader loader, Object fieldObject, String values) {
        // "center" // to center
        final UIBindInsets bindInsets = (UIBindInsets) fieldObject;
        if(values.equalsIgnoreCase("center")){
            bindInsets.toCenter();
        }else{
            throw new IllegalArgumentException("Invalid bindings option '" + values + "', allowed: center");
        }
    }

    private void modifyUIBind(UILoader loader, Object fieldObject, String values) {
        // "component_id, directory"
        final UIBind bind = (UIBind) fieldObject;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        if(arguments.length > 2)
            throw new IllegalArgumentException("Invalid binding format, allowed: [directory, (component_id, directory)]");

        if(arguments.length == 2){
            final UIComponent component = loader.context().findByID(arguments[0]);
            if(component == null)
                throw new IllegalArgumentException("Component '" + arguments[0] + "' not found");
            bind.setComponent(component);
        }

        final int dirArgIndex = (arguments.length - 1);
        try{
            final UIDir directory = UIDir.valueOf(arguments[dirArgIndex].toUpperCase());
            bind.setDirectory(directory);
        }catch(Exception ignored){
            throw new IllegalArgumentException("Invalid directory '" + arguments[dirArgIndex] + "', allowed: [none, top, left, bottom, right]");
        }
    }

    private void modifyVec2f(UILoader loader, Object fieldObject, String values) {
        // "1.0, 1.0"
        // "1.0"
        final Vec2f vector = (Vec2f) fieldObject;

        final String[] components = values.split(",");
        for(int i = 0; i < components.length; i++)
            components[i] = components[i].trim();

        switch(components.length) {
            case 1 -> vector.set(Float.parseFloat(components[0]));
            case 2 -> vector.set(Float.parseFloat(components[0]), Float.parseFloat(components[1]));
            default -> throw new IllegalArgumentException("Invalid vector format, allowed: (x, y)");
        }
    }

    private void modifyColor(UILoader loader, Object fieldObject, String values) {
        // "#fff"               - rgb (short)
        // "#ffff"              - rgba (short)
        // "#ffffff"            - rgb
        // "#ffffffff"          - rgba
        // "1.0, 1.0, 1.0, 1.0"
        // "1.0, 1.0, 1.0"
        // "1.0, 1.0"           - grayscale, alpha
        // "1.0"                - only set alpha channel
        final Color color = (Color) fieldObject;
        if(values.startsWith("#")) {
            color.set(values);
        }else{
            final String[] channels = values.split(",");
            for(int i = 0; i < channels.length; i++)
                channels[i] = channels[i].trim();

            switch(channels.length) {
                case 1 -> color.setAlpha(Float.parseFloat(channels[0]));
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
                default -> throw new IllegalArgumentException("Invalid color format, allowed: [(r, g, b, a), (r, g, b), (grayscale, alpha), alpha]");
            }
        }
    }

    private void modifyTexture2D(UILoader loader, Object fieldObject, String values) {
        // "restype:texture"
        final Texture2D texture2D = (Texture2D) fieldObject;
        loadTexture2D(loader, texture2D, values);
    }

    private void modifyTextureRegion(UILoader loader, Object fieldObject, String values) {
        // "restype:texture"
        // "restype:texture, x, y, width, height" // in pixels
        final TextureRegion textureRegion = (TextureRegion) fieldObject;

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        final Texture2D texture2D = UILoaderFieldSetterRegistry.loadTexture2D(loader, arguments[0]);

        if(arguments.length == 5) {
            final int x = Integer.parseInt(arguments[1]);
            final int y = Integer.parseInt(arguments[2]);
            final int width = Integer.parseInt(arguments[3]);
            final int height = Integer.parseInt(arguments[4]);
            textureRegion.set(x, y, width, height);

        }else if(arguments.length != 1){
            throw new IllegalArgumentException("Invalid texture region format, allowed: [restype:texture, (restype:texture, x, y, width, height)]");
        }
    }

    protected static void loadTexture2D(UILoader loader, Texture2D texture2D, String values) {
        // "registry:texture"
        // "internal:/texture.png"
        // "external:/texture.png"
        //      "url:link"
        final String[] split = values.split(":");
        if(split.length == 1)
            throw new IllegalArgumentException("Invalid texture resource format, allowed: 'restype:texture'");

        final String resourceType = split[0];
        final String path = values.replace(resourceType + ":", "");
        switch(resourceType) {
            case "registry" -> loader.resources().get(path);
            case "internal" -> texture2D.setImage(path);
            case "external" -> texture2D.setImage(Resource.external(path));
            case "url" -> texture2D.setImage(Resource.url(path));
            default -> throw new IllegalArgumentException("Invalid texture resource type '" + resourceType + "', allowed: [registry, internal, external, url].");
        }
    }

}
