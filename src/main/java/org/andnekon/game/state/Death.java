package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;

public class Death extends State {

    public Death(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(GameAction action) {
        switch (action.action()) {
            case REFUSE:
                return new Quit(session);
            case ACCEPT:
                return new Menu(session);
            default:
                throw new IllegalStateException("Incorrect GameAction for State Death: " + action);
        }
    }

    @Override
    protected void setType() {
        this.type = State.Type.DEATH;
    }
}
