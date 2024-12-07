package org.andnekon.view;

import org.andnekon.game.state.State;

public interface GameView {

    public void welcome();

    public void display(State state);

}
