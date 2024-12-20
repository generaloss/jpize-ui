package jpize.ui.loader;

import jpize.ui.component.UIComponent;
import jpize.ui.component.UIContext;
import jpize.util.res.Resource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UILoader {

    private final UILoaderAliases aliases;
    private final UILoaderClassRegistry classes;
    private final UILoaderFieldModifierRegistry fieldModifiers;
    private final UILoaderFieldSetterRegistry fieldSetters;
    private final UILoaderResourceRegistry resources;

    private final UIComponentToLoad rootComponentToLoad;
    private final Map<UIComponentToLoad, UIComponent> loadedComponentMap;
    private UIContext context;

    public UILoader() {
        this.aliases = new UILoaderAliases();
        this.classes = new UILoaderClassRegistry();
        this.fieldModifiers = new UILoaderFieldModifierRegistry();
        this.fieldSetters = new UILoaderFieldSetterRegistry();
        this.resources = new UILoaderResourceRegistry();
        this.rootComponentToLoad = new UIComponentToLoad();
        this.loadedComponentMap = new ConcurrentHashMap<>();
    }

    public UILoaderAliases aliases() {
        return aliases;
    }

    public UILoaderClassRegistry classes() {
        return classes;
    }

    public UILoaderFieldModifierRegistry fieldModifiers() {
        return fieldModifiers;
    }

    public UILoaderFieldSetterRegistry fieldSetters() {
        return fieldSetters;
    }

    public UILoaderResourceRegistry resources() {
        return resources;
    }

    public UIContext context() {
        return context;
    }

    public UILoader load(Resource resource) {
        if(!resource.exists()){
            throw new IllegalStateException("XML resource '" + resource + "' not found");
        }
        try{
            final SAXReader xmlReader = new SAXReader();
            final Document xml = xmlReader.read(resource.inStream());
            final Element rootXml = xml.getRootElement();
            rootComponentToLoad.setClassName(rootXml.getName());
            this.loadComponent(rootComponentToLoad, rootXml);
            this.normalizeAttributes(rootComponentToLoad);

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public UILoader load(String internalPath) {
        return this.load(Resource.internal(internalPath));
    }

    private void loadComponent(UIComponentToLoad component, Element xml) {
        // attributes
        for(Attribute attribute : xml.attributes())
            component.attributes().put(attribute.getName(), attribute.getValue());
        // children
        for(Element childXml : xml.elements()){
            final UIComponentToLoad childComponentToLoad = new UIComponentToLoad();
            childComponentToLoad.setClassName(childXml.getName());
            component.children().add(childComponentToLoad);
            this.loadComponent(childComponentToLoad, childXml);
        }
    }

    private void normalizeAttributes(UIComponentToLoad component) {
        // attributes
        final Map<String, String> attributes = new HashMap<>();
        component.attributes().forEach((attributeAlias, value) -> {
            final String attribute = aliases.normalizeAttribute(attributeAlias);
            attributes.put(attribute, value);
        });
        component.attributes().clear();
        component.attributes().putAll(attributes);
        // children
        for(UIComponentToLoad child : component.children())
            this.normalizeAttributes(child);
    }


    private UIComponent createComponent(UIComponentToLoad componentToLoad) {
        final Class<UIComponent> componentClass = classes.get(componentToLoad.getClassName());
        if(componentClass == null)
            throw new RuntimeException("Class '" + componentToLoad.getClassName() + "' not found");

        try{
            // instantiate component
            final Constructor<UIComponent> componentConstructor = componentClass.getDeclaredConstructor(UIContext.class);
            componentConstructor.setAccessible(true);
            final UIComponent component = componentConstructor.newInstance(context);
            // put to loaded component map
            loadedComponentMap.put(componentToLoad, component);

            // recursive create children components
            for(UIComponentToLoad childToLoad: componentToLoad.children()){
                final UIComponent child = this.createComponent(childToLoad);
                component.add(child);
            }

            return component;
        }catch(NoSuchMethodException ignored) {
            throw new RuntimeException("Class '" + componentToLoad.getClassName() + "' has no default constructor");
        }catch(InstantiationException | InvocationTargetException | IllegalAccessException e){
            throw new RuntimeException("Cannot instantiate class '" + componentToLoad.getClassName() + "'");
        }
    }


    private Field findField(Object object, String name){
        Class<?> c = object.getClass();
        while(c != null){
            try{
                final Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            }catch(Exception ignored){
                c = c.getSuperclass();
            }
        }
        return null;
    }

    private void initField(Field field, Object object, String value) {
        try{
            final Object fieldObject = field.get(object);
            if(fieldObject == null || field.getType().isPrimitive()) {
                fieldSetters.setField(this, object, field, value);
            }else{
                fieldModifiers.modifyField(this, fieldObject, value);
            }
        }catch(IllegalAccessException ignored){ }
    }

    private void findFieldAndInit(Object object, String fieldPath, String value) {
        final String[] fieldLinks = fieldPath.split("\\.");
        if(fieldLinks.length == 0)
            throw new RuntimeException("Field atribute is empty");

        // find actual field
        Field field = null;
        for(String fieldLink: fieldLinks){
            // get object for next field
            try{
                if(field != null){
                    object = field.get(object);
                }
            }catch(IllegalAccessException ignored){ }
            // find field in object
            field = this.findField(object, fieldLink);
            if(field == null)
                throw new RuntimeException("Cannot find field '" + fieldPath.replace(fieldLink, "[" + fieldLink + "]") + "' in class " + object.getClass().getName());
        }

        // init
        this.initField(field, object, value);
    }

    private void initFields(UIComponent component, UIComponentToLoad componentToLoad) {
        // init ID fields
        componentToLoad.attributes().forEach((attribute, value) -> {
            if(attribute.equals("ID"))
                this.findFieldAndInit(component, attribute, value);
        });
        // init other fields
        componentToLoad.attributes().forEach((attribute, value) -> {
            if(!attribute.equals("ID"))
                this.findFieldAndInit(component, attribute, value);
        });
        // recursive init children fields
        for(UIComponentToLoad childToLoad: componentToLoad.children())
            this.initFields(loadedComponentMap.get(childToLoad), childToLoad);
    }


    public UIContext createContext() {
        context = new UIContext();
        final UIComponent rootComponent = this.createComponent(rootComponentToLoad);
        this.initFields(rootComponent, rootComponentToLoad);
        context.setRoot(rootComponent);
        return context;
    }

}
