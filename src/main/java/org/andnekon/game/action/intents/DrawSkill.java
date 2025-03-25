package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;

public class DrawSkill extends Intent {

    public DrawSkill(Entity source, int value, Entity... targets) {
        super(source, value, targets);
    }

    @Override
    public void execute(Entity... targets) {
        assert targets.length == 1 && (targets[0] instanceof Player);
        Player p = (Player) targets[0];
        p.getShotDeck().drawCard();
    }

    @Override
    public String getName() {
        return "DrawAttack";
    }
}
