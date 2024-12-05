package org.andnekon.view;

import org.andnekon.game.GameLogic;
import org.andnekon.game.GameSession;
import org.andnekon.game.state.Battle;
import org.andnekon.game.state.BattleState;
import org.andnekon.view.formatter.ConsoleDisplayer;
import org.andnekon.view.formatter.DisplayOptions;
import org.andnekon.view.formatter.Displayer;

public class ConsoleView extends AbstractGameView {

    protected GameSession session;
    protected Displayer helper;

    public ConsoleView(GameLogic game) {
        this.game = game;
        this.session = game.getSession();
        this.helper = new ConsoleDisplayer(session);
    }

    @Override
    public void welcome() {
    }

    protected void showReward() {
        helper.message("YOU WON! Number of turns: %d\n", session.getTurnNumber());
    }

    protected void showQuitConfirm() {
        helper.prompt("Are you sure you want to quit? [y/n]");
    }

    protected void showNavigation() {
        helper.choice(session.getEnemyNavLeft().display(), session.getEnemyNavRight().display());
        helper.prompt("Where do you go?");
    }

    protected void showMenu() {
        helper.withSettings(DisplayOptions.MENU.id())
            .choice((Object[]) Messages.MENU_OPTIONS);
    }

    protected void showHelp() {
        helper.help(session.getHelpType());
    }

    protected void showDeath() {
        helper.message("YOU ARE DEAD.");
    }

    protected void showBattle() {
        BattleState phase = ((Battle) game.getCurrentState()).getPhase();
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
