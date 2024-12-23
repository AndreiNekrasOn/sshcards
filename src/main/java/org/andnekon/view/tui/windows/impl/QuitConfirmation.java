package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.PopupWindow;

import java.util.List;

public class QuitConfirmation extends PopupWindow {

    public QuitConfirmation(StatefulMultiWindowTextGui gui) {
        super(gui);
    }

    @Override
    public void setup() {
        Label sure = new Label("Are you sure you want to quit? [y/n]");
        this.setComponent(sure);
    }

    @Override
    protected void postRead() {
        List<KeyStroke> buffer = gui.getReader().getBuffer();
        for (KeyStroke key : buffer) {
            if (KeyStrokeUtil.compareType(key, KeyType.Escape)) {
                buffer.clear();
                buffer.add(KeyStroke.fromString("n"));
                return;
            }
        }
    }
}
