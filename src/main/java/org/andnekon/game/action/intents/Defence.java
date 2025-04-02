package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;

/** Defence */
public class Defence extends Intent {

    public Defence(Entity source, int value, Entity... targets) {
        super(source, value, targets);
    }

    @Override
    public void execute(Entity... ignored) {
        for (Entity target : this.targets) { // applies to self
            target.setDefense(target.getDefense() + value);
        }
    }

    @Override
    public String getName() {
        return "Defence";
    }
}
