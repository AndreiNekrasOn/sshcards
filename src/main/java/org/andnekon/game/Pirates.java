package org.andnekon.game;

public class Pirates extends Enemy {

    public Pirates() {
        super();
        this.hp = 5;
        this.maxHp = 5;
    }
    @Override
    public String display() {
        return "Pirates";
    }


    @Override
    protected void fillIntents(Player player) {
        this.currentIntents.add(new Intent(this, Intent.IntentType.ATTACK, 2, player));
        this.currentIntents.add(new Intent(this, Intent.IntentType.DEFEND, 1, this));
    }

}

