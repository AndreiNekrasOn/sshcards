package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/** Options */
public class Options implements Widget, Iterator {

    final ActiveWidget[] options;

    public Options(TerminalPosition topLeft, String[] lines, int... selection) {
        assert selection.length <= lines.length;
        this.options = new ActiveWidget[lines.length];
        List<Integer> selList = Arrays.stream(selection).boxed().toList();
        TerminalPosition previous = topLeft;
        for (int i = 0; i < options.length; i++) {
            this.options[i] = new SingleLineActive(lines[i], previous);
            this.options[i].setActive(selList.contains(i)); // linear is faster on small size
            previous =
                    new TerminalPosition(
                            this.options[i].getRegion().leftCol(),
                            this.options[i].getRegion().botRow() + 1);
        }
    }

    @Override
    public void draw(Screen screen) {
        for (int i = 0; i < options.length; i++) {
            options[i].draw(screen);
        }
    }

    @Override
    public TerminalRegion getRegion() {
        TerminalRegion top = options[0].getRegion();
        TerminalRegion bot = options[options.length - 1].getRegion();
        int widthRight =
                Arrays.stream(options)
                        .mapToInt(o -> o.getRegion().rightCol())
                        .max()
                        .orElse(bot.rightCol());
        return new TerminalRegion(top.leftCol(), top.topRow(), widthRight, bot.botRow());
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasNext'");
    }

    @Override
    public Object next() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'next'");
    }
}
