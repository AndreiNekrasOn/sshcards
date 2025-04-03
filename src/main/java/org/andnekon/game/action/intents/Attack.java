package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.manage.BattleManager;

/** Attack */
public class Attack extends Intent {

    public Attack(Entity source, int value, BattleManager manager, Entity... targets) {
        super(source, value, manager, targets);
    }

    @Override
    public void execute(Entity... targets) {
        if (this.targets == null || this.targets.size() == 0) {
            doDamage(targets); // basically, do damage to player
        } else if (this.targets.size() == 1) {
            doDamage(this.targets.toArray(Entity[]::new)); // do damage to selected target
        } else {
            doDamage(this.manager.getEnemies()); // do damage to all targets
        }
    }

    private void doDamage(Entity... targets) {
        for (Entity target : targets) {
            target.takeDamage(value);
        }
    }

    @Override
    public String getName() {
        return "Attack";
    }
}
