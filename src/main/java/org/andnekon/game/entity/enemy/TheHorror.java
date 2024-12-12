package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;

public class TheHorror extends Enemy {

    public TheHorror() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String display() {
        return "The Horror";
    }

    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 5, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.DEFEND, 1, this));
    }
}
