package org.andnekon.game.action;

import org.andnekon.game.entity.Entity;

public abstract class Card {

    private static long allId = 0; // TODO: concurrency check, overflow check
    private String name;
    private int cost;
    Intent[] intents;
    private long id;

    protected Card(String name, int cost, Intent... intent) {
        this.name = name;
        this.cost = cost;
        this.intents = intent;
        this.id = allId++;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Intent[] getIntents() {
        return intents;
    }

    public void use(Entity target) {
        for (Intent intent : intents) {
            intent.execute(target);
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%d) [id=%d]", name, cost, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Card)) {
            return false;
        }
        return this.getId() == ((Card) obj).getId();
    }

    private long getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        // TODO: think if this ever will be a problem
        return (int) (this.id % Integer.MAX_VALUE);
    }
}
