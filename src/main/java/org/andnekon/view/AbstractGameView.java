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
            case MENU -> showMenu();
            case NAVIGATION -> showNavigation();
            case QUIT -> showQuitConfirm();
            case REWARD -> showReward();
            default -> throw new UnsupportedOperationException("Unknown game state");
        }
    }

    protected abstract void showReward();

    protected abstract void showQuitConfirm();

    protected abstract void showNavigation();

    protected abstract void showMenu();

    protected abstract void showDeath();

    protected abstract void showBattle();

    public State getState() {
        return state;
    }
}
