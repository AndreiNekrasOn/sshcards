package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public class Quit extends State {

    public Quit(GameSession session) {
        super(session);
        // TODO Auto-generated constructor stub
    }

    @Override
    public State handleInput(String input) {
        switch (input) {
            case "n", "N":
                return session.getPreviousState();
            case "q", "y", "Q", "Y":
                // TODO: Quitting logic
                throw new UnsupportedOperationException("Quit logic not fully done");
            default:
                return this;
        }
    }

    @Override
    protected void setType() {
        this.type = State.Type.QUIT;
    }
}
