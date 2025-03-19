package org.andnekon.view.tui.widgets;

import java.io.IOException;
import java.util.Arrays;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * AsciiArt
 */
public class AsciiArt implements Widget {

    private String[] ascii;
    private TerminalRegion region;

    public AsciiArt(int col, int row, String resource, AsciiReaderService asciiReaderService) {
        String lines;
        try {
            lines = asciiReaderService.readFile(resource);
        } catch (IOException e) {
            e.printStackTrace();
            lines = "<ERROR>";
        }
        this.ascii = lines.split("\n");

        int width = Arrays.stream(this.ascii).mapToInt(String::length).max().orElse(0);
        int height = this.ascii.length;
        this.region = new TerminalRegion(col, row, col + width, row + height);
    }

    @Override
    public void draw(Screen screen) {
        for (int i = 0; i < ascii.length; i++) {
            TextCharacter[] tcs = TextCharacter.fromString(ascii[i]);
            for (int j = 0; j < tcs.length; j++) {
                screen.setCharacter(this.region.topLeftCol() + i, this.region.topLeftRow() + j, tcs[j]);
            }
        }
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }


}
