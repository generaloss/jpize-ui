package jpize.ui.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UIComponentToLoad {

    private String className;
    private final Map<String, String> attributes;
    private final List<UIComponentToLoad> children;

    public UIComponentToLoad() {
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> attributes() {
        return attributes;
    }

    public List<UIComponentToLoad> children() {
        return children;
    }

    public void clear() {
        attributes.clear();
        children.clear();
    }

}