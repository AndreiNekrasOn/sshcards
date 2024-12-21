package org.andnekon.view.repl;

import org.andnekon.game.GameSession;
import org.andnekon.game.state.Battle;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.DisplayOptions;
import org.andnekon.view.Displayer;
import org.andnekon.view.HelpType;
import org.andnekon.view.Messages;

public class ConsoleView extends AbstractGameView {

    protected Displayer helper;

    public ConsoleView(GameSession session) {
        this.session = session;
        this.helper = new ConsoleDisplayer(session);
    }

    @Override
    public void welcome() {}

    protected void showReward() {
        helper.message("YOU WON! Number of turns: %d\n", session.getTurnNumber());
    }

    protected void showQuitConfirm() {
        helper.prompt("Are you sure you want to quit? [y/n]");
    }

    protected void showNavigation() {
        helper.choice(session.getEnemyNavLeft().toString(), session.getEnemyNavRight().toString());
        helper.prompt("Where do you go?");
    }

    protected void showMenu() {
        helper.withSettings(DisplayOptions.MENU.id()).choice((Object[]) Messages.MENU_OPTIONS);
    }

    protected void showDeath() {
        helper.message("YOU ARE DEAD.");
    }

    protected void showBattle() {
        Battle.BattleState phase = ((Battle) state).getPhase();
        switch (phase) {
            case PLAYER_TURN_START -> {
                helper.help(HelpType.BATTLE_INFO);
                helper.help(HelpType.BATTLE_ENEMY_INTENTS);
                helper.help(HelpType.ACTIONS);
            }
            case PLAYER_TURN -> {}
            case PLAYER_TURN_HELP -> {
                helper.help(session.getHelpType());
            }
            case COMPLETE -> helper.message("Battle finished");
            default -> throw new IllegalStateException("Unexpected value: " + phase);
        }
        helper.prompt("What do you do?");
    }
}
