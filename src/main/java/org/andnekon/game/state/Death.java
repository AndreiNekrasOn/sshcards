package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public class Death extends State {

    public Death(GameSession session) {
        super(session);
        //TODO Auto-generated constructor stub
    }

    @Override
    public State handleInput(String input) {
        return new Quit(session);
    }

    @Override
    protected void setType() {
        this.type = State.Type.DEATH;
    }

}

