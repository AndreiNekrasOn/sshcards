package org.andnekon.view.formatter;

import org.andnekon.game.GameSession;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class ConsoleRawDisplayer extends ConsoleDisplayer {

    public static class ConsoleRawDisplayerBuilder {

        Screen screen;
        GameSession session;

        public ConsoleRawDisplayerBuilder(GameSession session) {
            this.session = session;
        }

        public ConsoleRawDisplayer build() {
            return new ConsoleRawDisplayer(this);
        }

        public ConsoleRawDisplayerBuilder screen(Screen screen) {
            this.screen = screen;
            return this;
        }
    }

    // TODO: hack, until displayers are singleton `withSettings` doesn't allow to track printPos
    private static TerminalPosition printPos = new TerminalPosition(0, 0);


    public static ConsoleRawDisplayerBuilder builder(GameSession session) {
        return new ConsoleRawDisplayerBuilder(session);
    }

    // TODO: all displayers (and views?) should be Singleton. How would this affect `withSettings`?
    // one idea is to make user call `complete` that resets settings to the previous state
    private Screen screen;

    public ConsoleRawDisplayer(GameSession session, int settings, Screen screen) {
        super(session, settings);
        this.screen = screen;
    }

    private ConsoleRawDisplayer(GameSession session) {
        super(session);
    }


    private ConsoleRawDisplayer(ConsoleRawDisplayerBuilder builder) {
        super(builder.session);
        this.screen = builder.screen;
    }

    @Override
    public ConsoleRawDisplayer withSettings(int settings) {
        ConsoleRawDisplayer displayer = new ConsoleRawDisplayer(session, settings, screen);
        displayer.printPos = printPos;
        return displayer;
    }
    public void setPosition(TerminalPosition position) {
        printPos = position;
    }

    public void reset() {
        printPos = new TerminalPosition(0, 0);
    }

    public int getPrintRow() {
        return printPos.getRow();
    }

    public int getPrintCol() {
        return printPos.getRow();
    }

    @Override
    protected void printf(String format, Object... params) {
        format = transformTemplateString(format);
        String[] lines = String.format(format, params).split("\n");
        if (lines.length == 0) {
            return;
        }
        for (int row = 0; row < lines.length; row++) {
            TextGraphics tGraphics = screen.newTextGraphics();
            tGraphics.putString(printPos.withColumn(0).withRelativeRow(row), lines[row]);
        }
        int newLine = format.endsWith("\n") ? 1 : 0;
        printPos = printPos
            .withColumn(lines[lines.length - 1].length())
            .withRelativeRow(lines.length + newLine);
    }

    @Override
    public byte[] collect() {
        // TODO: raw mode
        throw new UnsupportedOperationException("Raw mode should be handled differently");
    }

}

