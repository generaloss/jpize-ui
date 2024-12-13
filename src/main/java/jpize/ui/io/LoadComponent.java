package jpize.ui.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LoadComponent {

    private final Map<String, String> attributes;
    private final List<LoadComponent> children;

    public LoadComponent() {
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
    }

    public Map<String, String> attributes() {
        return attributes;
    }

    public List<LoadComponent> children() {
        return children;
    }

    public void clear() {
        attributes.clear();
        children.clear();
    }

}