package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Effect;
import org.andnekon.game.entity.Player;

/**
 * Mirrored ship has a lot of max hp and starts with half hp. It scaels exponentially in attack
 * value with the number of turns, and it also heals itslef.
 */
public class MirroredShip extends Enemy {

    public MirroredShip() {
        super();
        this.hp = 10;
        this.maxHp = 20;
    }

    @Override
    public String toString() {
        return "Mirrored Ship";
    }

    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Effect(this, "Crack", turnNumber, player));
        this.currentIntents.add(new Effect(this, "Heal", turnNumber, this));
    }
}
