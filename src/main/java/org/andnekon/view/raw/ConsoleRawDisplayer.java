package org.andnekon.view.raw;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.view.repl.ConsoleDisplayer;

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

    private static TerminalPosition printPos = new TerminalPosition(0, 0);

    public static ConsoleRawDisplayerBuilder builder(GameSession session) {
        return new ConsoleRawDisplayerBuilder(session);
    }

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
            TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.putString(printPos.withColumn(0).withRelativeRow(row), lines[row]);
        }
        int newLine = format.endsWith("\n") ? 1 : 0;
        printPos =
                printPos.withColumn(lines[lines.length - 1].length())
                        .withRelativeRow(lines.length + newLine);
    }
}
