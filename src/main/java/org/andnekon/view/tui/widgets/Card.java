package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.utils.StringUtil;
import org.andnekon.view.tui.TerminalRegion;

import java.util.function.Function;

/** Card */
public class Card implements Widget {

    public static final int CARD_WIDTH = 12;

    private Widget nameWidget;
    private Widget costWidget;
    private Widget descWidget;

    private Function<Widget, Integer> startCol = w -> w.getRegion().leftCol();
    private Function<Widget, Integer> endRow = w -> w.getRegion().botRow();

    public Card(TerminalPosition topLeft, String name, int cost, String description) {
        name = StringUtil.wrap(name, CARD_WIDTH); // border
        description = StringUtil.wrap(description, CARD_WIDTH);

        nameWidget = new MultiLine(topLeft.getColumn(), topLeft.getRow(), name);
        costWidget =
                new SingleLine(
                        String.valueOf(cost),
                        new TerminalPosition(
                                startCol.apply(nameWidget), 1 + endRow.apply(nameWidget)));
        descWidget =
                new MultiLine(
                        startCol.apply(costWidget), 1 + endRow.apply(costWidget), description);
    }

    @Override
    public void draw(Screen screen) {
        nameWidget.draw(screen);
        costWidget.draw(screen);
        descWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        TerminalRegion left = nameWidget.getRegion();
        return new TerminalRegion(
                left.leftCol(),
                left.topRow(),
                left.leftCol() + CARD_WIDTH,
                descWidget.getRegion().botRow());
    }
}
