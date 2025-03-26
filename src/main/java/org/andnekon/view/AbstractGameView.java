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
            case BALANCE_DRAFT -> showBalanceDraft();
            case BALANCE_NAV -> showBalanceNav();
            case BALANCE_BATTlE -> showBalanceBattle();
            default -> throw new UnsupportedOperationException("Unknown game state");
        }
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
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

    protected void showBalanceBattle() {
        throw new UnsupportedOperationException("Unimplemented method 'showBalanceBattle'");
    }

    protected void showBalanceNav() {
        throw new UnsupportedOperationException("Unimplemented method 'showBalanceNav'");
    }

    protected void showBalanceDraft() {
        throw new UnsupportedOperationException("Unimplemented method 'showBalanceDraft'");
    }
}
