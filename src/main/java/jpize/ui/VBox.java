package jpize.ui;

public class VBox extends UIComponent {

    private UIComponent last;

    public VBox(UIContext context) {
        super(context);
        super.background().color().a = 0.2F; //! default color
    }

    public VBox add(UIComponent component) {
        super.add(component);
        component.bindings().left().set(UIDir.LEFT);
        component.bindings().right().set(UIDir.RIGHT);
        if(last != null){
            component.bindings().top().set(UIDir.BOTTOM, last);
        }else{
            component.bindings().top().set(UIDir.TOP);
        }
        last = component;
        return this;
    }

    public UIComponent getLast() {
        return last;
    }

}
