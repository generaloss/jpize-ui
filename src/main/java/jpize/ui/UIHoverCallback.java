package jpize.ui;

@FunctionalInterface
public interface UIHoverCallback {

    void invoke(UIComponent component, boolean hovered);

}
