package jpize.ui.io;

import jpize.ui.component.UIComponent;
import jpize.ui.palette.*;

import java.util.HashMap;
import java.util.Map;

public class UILoaderClassRegistry {

    private final Map<String, Class<? extends UIComponent>> classMap;

    public UILoaderClassRegistry() {
        this.classMap = new HashMap<>();

        this.register(ImageView.class);
        this.register(ScrollView.class);
        this.register(VBox.class);
    }

    public UILoaderClassRegistry register(Class<? extends UIComponent> componentClass) {
        classMap.put(componentClass.getName(), componentClass);
        return this;
    }

    public <C extends Class<? extends UIComponent>> C get(String className) {
        // noinspection unchecked
        return (C) classMap.get(className);
    }

}
