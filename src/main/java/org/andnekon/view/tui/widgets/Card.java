package org.andnekon.view.tui.widgets;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.andnekon.utils.StringUtil;
import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

/**
 * Card
 */
public class Card implements Widget {

    public static final int CARD_WIDTH = 12;

    private Widget artWidget;
    private Widget costWidget;
    private Widget descWidget;

    private Function<Widget, Integer> startCol = w -> w.getRegion().leftCol();
    private Function<Widget, Integer> endRow = w -> w.getRegion().botRow();

    // TODO: replace parameters with cardInfoService
    public Card(TerminalPosition topLeft, String art, int cost, String description) {
        art = StringUtil.wrap(art, CARD_WIDTH); // border
        description = StringUtil.wrap(description, CARD_WIDTH);


        artWidget = new MultiLine(topLeft.getColumn(), topLeft.getRow(), art);
        costWidget = new SingleLine(String.valueOf(cost),
                new TerminalPosition(startCol.apply(artWidget), 1 + endRow.apply(artWidget)));
        descWidget = new MultiLine(startCol.apply(costWidget), 1 + endRow.apply(costWidget), description);
    }

    @Override
    public void draw(Screen screen) {
        artWidget.draw(screen);
        costWidget.draw(screen);
        descWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return new TerminalRegion(artWidget.getRegion().leftCol(), artWidget.getRegion().topRow(),
                descWidget.getRegion().rightCol(), descWidget.getRegion().botRow());
    }
}
