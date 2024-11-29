package org.andnekon.game.entity;

public abstract class Entity {
    protected int maxHp;
    protected int hp;
    protected int defense;

    // TODO: move takeDamage to entity, Player and Enemy should be subclasses of Entity
    public void takeDamage( int damage ) {
        if (damage >= this.defense) {
            damage -= this.defense;
            this.hp -= damage;
            this.defense = 0;
        } else {
            this.defense -= damage;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void heal(int value) {
        this.hp = Math.min(this.maxHp, this.hp + value);
    }
}

