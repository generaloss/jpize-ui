package jpize.ui.loader;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class UILoaderAliases {

    private final Map<String, String> map;

    public UILoaderAliases() {
        this.map = new HashMap<>();
        this.aliasAttribute("bindings.bias", "bias");
        this.aliasAttribute("ID", "id");
    }

    public UILoaderAliases aliasAttribute(String attribute, String alias) {
        map.put(alias, attribute);
        return this;
    }

    public String normalizeAttribute(String aliasedAttribute) {
        // 'color' => 'background.color'
        // 'background.color' => 'background.color'

        final String[] links = aliasedAttribute.split("\\.");
        for(int i = (links.length - 1); i >= 0; i--){
            final StringJoiner joiner = new StringJoiner(".");
            for(int j = 0; j <= i; j++)
                joiner.add(links[j]);

            final String part = joiner.toString();
            if(map.containsKey(part))
                return aliasedAttribute.replace(part, map.get(part));
        }

        return aliasedAttribute;
    }

}
