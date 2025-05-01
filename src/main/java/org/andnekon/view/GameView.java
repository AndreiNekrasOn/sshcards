package org.andnekon.view;

import org.andnekon.game.state.State;

public interface GameView {

    public void stop();

    public void welcome();

    public void display(State state);
}
