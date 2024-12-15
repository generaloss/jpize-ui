package jpize.ui.loader;

import java.util.HashMap;
import java.util.Map;

public class UILoaderResourceRegistry {

    private final Map<String, Object> resourceMap;

    public UILoaderResourceRegistry() {
        this.resourceMap = new HashMap<>();
    }

    public UILoaderResourceRegistry register(String string, Object resource) {
        resourceMap.put(string, resource);
        return this;
    }

    public <O> O get(String string) {
        // noinspection unchecked
        return (O) resourceMap.get(string);
    }

}
