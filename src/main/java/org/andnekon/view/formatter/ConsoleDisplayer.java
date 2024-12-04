package org.andnekon.view.formatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andnekon.game.GameSession;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.view.HelpType;
import org.andnekon.view.Messages;

public class ConsoleDisplayer implements Displayer {

    GameSession session;

    private static final String O_BRACKET = "\\{OBRACKET\\}";
    private static final String C_BRACKET = "\\{CBRACKET\\}";
    private static final String SEPARATOR = "\\{SEPARATOR\\}";
    private static final String NUMBER = "\\{NUMBER (\\d+)\\}";

    // DisplayOptions
    int settings = 0;

    public ConsoleDisplayer(final GameSession session) {
        this.session = session;
    }

    public ConsoleDisplayer(final GameSession session, final int settings) {
        this.session = session;
        this.settings = settings;
    }

    @Override
    public void help(final HelpType type) {
        switch (type) {
            case BATTLE_INFO -> helpBattleInfo();
            case BATTLE_ENEMY_INTENTS -> helpBattleEnemyIntents();
            case WRONG_INPUT -> printf("Informative help message");
            case ACTIONS -> helpActions();
            case TURN_INFO -> throw new UnsupportedOperationException("Unimplemented case: " + type);
            default -> throw new IllegalArgumentException("Unexpected value: " + type);
        };
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
        builder.append("{CBRACKET}");
        printf(builder.toString());
    }

    @Override
    public Displayer withSettings(final int settings) {
        return new ConsoleDisplayer(session, settings);
    }

    private void printf(String format, final Object... params) {
        if ((DisplayOptions.MENU.id() & settings) != 0) {
            format = format.replaceAll(O_BRACKET, "");
            format = format.replaceAll(C_BRACKET, "");
            format = format.replaceAll(SEPARATOR, "\n");
        } else {
            format = format.replaceAll(O_BRACKET, "[");
            format = format.replaceAll(C_BRACKET, "]\n");
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
        System.out.printf(format, params);
    }

    private void helpActions() {
        withSettings(DisplayOptions.MENU.id() | DisplayOptions.UNNUMBERED.id())
            .choice((Object[]) Messages.BATTLE_OPTIONS);
    }

    private void helpBattleEnemyIntents() {
        final Enemy enemy = session.getEnemy();
        for (final var intent : enemy.getCurrentIntents()) {
            printf("%s: %s\n", enemy.display(), intent);
        }
        printf("\n");
    }

    private void helpBattleInfo() {
                final Player player = session.getPlayer();
                final Enemy enemy = session.getEnemy();
                printf("=== Turn %d ===\n", session.getTurnNumber());
                printf("""
                            Player: [energy: %d, hp: %d, defence: %d]
                            %s: [hp: %d, def %d]
                            %s intends to %s.\n""",
                            player.getEnergy(), player.getHp(), player.getDefense(),
                            enemy.display(), enemy.getHp(), enemy.getDefense(),
                            enemy.display(), enemy.displayIntents());
                printf("=== GOOD LUCK!  ===\n", session.getTurnNumber());
            }
}

