package org.andnekon.game.action;

import org.andnekon.game.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Intent {

    protected List<Entity> targets;
    protected Entity source;
    protected int value;

    public Intent(Entity source, int value, Entity... targets) {
        this.targets = new ArrayList<>();
        this.targets.addAll(List.of(targets));
        this.source = source;
        this.value = value;
    }

    public void execute() {
        for (Entity target : this.targets) {
            execute(target);
        }
    }

    public abstract void execute(Entity... targets);

    // TODO: add descriptions to all intents...
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("[%s for %d on ", this.getClass().getSimpleName(), value));
        for (Entity target : targets) {
            s.append(target.toString() + ", ");
        }
        s.append("], ");
        return s.toString();
    }
}
