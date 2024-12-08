package org.andnekon.game.state;

import java.util.List;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.view.HelpType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final List<BattleState> phasesRequiringInput = List.of(
        BattleState.PLAYER_TURN_START,
        BattleState.PLAYER_TURN,
        BattleState.PLAYER_TURN_HELP,
        BattleState.COMPLETE
    );

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
        while (runBattle(input)) {
        }
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
                enemy.setDefense(0);
                for (Intent intent : enemy.getCurrentIntents()) {
                    intent.execute();
                }
                phase = BattleState.ENEMY_TURN_END;
            }
            case ENEMY_TURN_END -> {
                enemy.clearIntents();
                session.incTurn();
                // we need to init turn in constructor for the better View
                // so we call initTurn again here, not on PLAYER_TURN_START
                session.initTurn();
                phase = checkBattleEnd(BattleState.PLAYER_TURN_START, player, enemy);
            }
            case COMPLETE -> {}
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
        // play card
        Card card;
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > player.getBattleDeck().size()) {
                return BattleState.PLAYER_TURN_HELP;
            }
            card = player.getBattleDeck().get(i - 1);
        } catch (Exception e) {
            return BattleState.PLAYER_TURN_HELP;
        }
        if (card.getCost() > player.getEnergy()) {
            session.setHelpType(HelpType.WRONG_INPUT);
            return BattleState.PLAYER_TURN_HELP;
        }
        player.useCard(card, enemy);
        return BattleState.PLAYER_TURN;
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

