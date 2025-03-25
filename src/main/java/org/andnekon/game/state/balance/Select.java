package org.andnekon.game.state.balance;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;
import org.andnekon.game.entity.enemy.EnemyFactory;
import org.andnekon.game.state.State;

/**
 * Select
 */
public class Select extends State {

    private int idx;

    public Select(GameSession session) {
        super(session);
        idx = 0;
    }

    @Override
    public State handleInput(GameAction gameAction) {
        int max = EnemyFactory.enemyTypes.length;
        switch (gameAction.action()){
            case DRAFT_NEXT:
                this.idx++;
            case DRAFT_ADD:
                session.getBattleManager().setEnemy(EnemyFactory.getEnemy(idx % max));
                return new SpecialBattle(session);
            default:
                break;
        }
        return this;
    }

    @Override
    protected void setType() {
        this.type = State.Type.BALANCE_NAV;
    }
}
