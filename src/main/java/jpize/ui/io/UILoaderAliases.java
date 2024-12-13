package jpize.ui.io;

import java.util.HashMap;
import java.util.Map;

public class UILoaderAliases {

    private final Map<String, String> map;

    public UILoaderAliases() {
        this.map = new HashMap<>();
        this.aliasAttribute("background.color", "color");
        this.aliasAttribute("background.image", "image");
    }

    public UILoaderAliases aliasAttribute(String attribute, String alias) {
        map.put(alias, attribute);
        return this;
    }

    public String normalizeAttribute(String alias) {
        // 'color' => 'background.color'
        // 'background.color' => 'background.color'
        return map.getOrDefault(alias, alias);
    }

}
