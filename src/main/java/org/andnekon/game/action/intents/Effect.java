package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;

import java.util.List;

public class Effect extends Intent {

    private String name;

    public Effect(Entity source, String name, int value, Entity... targets) {
        super(source, value, targets);
        this.name = name;
    }

    protected void addEffect(List<Entity> targets) {
        targets.forEach(t -> t.increaseEffect(name, value));
    }

    @Override
    public void execute(Entity... targets) {
        if (this.targets != null && !this.targets.isEmpty()) {
            addEffect(this.targets);
        } else {
            addEffect(List.of(targets));
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
}
