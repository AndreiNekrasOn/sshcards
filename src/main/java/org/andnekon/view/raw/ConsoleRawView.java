package org.andnekon.view.raw;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.entity.Player;
import org.andnekon.game.state.State;
import org.andnekon.view.HelpType;
import org.andnekon.view.Reader;
import org.andnekon.view.repl.ConsoleView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// TODO: consider making this final
// Since in this case Reader uses the Screen of the View, I decided
// to join them in one class. Violates SRP
public class ConsoleRawView extends ConsoleView implements Reader {

    private UnixTerminal terminal;

    private Screen screen;

    // to avoid constantly casting to type
    private ConsoleRawDisplayer crdHelper;

    private ConsoleRawView(GameSession session) throws IOException {
        super(session);
    }

    public ConsoleRawView(GameSession session, InputStream is, OutputStream os) throws IOException {
        super(session);
        this.terminal = new UnixTerminal(is, os, Charset.defaultCharset());
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        crdHelper = ConsoleRawDisplayer.builder(session).screen(screen).build();
        helper = crdHelper;
    }

    @Override
    public void display(State state) {
        screen.clear();
        super.display(state);
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showBattle() {
        crdHelper.reset();
        crdHelper.help(HelpType.BATTLE_INFO);
        crdHelper.help(HelpType.BATTLE_ENEMY_INTENTS);
        Player player = session.getPlayer();
        List<Card> deck = new ArrayList<>();
        deck.addAll(player.getShotDeck().getHand());
        deck.addAll(player.getArmorDeck().getHand());
        crdHelper.choice(deck);
        crdHelper.help(HelpType.ACTIONS);
        crdHelper.prompt("What do you do?");
    }

    @Override
    public void welcome() {
        crdHelper.reset();
        super.welcome();
    }

    /** Reader * */
    @Override
    public String read() {
        KeyStroke keyStroke = null;
        StringBuilder builder = new StringBuilder();
        int inputSize = 0;
        // screen.setCursorPosition(new TerminalPosition(crdHelper.getPrintCol(),
        // crdHelper.getPrintRow()));
        screen.setCursorPosition(new TerminalPosition(0, 30));
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // TODO: rethink that, maybe redraw every character. Maybe no need to redraw at all
            while (keyStroke == null || keyStroke.getKeyType() != KeyType.Enter) {
                if (keyStroke != null) {
                    builder.append(keyStroke.getCharacter());
                    drawInput(keyStroke.getCharacter(), inputSize++);
                }
                keyStroke = screen.readInput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        crdHelper.reset();
        return builder.toString().strip();
    }

    @Override
    protected void showNavigation() {
        crdHelper.reset();
        crdHelper.choice(
                session.getEnemyNavLeft().toString(), session.getEnemyNavRight().toString());
        crdHelper.prompt("Where do you go?");
    }

    @Override
    protected void showQuitConfirm() {
        crdHelper.reset();
        crdHelper.prompt("Are you sure you want to quit? [y/n]");
    }

    @Override
    protected void showReward() {
        crdHelper.reset();
        crdHelper.message("Congrats, reward");
    }

    // TODO: c can be null if in telnet you press return without input.
    // What should we do with it?
    private void drawInput(Character c, int inputSize) throws IOException {
        if (c == null) {
            return;
        }
        TerminalPosition inPos = new TerminalPosition(inputSize, 30);
        screen.setCharacter(inPos, TextCharacter.fromCharacter(c)[0]);
        screen.setCursorPosition(inPos.withRelativeColumn(1));
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
