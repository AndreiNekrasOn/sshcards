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

        TerminalRegion prevCardRegion = this.region;
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
            // col + 2 is for border, +3 - padding
            Widget card = new Card(new TerminalPosition(prevCardRegion.topLeftCol(),
                        prevCardRegion.topLeftRow()), ascii, cost, description);
            card = new Border(card);
            prevCardRegion = card.getRegion();
            this.widgets.add(card);
        }
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
