package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;

public class RegularShip extends Enemy {

    public RegularShip() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String display() {
        return "Regular Ship";
    }

    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 1, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 1, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 1, player));
    }
}
