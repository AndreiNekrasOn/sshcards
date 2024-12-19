package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.entity.Player;

public class TheHorror extends Enemy {

    public TheHorror() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String toString() {
        return "The Horror";
    }

    @Override
    public void fillIntents(Player player) {
        this.currentIntents.add(new Attack(this, 5, player));
    }
}
