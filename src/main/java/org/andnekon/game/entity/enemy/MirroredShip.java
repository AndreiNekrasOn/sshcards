package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.entity.Player;

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
        this.currentIntents.add(new Attack(this, 2, player));
        this.currentIntents.add(new Defence(this, 1, this));
    }
}
