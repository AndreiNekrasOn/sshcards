package org.andnekon.game.action.intents.player;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;
import org.andnekon.game.manage.BattleManager;

public class DrawAttack extends Intent {

    public DrawAttack(Entity source, int value, BattleManager manager, Entity... targets) {
        super(source, value, manager, targets);
    }

    @Override
    public void execute(Entity... targets) {
        assert source != null && source instanceof Player;
        Player p = (Player) source;
        for (int i = 0; i < value; i++) {
            p.getShotDeck().drawCard();
        }
    }

    @Override
    public String getName() {
        return "DrawAttack";
    }
}
