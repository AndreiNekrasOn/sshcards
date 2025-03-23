package org.andnekon.view.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.state.State;
import org.andnekon.game.state.State.Type;
import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TuiReader implements Reader {

    Logger logger = LoggerFactory.getLogger(TuiReader.class);

    private List<KeyStroke> buffer;
    private TuiManager manager;

    public TuiReader(TuiManager manager) {
        this.buffer = new ArrayList<>();
        this.manager = manager;
    }

    @Override
    public String read() {
        readKeys();
        String result = KeyStrokeUtil.keysToString(buffer);
        buffer.clear();
        result = modifyInput(result.toCharArray()[0]);
        this.manager.processSpecialInput(result);
        return result;
    }

    /**
     * Interactively reads key strokes from the screen, storring the result in the internal buffer
     */
    public void readKeys() {
        buffer.clear();
        Screen screen = this.manager.getScreen();
        // can't hide cursor over telnet
        screen.setCursorPosition(new TerminalPosition(0, screen.getTerminalSize().getRows() - 1));
        try {
            KeyStroke key = screen.readInput(); // blocks
            if (key != null) {
                buffer.add(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifies recieved input character to match the controller's needs.
     */
    private String modifyInput(char c) {
        State state = this.manager.getView().getState();
        if (state != null && state.getType() == Type.BATTLE) {
            logger.info("modifyInput for battle");
            if ('0' <= c && c <= '9') {
                int i = c - '0';
                int shotHandSize = this.manager.getSession().getPlayer().getShotDeck().getHand().size();
                if (i <= shotHandSize) {
                    return "s" + i;
                }
                return "a" + (i - shotHandSize);
            }
        }
        return String.valueOf(c);
    }

    public List<KeyStroke> getBuffer() {
        return this.buffer;
    }
}
