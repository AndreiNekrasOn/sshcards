package org.andnekon.game;

import org.andnekon.game.state.Menu;
import org.andnekon.game.state.Quit;
import org.andnekon.game.state.State;
import org.andnekon.game.state.State.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Turn-based game that takes user input, provides a Slay-the-spire like interaction in the
 * terminal.<br>
 * <b>Game description:</b><br>
 * Menu: Start game, Continue, About, Quit<br>
 * After start game: navigation->battle->reward->navigation untill game over<br>
 * Navigation: choose one of 3 avaliable fights<br>
 * Battle: is turn-based<br>
 * during players turn, the player has several cards that cost energy;<br>
 * computer displays its intent<br>
 * during computer turn, computer makes the intended<br>
 * when the computer HP reaches 0, the battle is over<br>
 * if the player HP reaches 0, the game is over<br>
 * Reward: choose one of 3 rewards, randomly chosen cards<br>
 * When the game ends: show end screen with score<br>
 * After the end screen, show the menu<br>
 */
public class GameLogic {

    public static final Logger logger = LoggerFactory.getLogger(GameLogic.class);

    State previousState;

    State currentState;

    GameSession session;

    public GameLogic() {
        session = new GameSession();
        setCurrentState(new Menu(session));
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State state) {
        if (currentState == null || currentState.getType() != State.Type.QUIT) {
            previousState = currentState;
            session.setPreviousState(previousState);
        }
        currentState = state;
        session.setIsInBattle(currentState != null && currentState.getType() == State.Type.BATTLE);
    }

    public void process(GameAction action) {
        logger.info("Waiting for input, state {}", currentState.getType());
        if (action == null) {
            throw new UnsupportedOperationException("GameAction can't be null");
        }
        GameAction.Type actionType = action.action();
        if (actionType == GameAction.Type.PASS) {
            return;
        }
        // TODO: remove when all support HELP, or handle this differently
        List<State.Type> supportHelp = List.of(Type.BATTLE, Type.QUIT);
        if (actionType == GameAction.Type.HELP && !supportHelp.contains(currentState.getType())) {
            logger.info("Help action not supported for {}", currentState);
            return;
        }

        State newState =
                (action.action() == GameAction.Type.QUIT)
                        ? new Quit(session)
                        : currentState.handleInput(action);
        setCurrentState(newState);
        logger.info("After input {}, state {}", action, currentState.getType());
    }

    public GameSession getSession() {
        return session;
    }
}
