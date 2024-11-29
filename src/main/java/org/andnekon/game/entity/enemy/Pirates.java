package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;

public class Pirates extends Enemy {

    public Pirates() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }
    @Override
    public String display() {
        return "Pirates";
    }


    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 2, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.DEFEND, 1, this));
    }

}

