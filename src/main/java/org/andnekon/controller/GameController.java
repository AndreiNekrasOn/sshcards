package org.andnekon.controller;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameAction.Type;
import org.andnekon.game.GameLogic;
import org.andnekon.view.GameView;
import org.andnekon.view.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GameController {

    protected GameLogic game;

    protected GameView view;

    protected Reader reader;

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private static final Map<String, GameAction> navActions =
            Map.of(
                    "1", new GameAction(Type.NAVIGATION, 1),
                    "2", new GameAction(Type.NAVIGATION, 2),
                    "3", new GameAction(Type.NAVIGATION, 3),
                    "4", new GameAction(Type.NAVIGATION, 4));

    private static final Map<String, GameAction> rewardActions =
            Map.of(
                    "1", new GameAction(Type.NAVIGATION, 1),
                    "2", new GameAction(Type.NAVIGATION, 2),
                    "3", new GameAction(Type.NAVIGATION, 3),
                    "s", new GameAction(Type.NAVIGATION, 0));

    private static final Map<String, GameAction> battleActions =
            Map.of(
                    // menu is 1-indexed, while hand is 0-indexed
                    "s1", new GameAction(Type.BATTLE_CARD, 0, "shot"),
                    "s2", new GameAction(Type.BATTLE_CARD, 1, "shot"),
                    "s3", new GameAction(Type.BATTLE_CARD, 2, "shot"),
                    "s4", new GameAction(Type.BATTLE_CARD, 3, "shot"),
                    "a1", new GameAction(Type.BATTLE_CARD, 0, "armor"),
                    "a2", new GameAction(Type.BATTLE_CARD, 1, "armor"),
                    "a3", new GameAction(Type.BATTLE_CARD, 2, "armor"),
                    "a4", new GameAction(Type.BATTLE_CARD, 4, "armor"),
                    "e", new GameAction(Type.BATTLE_END_TURN),
                    "c", new GameAction(Type.BATTLE_CHECK));

    private static final Map<String, GameAction> confirmActions =
            Map.of(
                    "y", new GameAction(Type.ACCEPT),
                    "Y", new GameAction(Type.ACCEPT),
                    "j", new GameAction(Type.ACCEPT),
                    "J", new GameAction(Type.ACCEPT),
                    "n", new GameAction(Type.REFUSE),
                    "N", new GameAction(Type.REFUSE),
                    "x", new GameAction(Type.REFUSE),
                    "X", new GameAction(Type.REFUSE));

    private static final Map<String, GameAction> globalActions =
            Map.of(
                    "q", new GameAction(Type.QUIT),
                    "?", new GameAction(Type.HELP));

    public GameController(GameLogic game, GameView view, Reader reader) {
        this.game = game;
        this.view = view;
        this.reader = reader;
    }

    public void run() {
        view.welcome();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        do {
            view.display(game.getCurrentState()); // sends data to the os
            String in = reader.read();
            GameAction action = mapInputtoAction(in);
            logger.info("transformed input [{}] to action {}", in, action);
            game.process(action);
        } while (!game.getSession().isEnd());
    }

    private GameAction choose(Map<String, GameAction> choice, String key) {
        return choice.getOrDefault(key, new GameAction(Type.PASS));
    }

    public GameAction mapInputtoAction(String key) {
        if (globalActions.containsKey(key)) {
            return globalActions.get(key);
        }
        return switch (game.getCurrentState().getType()) {
            case BATTLE -> choose(battleActions, key);
            case DEATH -> choose(confirmActions, key);
            case MENU -> choose(navActions, key);
            case NAVIGATION -> choose(navActions, key);
            case QUIT -> {
                if (confirmActions.containsKey(key)) {
                    yield choose(confirmActions, key);
                }
                yield new GameAction(Type.ACCEPT); // any other key -> quit
            }
            case REWARD -> choose(rewardActions, key);
            default -> new GameAction(Type.PASS);
        };
    }
}
