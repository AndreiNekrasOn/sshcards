package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public class Help extends State {

    public Help(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(String input) {
        throw new UnsupportedOperationException("Unimplemented method 'handleInput'");
    }

    @Override
    protected void setType() {
        this.type = State.Type.HELP;
    }

}

