package org.andnekon.view.tui.buffers;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Widget;
import org.andnekon.view.tui.widgets.battle.CardHand;

import java.util.ArrayList;
import java.util.List;

public class Reward extends Popup {

    private List<Widget> widgets = new ArrayList<>();

    // todo -> service
    public Reward(TerminalRegion region, String[] cardResources, AsciiReaderService service) {
        super(region);
        Widget cards = new CardHand(service, cardResources, region);
        widgets.add(cards);
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
