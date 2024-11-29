package org.andnekon.ui.console;

import org.andnekon.game.GameSession;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.ui.DisplayOptions;
import org.andnekon.ui.Displayer;
import org.andnekon.ui.HelpType;

public class ConsoleDisplayer implements Displayer {

    GameSession session;

    // DisplayOptions
    int settings = 0;

    public ConsoleDisplayer(GameSession session) {
        this.session = session;
    }

    public ConsoleDisplayer(GameSession session, int settings) {
        this.session = session;
        this.settings = settings;
    }

    @Override
    public void help(HelpType type) {
        switch (type) {
            case BATTLE_INFO -> {
                Player player = session.getPlayer();
                Enemy enemy = session.getEnemy();
                System.out.printf("=== Turn %d ===\n", session.getTurnNumber());
                System.out.printf("""
                            Player: [energy: %d, hp: %d, defence: %d]
                            %s: [hp: %d, def %d]
                            %s intends to %s.\n""",
                            player.getEnergy(), player.getHp(), player.getDefense(),
                            enemy.display(), enemy.getHp(), enemy.getDefense(),
                            enemy.display(), enemy.displayIntents());
                System.out.printf("=== GOOD LUCK!  ===\n", session.getTurnNumber());
            }
            case BATTLE_ENEMY_INTENTS -> {
                Enemy enemy = session.getEnemy();
                for (var intent : enemy.getCurrentIntents()) {
                    System.out.printf("%s: %s\n", enemy.display(), intent);
                }
                System.out.println();
            }
            case WRONG_INPUT -> {
                System.out.println("Informative help message");
            }
        };
    }

    @Override
    public void warning(String message) {
        System.out.printf("WARNING! %s\n", message);
    }

    @Override
    public void prompt(String message) {
        System.out.printf("%s ", message);
    }

    @Override
    public void choice(Object... options) {
        StringBuilder builder = new StringBuilder();
        boolean isMenu = (settings & DisplayOptions.MENU.id()) != 0;
        builder.append(isMenu ? "" : "[ ");
        for (int i = 0; i < options.length; i++) {
            builder.append(String.format("%d. %s%s", i + 1, options[i],
                    isMenu ? "\n" : ", "));
        }
        builder.append(isMenu ? "" : "]\n");
        System.out.print(builder.toString());
    }

    @Override
    public Displayer withSettings(int settings) {
        return new ConsoleDisplayer(session, settings);
    }
}

