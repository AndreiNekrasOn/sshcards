package org.andnekon.view.repl;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.view.DisplayOptions;
import org.andnekon.view.Displayer;
import org.andnekon.view.HelpType;
import org.andnekon.view.Messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleDisplayer implements Displayer {

    protected GameSession session;

    protected OutputStream os;

    /** DisplayOptions */
    protected int settings = 0;

    protected static final String O_BRACKET = "\\{OBRACKET\\}";
    protected static final String C_BRACKET = "\\{CBRACKET\\}";
    protected static final String SEPARATOR = "\\{SEPARATOR\\}";
    protected static final String NUMBER = "\\{NUMBER (\\d+)\\}";

    private ByteArrayOutputStream baos;

    public ConsoleDisplayer(final GameSession session, final OutputStream os) {
        this(session, os, 0);
    }

    public ConsoleDisplayer(final GameSession session, final OutputStream os, final int settings) {
        this.session = session;
        this.settings = settings;
        this.os = os;
        this.baos = new ByteArrayOutputStream();
    }

    @Override
    public void help(final HelpType type) {
        switch (type) {
            case BATTLE_INFO -> helpBattleInfo();
            case BATTLE_ENEMY_INTENTS -> helpBattleEnemyIntents();
            case WRONG_INPUT -> printf("Informative help message\n");
            case ACTIONS -> helpActions();
            case TURN_INFO -> {
                helpBattleInfo();
                List<Card> battleDeck = new ArrayList<>();
                Player p = session.getPlayer();
                battleDeck.addAll(p.getShotDeck().getHand());
                battleDeck.addAll(p.getArmorDeck().getHand());
                choice(battleDeck.toArray());
            }
            case NONE -> {}
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        }
        ;
        session.setHelpType(HelpType.NONE);
    }

    @Override
    public void message(final String format, final Object... params) {
        if (params.length == 0) {
            printf("%s\n", format);
        } else {
            printf(format, params);
        }
    }

    @Override
    public void prompt(final String message) {
        printf("%s ", message);
    }

    @Override
    public void choice(final Object... options) {
        final StringBuilder builder = new StringBuilder();
        builder.append("{OBRACKET}");
        for (int i = 0; i < options.length; i++) {
            builder.append(String.format("{NUMBER %d}%s{SEPARATOR}", i + 1, options[i]));
        }
        builder.append("{CBRACKET}\n");
        printf(builder.toString());
    }

    @Override
    public Displayer withSettings(final int settings) {
        return new ConsoleDisplayer(session, os, settings);
    }

    @Override
    public void flush() {
        try {
            os.write(baos.toString().getBytes());
            baos = new ByteArrayOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void printf(String format, final Object... params) {
        format = transformTemplateString(format);
        String out = String.format(format, params);
        try {
            baos.write(out.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String transformTemplateString(String format) {
        if ((DisplayOptions.MENU.id() & settings) != 0) {
            format = format.replaceAll(O_BRACKET, "");
            format = format.replaceAll(C_BRACKET, "");
            format = format.replaceAll(SEPARATOR, "\n");
        } else {
            format = format.replaceAll(O_BRACKET, "[");
            format = format.replaceAll(C_BRACKET, "]");
            format = format.replaceAll(SEPARATOR, ", ");
        }
        if ((DisplayOptions.UNNUMBERED.id() & settings) != 0) {
            format = format.replaceAll(NUMBER, "");
        } else {
            Pattern p = Pattern.compile(NUMBER);
            Matcher m = p.matcher(format);
            while (m.find()) {
                int number = Integer.parseInt(m.group(1));
                format = m.replaceFirst(String.format("%d. ", number));
                m = p.matcher(format);
            }
        }
        return format;
    }

    private void helpActions() {
        withSettings(DisplayOptions.MENU.id() | DisplayOptions.UNNUMBERED.id())
                .choice((Object[]) Messages.BATTLE_OPTIONS);
    }

    private void helpBattleEnemyIntents() {
        final Enemy enemy = session.getBattleManager().getEnemy();
        for (final var intent : enemy.getCurrentIntents()) {
            printf("%s: %s\n", enemy.toString(), intent);
        }
        printf("\n");
    }

    private void helpBattleInfo() {
        final Player player = session.getPlayer();
        final Enemy enemy = session.getBattleManager().getEnemy();
        printf("=== Turn %d ===\n", session.getBattleManager().getTurnNumber());
        printf(
                """
                Player: [energy: %d, hp: %d, defence: %d]
                %s: [hp: %d, def %d]
                %s intends to %s.\n\
                """,
                player.getEnergy(),
                player.getHp(),
                player.getDefense(),
                enemy.toString(),
                enemy.getHp(),
                enemy.getDefense(),
                enemy.toString(),
                enemy.displayIntents());
        printf("=== GOOD LUCK! ===\n");
    }
}
