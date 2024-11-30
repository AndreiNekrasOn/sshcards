package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public class Reward extends State {

    public Reward(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(String input) {
        return new Navigation(session);
    }

    @Override
    protected void setType() {
        this.type = State.Type.REWARD;
    }

}

