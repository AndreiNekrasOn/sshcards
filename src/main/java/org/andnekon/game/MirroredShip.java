package org.andnekon.game;

public class MirroredShip extends Enemy {

    public MirroredShip() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }

    @Override
    public String display() {
        return "Mirrored Ship";
    }

    @Override
    protected void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 2, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.DEFEND, 1, this));
    }

}

