package org.andnekon.game;

public class Card {

    private String name;
    private int cost;
    Intent intent;
    public Card(String name, int cost, Intent intent) {
        this.name = name;
        this.cost = cost;
        this.intent = intent;
    }
    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
    public Intent getIntent() {
        return intent;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, cost);
    }
}

