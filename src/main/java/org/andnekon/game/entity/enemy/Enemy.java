package org.andnekon.game.entity.enemy;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;


public abstract class Enemy extends Entity {

    List<Intent> currentIntents;

    Enemy() {
        currentIntents = new ArrayList<>();
    }

    public abstract String display();

    public void turn() {
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
}

