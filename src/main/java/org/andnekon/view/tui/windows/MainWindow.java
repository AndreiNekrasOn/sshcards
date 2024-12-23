package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Separator;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.util.List;

/** Window with a status line on the top, {@code content}, and a help line on the bottom */
public abstract class MainWindow extends AbstractTuiWindow {

    protected Panel content;

    public MainWindow(StatefulMultiWindowTextGui gui) {
        super(gui); // setup is called here
        setHints(List.of(Hint.FIT_TERMINAL_WINDOW, Hint.CENTERED, Hint.FULL_SCREEN));

        Panel container = new Panel();

        Panel messages = new Panel();
        messages.addComponent(new Label("Super Space Heros!"));
        container.addComponent(messages);
        container.addComponent(new Separator(Direction.HORIZONTAL));

        container.addComponent(content);
        container.addComponent(new Separator(Direction.HORIZONTAL));

        // first column - generic help, second - specific
        Panel helpPanel = new Panel(new GridLayout(2));
        helpPanel.addComponent(new Label("[q, ^C] - quit, ? - help."));
        Label controlsLabel = new Label(getControls());
        helpPanel.addComponent(controlsLabel);
        container.addComponent(helpPanel);

        this.setComponent(container);
    }

    protected String getControls() {
        return "";
    }
}
