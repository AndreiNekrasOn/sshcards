package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;

/** BottomLine decorator */
public class BottomLine implements Widget {

    private Widget widget;

    private String help;

    public BottomLine(Widget widget, String help) {
        this.widget = widget;
        this.help = help;
    }

    @Override
    public void draw(Screen screen) {
        widget.draw(screen);
        TerminalRegion region = widget.getRegion();
        Widget helpWidget =
                new SingleLine(help, new TerminalPosition(region.leftCol(), region.botRow() + 1));
        helpWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRegion'");
    }
}
