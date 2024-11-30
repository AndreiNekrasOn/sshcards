package org.andnekon.game.state;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.view.HelpType;

public class Battle extends State {

    BattleState phase;

    public BattleState getPhase() {
        return phase;
    }

    State nextState;

    public Battle(GameSession session) {
        super(session);
        phase = BattleState.START;
        session.initBattle();
    }

    @Override
    public State handleInput(String input) {
        Player player = session.getPlayer();
        Enemy enemy = session.getEnemy();
        switch (phase) {
            case START -> { phase = BattleState.PLAYER_TURN_START; }
            case PLAYER_TURN_START -> {
                session.initTurn();
                phase = BattleState.PLAYER_TURN;
            }
            case PLAYER_TURN -> { phase = processBattleInput(player, enemy, input); }
            case PLAYER_TURN_END -> { phase = checkBattleEnd(BattleState.ENEMY_TURN_START, player, enemy); }
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
                phase = checkBattleEnd(BattleState.PLAYER_TURN_START, player, enemy);
            }
            case PLAYER_TURN_HELP -> {
                nextState = new Help(session);
                session.setHelpType(HelpType.WRONG_INPUT);
                phase = BattleState.PLAYER_TURN;
            }
            case COMPLETE -> {}
        }

        if (phase == BattleState.COMPLETE) {
            return new Reward(session);
        }
        return this;
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
                session.setHelpType(HelpType.BATTLE_INFO);
                nextState = new Help(session);
                yield BattleState.PLAYER_TURN_HELP;
            }
            case 'c' -> {
                session.setHelpType(HelpType.TURN_INFO);
                nextState = new Help(session);
                yield BattleState.PLAYER_TURN_HELP;
            }
            default -> BattleState.PLAYER_TURN;
        };
    }

    @Override
    protected void setType() {
        this.type = State.Type.BATTLE;
    }
}

