package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.utils.StringUtil;
import org.andnekon.view.tui.TerminalRegion;

/** Card */
public class Card implements Widget {

    public static final int CARD_WIDTH = 12;

    private Widget nameWidget;
    private Widget costWidget;
    private Widget descWidget;
    private Widget asciiWidget;

    public Card(TerminalPosition topLeft, String name, String ascii, int cost, String description) {
        name = StringUtil.wrap(name, CARD_WIDTH - 2); // border
        description = StringUtil.wrap(description, CARD_WIDTH);

        costWidget =
                new SingleLine(
                        String.valueOf(cost),
                        new TerminalPosition(topLeft.getColumn(), topLeft.getRow()));
        nameWidget = new MultiLine(costWidget.getRegion().rightCol() + 2, topLeft.getRow(), name);
        descWidget =
                new MultiLine(
                        topLeft.getColumn(), 1 + nameWidget.getRegion().botRow(), description);
        asciiWidget = new MultiLine(topLeft.getColumn(), descWidget.getRegion().botRow(), ascii);
    }

    @Override
    public void draw(Screen screen) {
        nameWidget.draw(screen);
        costWidget.draw(screen);
        descWidget.draw(screen);
        asciiWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        TerminalRegion left = costWidget.getRegion();
        return new TerminalRegion(
                left.leftCol(),
                left.topRow(),
                left.leftCol() + CARD_WIDTH,
                asciiWidget.getRegion().botRow());
    }
}
