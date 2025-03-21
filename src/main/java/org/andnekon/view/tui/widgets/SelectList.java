package org.andnekon.view.tui.widgets;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

/**
 * List with an active selection
 */
public class SelectList implements ActiveWidget {

    private int selectedIndex;
    private TerminalRegion region;
    private String[] items;

    public SelectList(int col, int row, String[] items) {
        int maxLength = Arrays.stream(items).mapToInt(String::length).max().orElse(0);
        this.region = new TerminalRegion(col, row, col + maxLength - 1, row + items.length - 1);
        this.items = items;
        this.selectedIndex = -1;
    }

    @Override
    public void draw(Screen screen) {
        for (int i = 0; i < items.length; i++) {
            if (isActive() && i == selectedIndex) {
                drawLine(screen, TextColor.ANSI.GREEN, region.topLeftCol(), region.topLeftRow() + i, items[i]);
            } else {
                drawLine(screen, TextColor.ANSI.WHITE, region.topLeftCol(), region.topLeftRow() + i, items[i]);
            }
        }
    }

    private void drawLine(Screen screen, TextColor color, int col, int row, String line) {
        TextCharacter[] tcs = TextCharacter.fromString(line, color, TextColor.ANSI.DEFAULT);
        for (int i = 0; i < tcs.length; i++) {
            screen.setCharacter(col + i, row, tcs[i]);
        }
    }

    public SelectList setIndex(int i) {
        this.selectedIndex = i;
        return this;
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }

    @Override
    public boolean isActive() {
        return selectedIndex != -1;
    }

    @Override
    public void setActive(boolean state) {
        this.selectedIndex = 0;
    }
}
