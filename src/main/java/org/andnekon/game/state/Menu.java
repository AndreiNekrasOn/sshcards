package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;

public class Menu extends State {

    public Menu(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(GameAction action) {
        if (action.action() != GameAction.Type.NAVIGATION) {
            throw new UnsupportedOperationException("Wrong action for state");
        }
        return switch (action.id()) {
            case 1 -> {
                session.reset();
                yield new Navigation(session);
            }
            case 2 -> this; // no need for state, handle at view
            case 3 -> this; // no need for state, handle at view
            case 4 -> new Quit(session);
            default -> this;
        };
    }

    @Override
    protected void setType() {
        this.type = State.Type.MENU;
    }
}
