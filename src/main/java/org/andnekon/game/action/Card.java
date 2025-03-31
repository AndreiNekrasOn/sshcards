package org.andnekon.game.action;

import org.andnekon.game.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Card {

    private static AtomicLong allId = new AtomicLong(0);
    private long id;
    private String name;
    private int cost;
    List<Intent> intents;
    private String description;
    private String art;

    public Card() {
        this.intents = new ArrayList<>();
        this.id = allId.getAndAdd(1);
    }

    protected Card(String name, int cost, Intent... intents) {
        this.name = name;
        this.cost = cost;
        this.intents = new ArrayList<>();
        // if (intents != null && intents.length != 0) {
        //     this.intents.addAll(List.of(intents));
        // }
        this.id = allId.getAndAdd(1);
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public Intent[] getIntents() {
        return intents.toArray(Intent[]::new);
    }

    public String getDescription() {
        return description;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void addIntent(Intent intent) {
        this.intents.add(intent);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }



}
