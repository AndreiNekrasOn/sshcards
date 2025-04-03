package org.andnekon.game.action;

import org.andnekon.game.entity.Entity;
import org.andnekon.game.manage.BattleManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Intent {

    protected List<Entity> targets;
    protected Entity source;
    protected int value;
    protected BattleManager manager;

    /** For player targets should be null, since execute(targets) is used */
    public Intent(Entity source, int value, BattleManager manager, Entity... targets) {
        this.targets = new ArrayList<>();
        if (targets != null && targets.length != 0) {
            this.targets.addAll(List.of(targets));
        }
        this.source = source;
        this.value = value;
        this.manager = manager;
    }

    public void execute() {
        for (Entity target : this.targets) {
            execute(target);
        }
    }

    public abstract void execute(Entity... targets);

    public abstract String getName();

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("[%s for %d on ", getName(), value));
        for (Entity target : targets) {
            s.append(target.toString() + ", ");
        }
        s.append("], ");
        return s.toString();
    }
}
