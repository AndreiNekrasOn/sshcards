package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;

/** Support */
public class Support extends Intent {

    public Support(Entity source, int value, Entity targets) {
        super(source, value, targets);
    }

    @Override
    public void execute(Entity... targets) {
        for (Entity target : targets) {
            target.heal(value); // TODO: apply effect instead
        }
    }
}
