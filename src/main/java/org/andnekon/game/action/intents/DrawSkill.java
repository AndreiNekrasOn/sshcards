package org.andnekon.game.action.intents;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;

public class DrawSkill extends Intent {

    public DrawSkill(Entity source, int value, Entity... targets) {
        super(source, value, targets);
    }

    @Override
    public void execute(Entity... ignored) {
        assert source instanceof Player;
        Player p = (Player) source;
        for (int i = 0; i < value; i++) {
            p.getArmorDeck().drawCard();
        }
    }

    @Override
    public String getName() {
        return "DrawAttack";
    }
}
