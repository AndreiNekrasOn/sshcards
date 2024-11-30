package org.andnekon.view;

import org.andnekon.game.GameLogic;

public abstract class AbstractGameView implements GameView {

    protected GameLogic game;

    @Override
    public void display() {
        switch (game.getCurrentState().getType()) {
            case BATTLE -> showBattle();
            case DEATH -> showDeath();
            case HELP -> showHelp();
            case MENU -> showMenu();
            case NAVIGATION -> showNavigation();
            case QUIT -> showQuitConfirm();
            case REWARD -> showReward();
        }
    }

    protected abstract void showReward();

    protected abstract void showQuitConfirm();

    protected abstract void showNavigation();

    protected abstract void showMenu();

    protected abstract void showHelp();

    protected abstract void showDeath();

    protected abstract void showBattle();

}
