package org.andnekon.game;

import java.util.ArrayList;
import java.util.List;

public class Intent {

    public enum IntentType {
        ATTACK,
        DEFEND,
        HEAL
    }
    public List<Entity> targets;
    public Entity source;

    public IntentType type;

    public int value;

    public Intent(Entity source, IntentType type, int value, Entity... targets) {
        this.targets = new ArrayList<>();
        this.targets.addAll(List.of(targets));
        this.source = source;
        this.type = type;
        this.value = value;
    }

    public void execute() {
        for (Entity target : this.targets) {
            execute(target);
        }
    }

    public void execute(Entity... targets) {
        switch (type) {
            case ATTACK:
                for (Entity target : targets) {
                    target.takeDamage(value);
                }
                break;
            case DEFEND:
                for (Entity target : targets) {
                    target.setDefense(target.getDefense() + value);
                }
                break;
            case HEAL:
                for (Entity target : targets) {
                    target.heal(value);
                }
                break;
        }

    }

    public boolean isTargetSelf() {
        return this.targets.size() == 1 && this.targets.get(0) instanceof Player;
    }

    // add descriptions to all intents...
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("[do %s for %d on ", type, value));
        for (Entity target : targets) {
            if (target instanceof Player) {
                s.append("Player, ");
            } else if (target instanceof Enemy){
                s.append(String.format("%s, ", ((Enemy) target).display()));
            }
        }
        s.append("]");
        return s.toString();

    }
}

