package jpize.ui.loader.mapper;

import jpize.gl.texture.Texture2D;
import jpize.ui.loader.UILoader;
import jpize.util.res.Resource;

public class UIMapperTexture2D {

    // "registry:textureID"
    // "internal:/texture.png"
    // "external:/texture.png"
    //      "url:link"

    public static void modify(UILoader loader, Object object, String value) {
        final Texture2D texture2D = (Texture2D) object;

        final String[] split = value.split(":");
        if(split.length == 1)
            throw new IllegalArgumentException("Invalid texture resource format, allowed: 'restype:texture'");

        final String resourceType = split[0];
        final String path = value.replace(resourceType + ":", "");
        switch(resourceType) {
            case "registry" -> loader.resources().get(path);
            case "internal" -> texture2D.setImage(path);
            case "external" -> texture2D.setImage(Resource.external(path));
            case "url" -> texture2D.setImage(Resource.url(path));
            default -> throw new IllegalArgumentException("Invalid texture resource type '" + resourceType + "', allowed: [registry, internal, external, url].");
        }
    }

    public static Texture2D load(UILoader loader, String value) {
        final Texture2D texture2D = new Texture2D();
        modify(loader, texture2D, value);
        return texture2D;
    }

}
