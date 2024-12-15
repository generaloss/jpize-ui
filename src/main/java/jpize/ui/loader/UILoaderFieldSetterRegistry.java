package jpize.ui.loader;

import jpize.gl.texture.Texture2D;
import jpize.ui.component.UIDrawable;
import jpize.ui.component.UIDrawableImage;
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
        // "restype:texture"
        // "restype:texture, r, g, b, a"
        final UIDrawableImage drawableImage = new UIDrawableImage();

        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();

        switch (arguments.length) {
            case 1 -> drawableImage.setImage(loadTexture2D(loader, values));
            case 5 -> {
                drawableImage.setImage(loadTexture2D(loader, arguments[0]));
                drawableImage.color().set(
                    Float.parseFloat(arguments[1]),
                    Float.parseFloat(arguments[2]),
                    Float.parseFloat(arguments[3]),
                    Float.parseFloat(arguments[4])
                );
            }
        }

        field.set(classObject, drawableImage);
    }

    private void setTexture2D(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        // "restype:texture"
        final Texture2D texture2D = loadTexture2D(loader, values);
        field.set(classObject, texture2D);
    }

    private void setTextureRegion(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException {
        // "restype:texture"
        // "restype:texture, x, y, width, height" // in pixels
        final String[] arguments = values.split(",");
        for(int i = 0; i < arguments.length; i++)
            arguments[i] = arguments[i].trim();
        
        final Texture2D texture2D = loadTexture2D(loader, arguments[0]);
        final TextureRegion textureRegion = new TextureRegion(texture2D);
        field.set(classObject, textureRegion);

        if(arguments.length == 5) {
            final int x = Integer.parseInt(arguments[1]);
            final int y = Integer.parseInt(arguments[2]);
            final int width = Integer.parseInt(arguments[3]);
            final int height = Integer.parseInt(arguments[4]);
            textureRegion.set(x, y, width, height);

        }else if(arguments.length != 1){
            throw new IllegalArgumentException("Illegal texture region format");
        }
    }

    protected static Texture2D loadTexture2D(UILoader loader, String values) {
        final Texture2D texture2D = new Texture2D();
        UILoaderFieldModifierRegistry.loadTexture2D(loader, texture2D, values);
        return texture2D;
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
