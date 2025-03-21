package org.andnekon.view.tui.buffers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Card;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.TerminalPosition;

public class Reward extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    // todo -> service
    public Reward(TerminalRegion region, String[] cardResources, AsciiReaderService service) {
        super(region);

        for (int i = 0; i < cardResources.length; i++) {
            String[] info;
            try {
                info = service.readFile(cardResources[i]).split(":\n");
            } catch (IOException e) {
                return;
            }
            int cost = Integer.valueOf(info[0]);
            String description = info[1];
            String ascii = String.join("\n", Arrays.stream(info).skip(2).collect(Collectors.toList()));
            Widget card = new Card(new TerminalPosition(region.topLeftCol() + i * 2 *  Card.CARD_WIDTH + 1,
                        region.topLeftRow()), ascii, cost, description);
            this.widgets.add(card);
        }
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
