package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.manage.BattleManager;

/**
 * Pirates have two states: defence mode and attack mode, that switch every turn The value of
 * attacks in defence mode grows by 1 each time
 */
public class Pirates extends Enemy {

    private int attackValue = 0;

    @Override
    public void fillIntents(BattleManager manager) {
        if (turnNumber % 2 == 0) {
            this.currentIntents.add(new Defence(this, 2, manager, this));
            this.currentIntents.add(
                    new Attack(this, dmg + attackValue++, manager, manager.getPlayer()));
        } else {
            this.currentIntents.add(new Attack(this, 1, manager, manager.getPlayer()));
            this.currentIntents.add(
                    new Attack(this, dmg + attackValue++, manager, manager.getPlayer()));
        }
    }
}
