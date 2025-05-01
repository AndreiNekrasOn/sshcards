package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Effect;
import org.andnekon.game.manage.BattleManager;

/**
 * RegularShip scales exponentially with each turn and has a lot of hp, but also takes damage with
 * each attack
 */
public class RegularShip extends Enemy {

    @Override
    public void fillIntents(BattleManager manager) {
        int attackValue = 2 * turnNumber + 1;
        this.currentIntents.add(new Attack(this, attackValue, manager, manager.getPlayer()));
        this.currentIntents.add(new Attack(this, attackValue, manager, manager.getPlayer()));
        this.currentIntents.add(new Effect(this, "Corrosion", 4, manager, this));
    }
}
