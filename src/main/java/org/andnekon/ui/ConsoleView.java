package org.andnekon.ui;

import org.andnekon.game.BattleState;
import org.andnekon.game.GameLogic;
import org.andnekon.game.GameSession;
import org.andnekon.game.state.Battle;
import org.andnekon.game.state.State;
import org.andnekon.ui.console.ConsoleDisplayer;

public class ConsoleView extends AbstractGameView {

    GameSession session;
    Displayer helper;

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
        switch (((Battle) game.getCurrentState()).getPhase()) {
            case PLAYER_TURN_START -> {
                helper.help(HelpType.BATTLE_INFO);
                helper.help(HelpType.BATTLE_ENEMY_INTENTS);
            }
            case PLAYER_TURN -> {
                helper.prompt("What do you do?");
            }
            case PLAYER_TURN_END -> {}
            case ENEMY_TURN_START -> {
                helper.withSettings(DisplayOptions.MENU.id())
                    .choice(session.getEnemy().getCurrentIntents().toArray());
            }
            case ENEMY_TURN_END -> {}
            case PLAYER_TURN_HELP -> helper.help(HelpType.BATTLE_ENEMY_INTENTS);
            case COMPLETE -> helper.message("Battle finished");
            default -> {}
        }
    }

}
