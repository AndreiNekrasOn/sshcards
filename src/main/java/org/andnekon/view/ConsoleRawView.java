package org.andnekon.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.andnekon.game.GameSession;
import org.andnekon.game.state.State;
import org.andnekon.view.formatter.ConsoleRawDisplayer;
import org.andnekon.view.reader.Reader;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

// Since in this case Reader uses the Screen of the View, I decided
// to join them in one class. Violates SRP
public class ConsoleRawView extends ConsoleView implements Reader {

    private UnixTerminal terminal;
    private Screen screen;

    // to avoid constantly casting to type
    private ConsoleRawDisplayer crdHelper;

    private InputStream is;
    private OutputStream os;

    public ConsoleRawView(GameSession session) throws IOException {
        super(session);
    }

    private void setup() throws IOException {
        this.terminal = new UnixTerminal(is, os, Charset.defaultCharset());
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        crdHelper = ConsoleRawDisplayer.builder(session)
            .screen(screen)
            .build();
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
    public void showBattle() {
        crdHelper.reset();
        crdHelper.help(HelpType.BATTLE_INFO);
        crdHelper.help(HelpType.BATTLE_ENEMY_INTENTS);
        crdHelper.choice(session.getPlayer().getBattleDeck().toArray());
        crdHelper.help(HelpType.ACTIONS);
        crdHelper.prompt("What do you do?");
    }

    @Override
    public void welcome() {
        crdHelper.reset();
        super.welcome();
    }

    /** Reader **/
    @Override
    public String read() {
        KeyStroke keyStroke = null;
        StringBuilder builder = new StringBuilder();
        int inputSize = 0;
        // screen.setCursorPosition(new TerminalPosition(crdHelper.getPrintCol(), crdHelper.getPrintRow()));
        screen.setCursorPosition(new TerminalPosition(0, 30));
        try { screen.refresh(); } catch (IOException e) { e.printStackTrace(); }
        try {
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
        crdHelper.choice(session.getEnemyNavLeft().display(), session.getEnemyNavRight().display());
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

    private void drawInput(char c, int inputSize) throws IOException {
        TerminalPosition inPos =
            new TerminalPosition(inputSize, 30);
        screen.setCharacter(inPos, TextCharacter.fromCharacter(c)[0]);
        screen.setCursorPosition(inPos.withRelativeColumn(1));
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: get rid of prepare all together, since it was added for ConsoleRawView
    // which has no use for it. Displayer/Reader can accept IOstream on init
    @Override
    public byte[] prepare() {
        return null;
    }

    // TODO: hack-ish way to do this, think this over
    // Since class has complicated configuration, maybe another case for Factory
    public void bind(InputStream is, OutputStream os) throws IOException {
        this.is = is;
        this.os = os;
        setup();
    }
}
