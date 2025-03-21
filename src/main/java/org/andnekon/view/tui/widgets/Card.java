package org.andnekon.view.tui.widgets;

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


    // TODO: replace parameters with cardInfoService
    public Card(TerminalPosition topLeft, String art, int cost, String description) {
        art = StringUtil.wrap(art, CARD_WIDTH - 2); // border
        cost = cost;
        description = StringUtil.wrap(description, CARD_WIDTH - 2);

        int height = StringUtil.countChar(art, '\n');
        height += 1; // cost
        height += StringUtil.countChar(description, '\n');
        // height += 2 + 2 + 2; // border

        TerminalRegion region = new TerminalRegion(topLeft.getColumn(), topLeft.getRow(),
                // topLeft.getColumn() + 2 + CARD_WIDTH, topLeft.getRow() + height);
                topLeft.getColumn() + CARD_WIDTH, topLeft.getRow() + height);
        artWidget = new MultiLine(region.topLeftCol(), region.topLeftRow(), art);
        artWidget = new Border(artWidget);
        costWidget = new SingleLine(String.valueOf(cost),
                new TerminalPosition(artWidget.getRegion().botRightRow() + 1,
                    artWidget.getRegion().topLeftRow()));
        costWidget = new Border(costWidget);
        descWidget = new MultiLine(costWidget.getRegion().topLeftCol(),
                costWidget.getRegion().botRightRow() + 1, description);
        descWidget = new Border(descWidget);
    }

    @Override
    public void draw(Screen screen) {
        artWidget.draw(screen);
        costWidget.draw(screen);
        descWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return new TerminalRegion(artWidget.getRegion().topLeftCol(), artWidget.getRegion().topLeftCol(),
                descWidget.getRegion().botRightCol(), descWidget.getRegion().botRightRow());
    }
}
