package org.andnekon.game.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    protected int maxHp;
    protected int hp;
    protected int defense;

    // TODO: use decorator here?
    public Map<String, Integer> effectCounter = new HashMap<>();

    public void onTurnBegin(Entity... targets) {
        this.hp += effectCounter.getOrDefault("Heal", 0);
        this.hp -= effectCounter.getOrDefault("Poison", 0);

        effectCounter.put("Heal", 0);
        for (String effectName : effectCounter.keySet()) {
            effectCounter.put(effectName, Math.max(0, effectCounter.get(effectName) - 1));
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

    private int modifyDamage(int damage) {
        int vuln = effectCounter.getOrDefault("Vulnurable", 0);
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
