package org.andnekon.game;

import java.util.ArrayList;
import java.util.List;

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

    protected abstract void fillIntents(Player player);

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
}

