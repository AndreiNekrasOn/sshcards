package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public class Quit extends State {

    public Quit(GameSession session) {
        super(session);
        //TODO Auto-generated constructor stub
    }

    @Override
    public State handleInput(String input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleInput'");
    }

    @Override
    protected void setType() {
        this.type = State.Type.QUIT;
    }

}

