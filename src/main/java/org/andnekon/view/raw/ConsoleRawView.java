package org.andnekon.view.raw;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.entity.Player;
import org.andnekon.game.state.State;
import org.andnekon.game.state.State.Type;
import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.DisplayOptions;
import org.andnekon.view.HelpType;
import org.andnekon.view.Reader;
import org.andnekon.view.repl.ConsoleView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// TODO: separate from Reader
public class ConsoleRawView extends ConsoleView implements Reader {

    private UnixTerminal terminal;

    private Screen screen;

    // to avoid constantly casting to type
    private ConsoleRawDisplayer crdHelper;

    public ConsoleRawView(GameSession session, InputStream is, OutputStream os) throws IOException {
        super(session, os);
        this.terminal = new UnixTerminal(is, os, Charset.defaultCharset());
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        crdHelper = ConsoleRawDisplayer.builder(session).screen(screen).os(os).build();
        helper = crdHelper;
    }

    @Override
    public void display(State state) {
        screen.clear();
        super.display(state);
        helper.flush();
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
        crdHelper.withSettings(DisplayOptions.MENU.id()).choice(deck.toArray());
        crdHelper.help(HelpType.ACTIONS);
        crdHelper.prompt("What do you do?");
    }

    @Override
    public void welcome() {
        crdHelper.reset();
        super.welcome();
    }

    /** Reader */
    @Override
    public String read() {
        screen.setCursorPosition(new TerminalPosition(0, screen.getTerminalSize().getRows() - 1));
        helper.flush();
        try {
            KeyStroke key = screen.readInput();
            return KeyStrokeUtil.isCharacter(key) ? modifyInput(key.getCharacter()) : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Modifies recieved input character to match the controller's needs. In {@code TuiView} this is
     * hidden in {@code TuiWindow} processing their input before passing it through
     */
    private String modifyInput(char c) {
        if (state.getType() == Type.BATTLE) {
            if ('0' <= c && c <= '9') {
                int i = c - '0';
                int shotHandSize = session.getPlayer().getShotDeck().getHand().size();
                if (i <= shotHandSize) {
                    return "s" + i;
                }
                return "a" + (i - shotHandSize);
            }
        }
        return String.valueOf(c);
    }

    @Override
    protected void showNavigation() {
        crdHelper.reset();
        crdHelper.choice((Object[]) session.getNavigationManager().getNavigationOptionsArray());
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
        crdHelper
                .withSettings(DisplayOptions.MENU.id())
                .choice(session.getRewardManager().getRewardOptions().toArray());
        crdHelper.prompt("Choose a card");
    }
}
