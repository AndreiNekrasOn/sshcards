package org.andnekon.game.state;

import org.andnekon.game.GameSession;

public class Menu extends State {

    public Menu(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(String input) {
        return switch (input) {
            case "1" -> new Navigation(session);
            case "4" -> new Quit(session);
            default -> this;
        };
    }

    @Override
    protected void setType() {
        this.type = State.Type.MENU;
    }

}

