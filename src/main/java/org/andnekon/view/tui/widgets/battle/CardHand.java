package org.andnekon.view.tui.widgets.battle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.ActiveWidget;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Card;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

/**
 * CardHand
 * in constructor cardResources must be filled with dummies on init, and then refilled
 * with {@code fill} before drawn
 */
public class CardHand implements ActiveWidget {

    private TerminalRegion region;

    private BattleManager manager;

    private List<Widget> widgets;

    public CardHand(AsciiReaderService service, String[] cardResources, TerminalRegion region) {
        this.manager = manager;
        this.region = region;
        this.widgets = new ArrayList<>();

        TerminalRegion prevCardRegion = new TerminalRegion(region.leftCol(), region.topRow() + 2,
                region.leftCol(), region.topRow() + 2);
        for (int i = 0; i < cardResources.length; i++) {
            try {
                String[] info = service.readFile(cardResources[i]).split(":\n");
                assert(info.length == 4);
                Widget card = buildCardWidget(info, prevCardRegion, i);
                prevCardRegion = card.getRegion();
                this.widgets.add(card);
            } catch (IOException e) {
                return;
            }
        }
        this.region.setBottomRight(
                this.widgets.get(this.widgets.size() - 1).getRegion().getBottomRight()
                );
        //
        // // didn't figure out how to position cards better
        // for (int i = 0; i < widgets.size(); i++) {
        //     Widget widget = widgets.get(i);
        //     if (widget instanceof Card) {
        //         widgets.set(i, new Border(widget));
        //     }
        // }
    }

    private Widget buildCardWidget(String[] info, TerminalRegion prevCardRegion, int idx) {
        int cost = Integer.valueOf(info[0]); // TODO: cost -> cost+type, params?
        String description = info[1];
        String ascii = info[2];
        // col + 4 is for padding
        // row  +1 fixes border
        Widget card = new Card(new TerminalPosition(prevCardRegion.rightCol() + 4,
                    prevCardRegion.topRow() + 1), ascii, cost, description);
        card = new Border(card);
        // Widget selectIdx = new SingleLine(String.valueOf(idx),
        //         new TerminalPosition(card.getRegion().leftCol(), card.getRegion().topRow() - 2));
        // this.widgets.add(selectIdx);
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

    // TODO: implement ActiveWidget
	@Override
	public boolean isActive() {
        return true;
	}

	@Override
	public void setActive(boolean state) {
	}


}
