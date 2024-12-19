package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.entity.Player;

public class Pirates extends Enemy {

    public Pirates() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String toString() {
        return "Pirates";
    }

    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Defence(this, 2, this));
        this.currentIntents.add(new Attack(this, 1, player));
    }
}
