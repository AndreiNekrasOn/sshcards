package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

/** Window with a status line on the top, {@code content}, and a help line on the bottom */
public abstract class MainWindow extends AbstractTuiWindow {

    protected Panel content;

    public MainWindow(StatefulMultiWindowTextGui gui) {
        super(gui); // setup is called here

        Panel container = new Panel();
        container.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

        Panel messages = new Panel();
        messages.addComponent(new Label("Super Space Heros!"));
        container.addComponent(messages);
        container.addComponent(new Separator(Direction.HORIZONTAL));

        container.addComponent(content);
        container.addComponent(new Separator(Direction.HORIZONTAL));

        container.addComponent(new Panel().addComponent(new Label("q - quit, ? - help")));

        this.setComponent(container);
    }
}
