package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;

public class Quit extends State {

    public Quit(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(GameAction action) {
        switch (action.action()) {
            case REFUSE:
                return session.getPreviousState();
            case ACCEPT:
                session.end();
                return this;
            default:
                return this;
        }
    }

    @Override
    protected void setType() {
        this.type = State.Type.QUIT;
    }
}
