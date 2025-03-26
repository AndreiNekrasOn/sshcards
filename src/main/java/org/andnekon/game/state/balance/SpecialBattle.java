package org.andnekon.game.state.balance;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;
import org.andnekon.game.entity.Combat;
import org.andnekon.game.entity.Player;
import org.andnekon.game.state.Battle;
import org.andnekon.game.state.Death;
import org.andnekon.game.state.State;

/** SpecialBattle */
public class SpecialBattle extends Battle {

    public SpecialBattle(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(GameAction action) {
        return super.handleInput(action);
    }

    @Override
    protected void setType() {
        this.type = State.Type.BATTLE;
    }

    @Override
    protected BattleState checkBattleEnd(BattleState nextPhase, Player player, Combat combat) {
        if (player.getHp() <= 0) {
            nextState = new Death(session);
            return BattleState.COMPLETE;
        } else if (combat.isEnded()) {
            nextState = new Draft(session);
            return BattleState.COMPLETE;
        }
        return nextPhase;
    }
}
