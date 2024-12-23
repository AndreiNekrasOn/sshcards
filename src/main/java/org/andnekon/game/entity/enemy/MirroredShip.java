package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Support;
import org.andnekon.game.entity.Player;

/**
 * Mirrored ship has a lot of max hp and starts with half hp. It scaels exponentially in attack
 * value with the number of turns, and it also heals itslef.
 */
public class MirroredShip extends Enemy {

    public MirroredShip() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String toString() {
        return "Mirrored Ship";
    }

    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Attack(this, (int) Math.pow(2, turnNumber), player));
        this.currentIntents.add(new Support(this, (int) Math.pow(2, turnNumber), this));
    }
}
