package jpize.ui.loader;

import java.lang.reflect.Field;

public interface UILoaderFieldSetter {

    void accept(UILoader loader, Object classObject, Field field, String values) throws IllegalAccessException;

}
