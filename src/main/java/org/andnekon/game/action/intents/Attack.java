package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;

/** Attack */
public class Attack extends Intent {

    public Attack(Entity source, int value, Entity... targets) {
        super(source, value, targets);
    }

    @Override
    public void execute(Entity... targets) {
        for (Entity target : targets) {
            target.takeDamage(value);
        }
    }
}
