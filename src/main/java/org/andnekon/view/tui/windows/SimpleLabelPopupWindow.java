package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.Label;

public class SimpleLabelPopupWindow extends PopupWindow {

    private final String message;
    private Label content;

    public SimpleLabelPopupWindow(String message) {
        super();
        this.message = message;
    }

    @Override
    public void setup() {
        content = new Label("");
        this.setComponent(content);
    }

    @Override
    public void prepare() {
        this.content.setText(message);
    }
}
