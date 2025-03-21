package org.andnekon.view.tui.widgets;

import java.io.IOException;
import java.util.Arrays;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class MultiLine implements Widget {

    private String[] ascii;
    private TerminalRegion region;

    public MultiLine(int col, int row, String ascii) {
        this.ascii = ascii.split("\n");

        int width = Arrays.stream(this.ascii).mapToInt(String::length).max().orElse(0);
        int height = this.ascii.length;
        this.region = new TerminalRegion(col, row, col + width, row + height);
    }

    @Override
    public void draw(Screen screen) {
        for (int i = 0; i < ascii.length; i++) {
            TextCharacter[] tcs = TextCharacter.fromString(ascii[i]);
            for (int j = 0; j < tcs.length; j++) {
                screen.setCharacter(this.region.topLeftCol() + j, this.region.topLeftRow() + i, tcs[j]);
            }
        }
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }


}
