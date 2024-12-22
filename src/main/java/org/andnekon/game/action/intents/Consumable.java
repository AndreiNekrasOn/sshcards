package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;

/** Consumable */
public class Consumable extends Intent {

    public Consumable(Entity source, int value, Entity... targets) {
        super(source, value, targets);
    }

    @Override
    public void execute(Entity... targets) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public String getName() {
        return "Consumable";
    }
}
