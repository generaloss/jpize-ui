package jpize.ui.loader;

import jpize.gl.texture.Texture2D;
import jpize.ui.common.*;
import jpize.ui.component.UIComponent;
import jpize.ui.common.UIDrawableImage;
import jpize.ui.common.UIDrawableNinePatch;
import jpize.ui.loader.mapper.*;
import jpize.util.color.Color;
import jpize.util.math.vector.Vec2f;
import jpize.util.region.TextureRegion;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UILoaderFieldModifierRegistry {

    private final Map<Type, UILoaderFieldModifier> modifierMap;

    public UILoaderFieldModifierRegistry() {
        this.modifierMap = new HashMap<>();

        this.register(UIDrawableNinePatch.class, UIMapperUIDrawableNinePatch::modify);
        this.register(UIDrawableImage.class, UIMapperUIDrawableImage::modify);
        this.register(UICorners.class, this::modifyUICorners);
        this.register(UIInsets.class, this::modifyUIInsets);
        this.register(UIDimensions.class, this::modifyUIDimensions);
        this.register(UIBindInsets.class, this::modifyUIBindInsets);
        this.register(UIBind.class, this::modifyUIBind);
        this.register(Vec2f.class, this::modifyVec2f);
        this.register(Color.class, UIMapperColor::modify);
        this.register(Texture2D.class, UIMapperTexture2D::modify);
        this.register(TextureRegion.class, UIMapperTextureRegion::modify);
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

}
