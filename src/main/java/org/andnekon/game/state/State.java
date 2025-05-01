package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;

public abstract class State {

    public enum Type {
        BATTLE,
        DEATH,
        MENU,
        NAVIGATION,
        QUIT,
        REWARD,

        BALANCE_DRAFT,
        BALANCE_NAV,
        BALANCE_BATTlE,
    }

    protected State previousState;
    protected GameSession session;
    protected Type type;

    public State(GameSession session) {
        this.session = session;
        setType();
    }

    public abstract State handleInput(GameAction gameAction);

    protected abstract void setType();

    public Type getType() {
        return type;
    }
}
