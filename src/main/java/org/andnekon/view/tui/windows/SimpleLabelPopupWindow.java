package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.Label;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

public class SimpleLabelPopupWindow extends PopupWindow {

    private final String message;
    private Label content;

    public SimpleLabelPopupWindow(StatefulMultiWindowTextGui gui, String message) {
        super(gui);
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
