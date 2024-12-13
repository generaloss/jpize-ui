package jpize.ui.common;

import jpize.ui.component.UIComponent;

public class UIBind {

    private UIComponent component;
    private UIDir directory;

    public UIBind() {
        this.setDirectory(UIDir.NONE);
    }

    public UIComponent component() {
        return component;
    }

    public UIBind setComponent(UIComponent component) {
        this.component = component;
        return this;
    }


    public UIDir directory() {
        return directory;
    }

    public UIBind setDirectory(UIDir directory) {
        this.directory = directory;
        return this;
    }



    public UIBind set(UIDir directory, UIComponent component) {
        this.component = component;
        this.directory = directory;
        return this;
    }

    public UIBind set(UIDir directory) {
        return this.set(directory, null);
    }

}
