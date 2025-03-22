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

import com.googlecode.lanterna.TerminalPosition;

public class Reward extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    // todo -> service
    public Reward(TerminalRegion region, String[] cardResources, AsciiReaderService service) {
        super(region);

        // TODO: CardHandWidget
        // TODO: this is bad...
        // single-point region so the card placement works correctly
        // +1 is offset for selection line;
        TerminalRegion prevCardRegion = new TerminalRegion(region.leftCol(), region.topRow() + 2,
                region.leftCol(), region.topRow() + 2);
        for (int i = 0; i < cardResources.length; i++) {
            String[] info;
            try {
                info = service.readFile(cardResources[i]).split(":\n");
                assert(info.length == 4);
            } catch (IOException e) {
                return;
            }
            int cost = Integer.valueOf(info[0]); // TODO: cost -> cost+type, params?
            String description = info[1];
            String ascii = info[2];
            // col + 2 is for border, +6 - padding
            Widget card = new Card(new TerminalPosition(prevCardRegion.rightCol() + 6,
                        prevCardRegion.topRow()), ascii, cost, description);
            prevCardRegion = card.getRegion();

            Widget selectIdx = new SingleLine(String.valueOf(i),
                    new TerminalPosition(card.getRegion().leftCol(), card.getRegion().topRow() - 2));
            this.widgets.add(selectIdx);
            this.widgets.add(card);
        }

        // didn't figure out how to position cards better
        for (int i = 0; i < widgets.size(); i++) {
            Widget widget = widgets.get(i);
            if (widget instanceof Card) {
                widgets.set(i, new Border(widget));
            }
        }
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
