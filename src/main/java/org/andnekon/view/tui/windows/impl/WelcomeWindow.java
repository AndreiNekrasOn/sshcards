package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.view.tui.windows.PopupWindow;

public class WelcomeWindow extends PopupWindow {

    public WelcomeWindow() {
        super();
    }

    @Override
    public void setup() {
        Panel content = new Panel();

        Label title = new Label("SUPER SPACE HEROES");
        Label secondTitle = new Label("Or spacecards, haven.t decided on the name yet");

        content.addComponent(title);
        content.addComponent(secondTitle);
        this.setComponent(content);
    }
}
