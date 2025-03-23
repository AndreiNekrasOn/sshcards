package org.andnekon.view.tui.buffers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Card;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;
import org.andnekon.view.tui.widgets.battle.CardHand;

import com.googlecode.lanterna.TerminalPosition;

public class Reward extends Buffer {

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
