package org.andnekon.game.state;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.entity.CardNotInHandException;
import org.andnekon.game.entity.NotEnoughEnergyException;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.view.HelpType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Battle extends State {

    public enum BattleState {
        PLAYER_TURN_START,
        PLAYER_TURN,
        PLAYER_TURN_END,
        ENEMY_TURN_START,
        ENEMY_TURN_END,
        COMPLETE,
        PLAYER_TURN_HELP,
    }

    private static final List<BattleState> phasesRequiringInput =
            List.of(
                    BattleState.PLAYER_TURN_START,
                    BattleState.PLAYER_TURN,
                    BattleState.PLAYER_TURN_HELP,
                    BattleState.COMPLETE);

    Logger logger = LoggerFactory.getLogger(Battle.class);

    BattleState phase;

    State nextState;

    public Battle(GameSession session) {
        super(session);
        phase = BattleState.PLAYER_TURN_START;
        session.initBattle();
        session.initTurn();
    }

    public BattleState getPhase() {
        return phase;
    }

    @Override
    public State handleInput(String input) {
        logger.info("In battle recieved {}, phase {}", input, phase);
        while (runBattle(input)) {}
        logger.info("After processing input phase {}", phase);

        if (phase == BattleState.COMPLETE) {
            return nextState;
        }
        return this;
    }

    @Override
    protected void setType() {
        this.type = State.Type.BATTLE;
    }

    private boolean runBattle(String input) {
        logger.info("runBattle start phase {}", phase);
        Player player = session.getPlayer();
        Enemy enemy = session.getEnemy();
        switch (phase) {
            case PLAYER_TURN_START, PLAYER_TURN, PLAYER_TURN_HELP -> {
                phase = processBattleInput(player, enemy, input);
            }
            case PLAYER_TURN_END -> {
                phase = checkBattleEnd(BattleState.ENEMY_TURN_START, player, enemy);
            }
            case ENEMY_TURN_START -> {
                enemy.turn();
                phase = BattleState.ENEMY_TURN_END;
            }
            case ENEMY_TURN_END -> {
                enemy.clearIntents();
                // we need to init turn in constructor for the better View
                // so we call initTurn again here, not on PLAYER_TURN_START
                session.initTurn();
                phase = checkBattleEnd(BattleState.PLAYER_TURN_START, player, enemy);
            }
            case COMPLETE -> {}
            default -> throw new UnsupportedOperationException("Unknown battle state");
        }
        logger.info("runBattle end phase {}", phase);
        return !phasesRequiringInput.contains(phase);
    }

    private BattleState checkBattleEnd(BattleState nextPhase, Player player, Enemy enemy) {
        if (player.getHp() <= 0) {
            nextState = new Death(session);
            return BattleState.COMPLETE;
        } else if (enemy.getHp() <= 0) {
            nextState = new Reward(session);
            return BattleState.COMPLETE;
        }
        return nextPhase;
    }

    private BattleState processBattleInput(Player player, Enemy enemy, String input) {
        BattleState state = processBattleInputHelpers(player, enemy, input);
        if (state != BattleState.PLAYER_TURN) {
            return state;
        }
        // TODO: rn input processing depends on the view, creating too much coupling.
        // Needs a mediator to transform input into spefic action
        try {
            // no bounds checking, throws instead
            var shotDeck = player.getShotDeck().getHand();
            int shotDeckSize = shotDeck.size();
            logger.info(
                    shotDeck.stream().map(c -> c.toString()).toList().stream()
                            .map(Object::toString)
                            .toList()
                            .toString());
            Card card;
            int cardNum = Integer.parseInt(input) - 1;
            if (cardNum < shotDeckSize) {
                card = shotDeck.get(cardNum);
            } else {
                card = player.getArmorDeck().getHand().get(cardNum - shotDeckSize);
            }
            player.useCard(card, enemy);
            return BattleState.PLAYER_TURN;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            session.setHelpType(HelpType.WRONG_INPUT); // TODO: more HelpTypes? Messages?
        } catch (NotEnoughEnergyException | CardNotInHandException e) {
            session.setHelpType(HelpType.WRONG_INPUT);
        }
        return BattleState.PLAYER_TURN_HELP;
    }

    private BattleState processBattleInputHelpers(Player player, Enemy enemy, String input) {
        if (input.length() == 0) {
            return BattleState.PLAYER_TURN_HELP;
        }
        return switch (input.charAt(0)) {
            case 'q' -> {
                nextState = new Quit(session);
                yield BattleState.COMPLETE;
            }
            case 'e' -> BattleState.PLAYER_TURN_END;
            case 'h' -> {
                session.setHelpType(HelpType.ACTIONS);
                yield BattleState.PLAYER_TURN_HELP;
            }
            case 'c' -> {
                session.setHelpType(HelpType.TURN_INFO);
                yield BattleState.PLAYER_TURN_HELP;
            }
            default -> BattleState.PLAYER_TURN;
        };
    }
}
