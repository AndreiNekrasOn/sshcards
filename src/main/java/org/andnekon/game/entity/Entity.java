package org.andnekon.game.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    protected int maxHp;
    protected int hp;
    protected int defense;

    /** only change via setEffect or increaseEffect */
    protected Map<String, Integer> effectCounter = new HashMap<>();

    // TODO: this is for UI reason. Is this the best solution?
    private static final int MAX_EFFECT_VALUE = 9;

    public void onTurnBegin(Entity... targets) {
        hp += effectCounter.getOrDefault("Heal", 0);
        hp = Math.min(hp, maxHp);
        hp -= effectCounter.getOrDefault("Corrosion", 0);

        effectCounter.put("Heal", 0);
        // tick everything down
        for (String eName : effectCounter.keySet()) {
            setEffect(eName, getEffectValue(eName) - 1);
        }
    }

    public void takeDamage(int damage) {
        damage = modifyDamage(damage);
        if (damage >= this.defense) {
            damage -= this.defense;
            this.hp -= damage;
            this.defense = 0;
        } else {
            this.defense -= damage;
        }
    }

    public int getEffectValue(String effect) {
        return effectCounter.getOrDefault(effect, 0);
    }

    public void increaseEffect(String effect, int value) {
        setEffect(effect, getEffectValue(effect) + value);
    }

    public void setEffect(String effect, int value) {
        value = Math.min(MAX_EFFECT_VALUE, value);
        value = Math.max(0, value);
        effectCounter.put(effect, value);
    }

    private int modifyDamage(int damage) {
        int vuln = effectCounter.getOrDefault("Crack", 0);
        return damage + vuln;
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
