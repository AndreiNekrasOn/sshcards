package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.entity.Player;

/**
 * Pirates have two states: defence mode and attack mode, that switch every turn The value of
 * attacks in defence mode grows by 1 each time
 */
public class Pirates extends Enemy {

    private int attackValue = 0;

    @Override
    public void fillIntents(Player player) {
        if (turnNumber % 2 == 0) {
            this.currentIntents.add(new Defence(this, 2, this));
            this.currentIntents.add(new Attack(this, dmg + attackValue++, player));
        } else {
            this.currentIntents.add(new Attack(this, 1, player));
            this.currentIntents.add(new Attack(this, dmg + attackValue++, player));
        }
    }
}
