package jpize.ui.loader;

import jpize.ui.component.UIComponent;
import jpize.ui.palette.*;

import java.util.HashMap;
import java.util.Map;

public class UILoaderClassRegistry {

    private final Map<String, Class<? extends UIComponent>> classMap;
    private final Map<String, String> aliasesMap;

    public UILoaderClassRegistry() {
        this.classMap = new HashMap<>();
        this.aliasesMap = new HashMap<>();

        this.register(ImageView.class,      "ImageView");
        // this.register(NinePatchImage.class, "NinePatchImage");
        this.register(ScrollView.class,     "ScrollView");
        this.register(VBox.class,           "VBox");
    }

    public UILoaderClassRegistry register(Class<? extends UIComponent> componentClass) {
        classMap.put(componentClass.getName(), componentClass);
        return this;
    }

    public UILoaderClassRegistry alias(Class<? extends UIComponent> componentClass, String alias) {
        aliasesMap.put(alias, componentClass.getName());
        return this;
    }

    public UILoaderClassRegistry register(Class<? extends UIComponent> componentClass, String alias) {
        this.register(componentClass);
        this.alias(componentClass, alias);
        return this;
    }

    public <C extends Class<? extends UIComponent>> C get(String classNameAlias) {
        final String className = aliasesMap.getOrDefault(classNameAlias, classNameAlias);
        // noinspection unchecked
        return (C) classMap.get(className);
    }

}
