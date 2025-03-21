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
        TerminalRegion prevCardRegion = new TerminalRegion(region.topLeftCol(), region.topLeftRow(),
                region.topLeftCol(), region.topLeftRow());
        for (int i = 0; i < cardResources.length; i++) {
            String[] info;
            try {
                info = service.readFile(cardResources[i]).split(":\n");
                assert(info.length == 4);
            } catch (IOException e) {
                return;
            }
            int cost = Integer.valueOf(info[0]);
            String description = info[1];
            String ascii = info[2];
            // col + 2 is for border, +6 - padding
            Widget card = new Card(new TerminalPosition(prevCardRegion.botRightCol() + 6,
                        prevCardRegion.topLeftRow()), ascii, cost, description);
            prevCardRegion = card.getRegion();
            this.widgets.add(card);
        }

        // didn't figure out how to position cards better
        this.widgets = this.widgets.stream().map(w -> (Widget) new Border(w)).toList();
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
