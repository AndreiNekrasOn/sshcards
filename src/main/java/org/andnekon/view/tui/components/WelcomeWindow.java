package org.andnekon.view.tui.components;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

public class WelcomeWindow extends AbstractTuiWindow {

    public WelcomeWindow(StatefulMultiWindowTextGui gui) {
        super(gui);
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
