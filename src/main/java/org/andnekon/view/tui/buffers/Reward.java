package org.andnekon.view.tui.buffers;

import org.andnekon.game.action.Card;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Widget;
import org.andnekon.view.tui.widgets.battle.CardHand;

import java.util.ArrayList;
import java.util.List;

public class Reward extends Popup {

    private List<Widget> widgets = new ArrayList<>();

    public Reward(TerminalRegion region, List<Card> hand, AsciiReaderService service) {
        super(region);
        Widget cards = new CardHand(service, hand, region);
        widgets.add(cards);
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
