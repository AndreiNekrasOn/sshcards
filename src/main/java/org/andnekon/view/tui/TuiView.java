package org.andnekon.view.tui;

import org.andnekon.game.GameSession;
import org.andnekon.view.Displayer;
import org.andnekon.view.raw.ConsoleRawDisplayer;
import org.andnekon.view.raw.ConsoleRawView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TuiView extends ConsoleRawView {
    private Displayer helper;
    private AsciiReaderService asciiReaderService;

    public TuiView(GameSession session, InputStream is, OutputStream os) throws IOException {
        super(session, is, os);
        // this.terminal = new UnixTerminal(is, os, Charset.defaultCharset());
        // screen = new TerminalScreen(terminal);
        // screen.startScreen();
        helper = new ConsoleRawDisplayer(session, 0, screen);
        asciiReaderService = new AsciiReaderService();
    }

    @Override
    public void welcome() {
        try {
            screen.clear();
            String logo = asciiReaderService.readFile("tui/logo.txt");
            helper.message(logo);
            screen.refresh();
            screen.readInput();
            ; // wait for any input
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
