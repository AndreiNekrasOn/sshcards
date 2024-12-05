package org.andnekon.view.formatter;

import java.io.PrintWriter;

import org.andnekon.game.GameSession;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

public class ConsoleRawDisplayer extends ConsoleDisplayer {

    private PrintWriter writer;
    private Terminal terminal;

    private ConsoleRawDisplayer(GameSession session) {
        super(session);
    }

    public ConsoleRawDisplayer(GameSession session, Terminal terminal) {
        super(session);
        this.terminal = terminal;
        this.writer = terminal.writer();
    }

    public ConsoleRawDisplayer(final GameSession session, final int settings, Terminal terminal) {
        super(session, settings);
        this.terminal = terminal;
        this.writer = terminal.writer();
    }


    @Override
    protected void printf(String format, Object... params) {
        format = transformTemplateString(format);
        writer.print(new AttributedStringBuilder()
            .append(String.format(format, params))
            .style(AttributedStyle.BOLD.foreground(AttributedStyle.RED))
            .toAnsi());
        // writer.format(format, params);
    }

    @Override
    public Displayer withSettings(final int settings) {
        return new ConsoleRawDisplayer(session, settings, terminal);
    }
}

