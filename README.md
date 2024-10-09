
## Module *UI*:
* *[UIComponent](ui/src/main/java/jpize/ui/component/UIComponent.java)* - Base GUI component class
* *[Constraint](ui/src/main/java/jpize/ui/constraint)* - Pixel, Relative (to width / height), Aspect, Flag (zero, auto, match_parent, wrap_content)
* *[AbstractLayout](ui/src/main/java/jpize/ui/component/AbstractLayout.java)* - implemented in: ConstraintLayout, VBox, HBox, ScrollView

#### 1. UI Java Example:
``` java
// context
UIContext ui = new UIContext();

// Layout
AbstractLayout layout = new VBox(Constr.win_width, Constr.win_height);
ui.setRoot(layout);

// Button
Button button = new Button(Constr.aspect(10), Constr.px(100), "Button Text", font);
button.padding().set(Constr.relh(0.35), Constr.zero, Constr.auto, Constr.zero);

// Slider
Slider slider = new Slider(Constr.aspect(10), Constr.px(100));
slider.padding().set(Constr.px(10), Constr.zero, Constr.auto, Constr.zero);

// Add to layout Button & Slider
layout.add(button);
layout.add(slider);

// Callbacks
button.input().addPressCallback((component, btn) -> {
    System.out.println("Press Button");
});
button.input().addReleaseCallback((component, btn) -> {
    System.out.println("Release Button");
});

slider.addSliderCallback(((component, value) -> {
    System.out.println("Slider value: " + value);
}));

// Render
ui.render();

// Dispose
ui.dispose();

// Enables/Disables touch & resize handling (disabled by defalut)
ui.enable();
ui.disable();
```

#### 2. [PUI Markup Language](https://github.com/generaloss/jpize-ui-idea-plugin) Example:
#### Java:
``` java
Texture background = new Texture("background.png");
Font font = FontLoader.getDefaultBold();

// create loader, put resources
PuiLoader loader = new PuiLoader();
loader.setRes("background", background);
loader.setRes("font", font);

// create ui context
ui = loader.loadCtxRes("view.pui");
ui.enable();

...
// render loop
Gl.clearColorBuffer();
ui.render();
```
#### PUI File:
[Install IDEA plugin for .PUI support](https://plugins.jetbrains.com/plugin/23406-jpize-ui)
``` pui
# Root component (Vertical Box)
@VBox {
    # Parameters
    margin: (0.05rw, 0.01rw, 0.05rw, 0.01rw) # (top, left, bottom, right)
    background: {
        image: !background # BG image
        color.a: 0.4 # BG alpha
    }

    # Components:
    @Button (0.4rw, 0.14ap, "Button", !font, 0.7rh) {
        padding: (auto, zero, auto, zero) # (top, left, bottom, right)
    }
    @Slider (0.4rw, 0.1ap) {
        padding: (0.02rw, zero, auto, zero)
    }
    @TextField (0.4rw, 0.12ap, !font) {
        padding: (0.02rw, zero, auto, zero)
        hint: "hint"
    }
    @ScrollView (0.4rw, wrap_content) {
        padding: (0.02rw, zero, auto, zero)
        margin: (zero, 0.01rw, zero, 0.01rw)
        background.color.a: 0.8

        # ScrollView Component:
        @TextView ("The\nquick\nbrown\nfox\njumps\nover\nthe\nlazy\ndog", !font, 0.1rw) { }
    }
}
```
#### Result:
![preview](preview.gif)

---

## Bugs and Feedback
For bugs, questions and discussions please use the [GitHub Issues](https://github.com/generaloss/jpize-engine/issues).
