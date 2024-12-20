package jpize.ui.loader;

import jpize.gl.texture.Texture2D;
import jpize.ui.common.UIDrawable;
import jpize.ui.loader.mapper.UIMapperTexture2D;
import jpize.ui.loader.mapper.UIMapperTextureRegion;
import jpize.ui.loader.mapper.UIMapperUIDrawable;
import jpize.util.region.TextureRegion;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UILoaderFieldSetterRegistry {

    private final Map<Type, UILoaderFieldSetter> setterMap;

    public UILoaderFieldSetterRegistry() {
        this.setterMap = new HashMap<>();

        this.register(UIDrawable.class, this::setUIDrawable);
        this.register(Texture2D.class, this::setTexture2D);
        this.register(TextureRegion.class, this::setTextureRegion);
        this.register(byte.class, this::setByte);
        this.register(short.class, this::setShort);
        this.register(int.class, this::setInteger);
        this.register(float.class, this::setFloat);
        this.register(double.class, this::setDouble);
        this.register(boolean.class, this::setBoolean);
        this.register(char.class, this::setChar);
        this.register(String.class, this::setString);
    }

    public UILoaderFieldSetterRegistry register(Type type, UILoaderFieldSetter modifier) {
        setterMap.put(type, modifier);
        return this;
    }

    public void setField(UILoader loader, Object classObject, Field field, String values) {
        if(values.isBlank())
            throw new IllegalArgumentException("Value is blank");

        final Type type = field.getType();
        final UILoaderFieldSetter setter = setterMap.get(type);
        if(setter == null)
            throw new IllegalArgumentException("Unknown field class '" + type.getTypeName() + "'");

        field.setAccessible(true);
        try{
            setter.accept(loader, classObject, field, values);
        }catch(IllegalAccessException ignored) { }
    }


    private void setUIDrawable(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, UIMapperUIDrawable.load(loader, values));
    }

    private void setTexture2D(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, UIMapperTexture2D.load(loader, values));
    }

    private void setTextureRegion(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, UIMapperTextureRegion.load(loader, values));
    }

    private void setByte(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, Byte.parseByte(values));
    }

    private void setShort(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, Short.parseShort(values));
    }

    private void setInteger(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, Integer.parseInt(values));
    }

    private void setFloat(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, Float.parseFloat(values));
    }

    private void setDouble(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, Double.parseDouble(values));
    }

    private void setBoolean(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, Boolean.parseBoolean(values));
    }

    private void setChar(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, values.charAt(0));
    }

    private void setString(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        field.set(classObject, values);
    }

}
