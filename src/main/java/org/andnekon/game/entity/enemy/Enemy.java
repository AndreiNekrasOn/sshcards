package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Entity {

    protected List<Intent> currentIntents;

    protected int turnNumber;

    public Enemy() {
        currentIntents = new ArrayList<>();
        turnNumber = 0;
    }

    @Override
    public void onTurnBegin(Entity... targets) {
        super.onTurnBegin(targets);
        turnNumber++;
        setDefense(0);
        for (Intent intent : currentIntents) {
            intent.execute();
        }
    }

    public abstract void fillIntents(Player player);

    public String displayIntents() {
        StringBuilder intents = new StringBuilder();
        for (Intent intent : currentIntents) {
            intents.append(intent);
        }
        return intents.toString();
    }

    public void clearIntents() {
        currentIntents.clear();
    }

    public List<Intent> getCurrentIntents() {
        return currentIntents;
    }

    @Override
    public abstract String toString();
}
