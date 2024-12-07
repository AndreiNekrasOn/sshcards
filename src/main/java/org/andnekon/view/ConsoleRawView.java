package org.andnekon.view;

import java.io.IOException;

import org.andnekon.game.GameSession;
import org.andnekon.game.state.State;
import org.andnekon.view.formatter.ConsoleRawDisplayer;
import org.andnekon.view.formatter.DisplayOptions;
import org.andnekon.view.formatter.Displayer;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

// TODO: move from jline to lanterna, lanterna has no external dependencies
public class ConsoleRawView extends ConsoleView {

    Terminal terminal;
    public LineReaderImpl reader; // TODO: not public

    public ConsoleRawView(GameSession session) {
        super(session);
        try {
            terminal = TerminalBuilder.builder()
                .system(true)
                .build();
            reader = (LineReaderImpl) LineReaderBuilder.builder()
                .terminal(terminal)
                .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper = new ConsoleRawDisplayer(session, terminal);
        terminal.enterRawMode();
    }

    @Override
    public void display(State state) {
        reader.clearScreen();
        super.display(state);
        reader.flush();
    }

    @Override
    public void showBattle() {
        Displayer currentDisplayer = helper;
        if (session.getHelpType() == HelpType.WRONG_INPUT) {
            currentDisplayer = currentDisplayer.withSettings(DisplayOptions.COLORED.id());
        }
        currentDisplayer.help(HelpType.BATTLE_INFO);
        currentDisplayer.help(HelpType.BATTLE_ENEMY_INTENTS);
        currentDisplayer.choice(session.getPlayer().getBattleDeck().toArray());
        currentDisplayer.help(HelpType.ACTIONS);
        helper.prompt("What do you do?");
    }
}
