package org.andnekon.game;

public class RegularShip extends Enemy {

    public RegularShip() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String display() {
        return "Regular Ship";
    }

    @Override
    protected void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 1, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 1, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 1, player));
    }

}

