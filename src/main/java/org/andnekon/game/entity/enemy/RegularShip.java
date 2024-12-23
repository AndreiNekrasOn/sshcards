package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Effect;
import org.andnekon.game.entity.Player;

/**
 * RegularShip scales exponentially with each turn and has a lot of hp, but also takes damage with
 * each attack
 */
public class RegularShip extends Enemy {

    public RegularShip() {
        super();
        this.hp = 15;
        this.maxHp = 15;
    }

    @Override
    public String toString() {
        return "Regular Ship";
    }

    @Override
    public void fillIntents(Player player) {
        int attackValue = (int) Math.pow(2, 1 + turnNumber);
        this.currentIntents.add(new Attack(this, attackValue, player));
        this.currentIntents.add(new Attack(this, attackValue, player));
        this.currentIntents.add(new Effect(this, "Poison", 3, this));
    }
}
