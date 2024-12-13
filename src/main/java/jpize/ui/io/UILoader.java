package jpize.ui.io;

import jpize.ui.component.UIComponent;
import jpize.ui.component.UIContext;
import jpize.util.res.Resource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Map;

public class UILoader {

    private final UILoaderAliases aliases;
    private final UILoaderClassRegistry classes;
    private final UILoaderFieldModifier fieldSetter;

    private final LoadComponent rootLoadComponent;

    public UILoader() {
        this.aliases = new UILoaderAliases();
        this.classes = new UILoaderClassRegistry();
        this.fieldSetter = new UILoaderFieldModifier();
        this.rootLoadComponent = new LoadComponent();
    }

    public UILoaderAliases aliases() {
        return aliases;
    }

    public UILoaderClassRegistry classes() {
        return classes;
    }

    public UILoaderFieldModifier fieldSetter() {
        return fieldSetter;
    }


    public UILoader load(Resource resource) {
        if(!resource.exists())
            throw new IllegalStateException("XML resource '" + resource + "' not found.");

        try{
            final SAXReader xmlReader = new SAXReader();
            final Document xml = xmlReader.read(resource.inStream());
            final Element rootXml = xml.getRootElement();
            this.loadComponent(rootLoadComponent, rootXml);
            this.normalizeAttributes(rootLoadComponent);

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public UILoader load(String internalPath) {
        return load(Resource.internal(internalPath));
    }

    private void loadComponent(LoadComponent component, Element xml) {
        // attributes
        for(Attribute attribute : xml.attributes())
            component.attributes().put(attribute.getName(), attribute.getValue());
        // children
        for(Element childXml : xml.elements()){
            final LoadComponent childLoadComponent = new LoadComponent();
            component.children().add(childLoadComponent);
            this.loadComponent(childLoadComponent, childXml);
        }
    }

    private void normalizeAttributes(LoadComponent component) {
        // attributes
        final Map<String, String> attributes = new HashMap<>();
        component.attributes().forEach((attributeAlias, value) -> {
            final String attribute = aliases.normalizeAttribute(attributeAlias);
            attributes.put(attribute, value);
        });
        component.attributes().clear();
        component.attributes().putAll(attributes);
        // children
        for(LoadComponent child : component.children())
            this.normalizeAttributes(child);
    }


    public UIComponent createComponent() {
        return null;
    }

    public UIContext createContext() {
        final UIContext context = new UIContext();
        context.setRoot(createComponent());
        return context;
    }

}
