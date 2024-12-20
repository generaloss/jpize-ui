package jpize.ui.common;

import jpize.ui.component.UIRenderer;
import jpize.util.Disposable;

public interface UIDrawable extends Disposable {

    void draw(UIRenderer renderer, float x, float y, float width, float height);

}
