package org.andnekon.game.state;

import org.andnekon.game.GameAction;
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

    private static final List<GameAction.Type> viableActions =
            List.of(
                    GameAction.Type.BATTLE_CARD,
                    GameAction.Type.BATTLE_CHECK,
                    GameAction.Type.BATTLE_END_TURN,
                    GameAction.Type.HELP);

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
        session.getBattleManager().init();
        session.getBattleManager().initTurn();
    }

    public BattleState getPhase() {
        return phase;
    }

    @Override
    public State handleInput(GameAction action) {
        while (runBattle(action)) {}

        if (phase == BattleState.COMPLETE) {
            return nextState;
        }
        return this;
    }

    @Override
    protected void setType() {
        this.type = State.Type.BATTLE;
    }

    private boolean runBattle(GameAction action) {
        logger.info("runBattle start phase {}", phase);
        Player player = session.getPlayer();
        Enemy enemy = session.getBattleManager().getEnemy();
        switch (phase) {
            case PLAYER_TURN_START, PLAYER_TURN, PLAYER_TURN_HELP -> {
                phase = processBattleInput(player, enemy, action);
            }
            case PLAYER_TURN_END -> {
                phase = checkBattleEnd(BattleState.ENEMY_TURN_START, player, enemy);
            }
            case ENEMY_TURN_START -> {
                enemy.onTurnBegin();
                phase = BattleState.ENEMY_TURN_END;
            }
            case ENEMY_TURN_END -> {
                enemy.clearIntents();
                // we need to init turn in constructor for the better View
                // so we call initTurn again here, not on PLAYER_TURN_START
                session.getBattleManager().initTurn();
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

    private BattleState processBattleInput(Player player, Enemy enemy, GameAction action) {
        BattleState state = processBattleInputHelpers(player, enemy, action);
        if (state != BattleState.PLAYER_TURN) {
            return state;
        }
        if (action.action() != GameAction.Type.BATTLE_CARD || action.payload() == null) {
            throw new UnsupportedOperationException("Unexpexted GameAction in Battle");
        }
        try {
            List<Card> hand = chooseDeck(player, action.payload());
            player.useCard(hand.get(action.id()), enemy);
            return BattleState.PLAYER_TURN;
        } catch (IndexOutOfBoundsException e) {
            session.setHelpType(HelpType.WRONG_INPUT);
        } catch (NotEnoughEnergyException | CardNotInHandException e) {
            session.setHelpType(HelpType.WRONG_INPUT);
        }
        return BattleState.PLAYER_TURN_HELP;
    }

    private List<Card> chooseDeck(Player player, String type) {
        return switch (type) {
            case "shot" -> player.getShotDeck().getHand();
            case "armor" -> player.getArmorDeck().getHand();
            default -> throw new UnsupportedOperationException("Unimplemented deck type");
        };
    }

    private BattleState processBattleInputHelpers(Player player, Enemy enemy, GameAction action) {
        if (!viableActions.contains(action.action())) {
            return BattleState.PLAYER_TURN_HELP;
        }
        return switch (action.action()) {
            case BATTLE_END_TURN -> BattleState.PLAYER_TURN_END;
            case HELP -> {
                session.setHelpType(HelpType.ACTIONS);
                yield BattleState.PLAYER_TURN_HELP;
            }
            case BATTLE_CHECK -> {
                session.setHelpType(HelpType.TURN_INFO);
                yield BattleState.PLAYER_TURN_HELP;
            }
            default -> BattleState.PLAYER_TURN;
        };
    }
}
