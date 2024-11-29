package org.andnekon.game;

public class TheHorror extends Enemy {

    public TheHorror() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String display() {
        return "The Horror";

    }

    @Override
    protected void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 5, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.DEFEND, 1, this));
    }
}

