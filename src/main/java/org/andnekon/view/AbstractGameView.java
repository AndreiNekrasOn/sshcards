package org.andnekon.view;

import org.andnekon.game.GameSession;
import org.andnekon.game.state.State;

public abstract class AbstractGameView implements GameView {

    protected GameSession session;
    protected State state;

    @Override
    public void display(State state) {
        this.state = state;
        switch (state.getType()) {
            case BATTLE -> showBattle();
            case DEATH -> showDeath();
            case HELP -> showHelp();
            case MENU -> showMenu();
            case NAVIGATION -> showNavigation();
            case QUIT -> showQuitConfirm();
            case REWARD -> showReward();
        }
    }

    public abstract byte[] prepare();

    protected abstract void showReward();

    protected abstract void showQuitConfirm();

    protected abstract void showNavigation();

    protected abstract void showMenu();

    protected abstract void showHelp();

    protected abstract void showDeath();

    protected abstract void showBattle();

}
