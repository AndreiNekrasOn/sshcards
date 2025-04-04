package org.andnekon.view.tui.widgets.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.ActiveWidget;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Card;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CardHand in constructor cardResources must be filled with dummies on init, and then refilled with
 * {@code fill} before drawn
 */
public class CardHand implements ActiveWidget {

    private TerminalRegion region;

    private BattleManager manager;

    private List<Widget> widgets;

    public CardHand(
            AsciiReaderService service,
            List<org.andnekon.game.action.Card> hand,
            TerminalRegion region) {
        this(service, hand, region, 0);
    }

    public CardHand(
            AsciiReaderService service,
            List<org.andnekon.game.action.Card> hand,
            TerminalRegion region,
            int cardIdxOffset) {
        this.region = region;
        this.widgets = new ArrayList<>();
        TerminalRegion prevCardRegion =
                new TerminalRegion(
                        region.leftCol(),
                        region.topRow() + 2,
                        region.leftCol(),
                        region.topRow() + 2);
        for (int i = 0; i < hand.size(); i++) {
            var cardInfo = hand.get(i);
            String[] info = new String[4];
            info[0] = cardInfo.getName();
            info[1] = String.valueOf(cardInfo.getCost());
            info[2] = cardInfo.getDescription();
            try {
                info[3] = service.readFile(cardInfo.getArt());
            } catch (IOException e) {
                info[3] = "ERROR";
            }
            assert (info.length == 4);
            Widget card = buildCardWidget(info, prevCardRegion, i + cardIdxOffset);
            prevCardRegion = card.getRegion();
            this.widgets.add(card);
        }
        this.region.setBottomRight(
                this.widgets.get(this.widgets.size() - 1).getRegion().getBottomRight());
    }

    private Widget buildCardWidget(String[] info, TerminalRegion prevCardRegion, int idx) {
        String name = info[0];
        int cost = Integer.valueOf(info[1]);
        String description = info[2];
        String ascii = info[3];
        // col + 4 is for padding
        // row  +1 fixes border
        Widget card =
                new Card(
                        new TerminalPosition(
                                prevCardRegion.rightCol() + 4, prevCardRegion.topRow() + 1),
                        name,
                        ascii,
                        cost,
                        description);
        card = new Border(card);
        var cRegion = card.getRegion();
        int middle = cRegion.leftCol() + (cRegion.rightCol() - cRegion.leftCol()) / 2;
        Widget selectIdx =
                new SingleLine(
                        String.valueOf(1 + idx),
                        new TerminalPosition(middle, cRegion.topRow() - 1));
        this.widgets.add(selectIdx);
        return card;
    }

    @Override
    public void draw(Screen screen) {
        for (Widget widget : widgets) {
            widget.draw(screen);
        }
    }

    @Override
    public TerminalRegion getRegion() {
        return region;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setActive(boolean state) {}
}
