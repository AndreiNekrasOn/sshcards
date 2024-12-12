package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public abstract class State {

    public enum Type {
        BATTLE,
        DEATH,
        HELP,
        MENU,
        NAVIGATION,
        QUIT,
        REWARD,
    }

    State previousState;
    GameSession session;
    Type type;

    public State(GameSession session) {
        this.session = session;
        setType();
    }

    public abstract State handleInput(String input);

    protected abstract void setType();

    public Type getType() {
        return type;
    }
}
