package jpize.ui.loader;

import jpize.gl.texture.Texture2D;
import jpize.ui.common.*;
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
        this.register(UICorners.class, UIMapperUICorners::modify);
        this.register(UIInsets.class, UIMapperUIInsets::modify);
        this.register(UIDimensions.class, UIMapperUIDimentions::modify);
        this.register(UIBindInsets.class, UIMapperUIBindInsets::modify);
        this.register(UIBind.class, UIMapperUIBind::modify);
        this.register(Vec2f.class, UIMapperVec2f::modify);
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

}
