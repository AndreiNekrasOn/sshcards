package org.andnekon.utils.config;

import java.util.Map;

/** EnemyBase */
public class EnemyBase {

    private String name;
    private String resource;
    private int hp;
    private int maxHp;
    private int dmg;
    private int armor;
    private Map<String, Integer> misc;

    public EnemyBase(
            String name,
            String resource,
            int hp,
            int maxHp,
            int dmg,
            int armor,
            Map<String, Integer> misc) {
        this.name = name;
        this.resource = resource;
        this.hp = hp;
        this.maxHp = maxHp;
        this.dmg = dmg;
        this.armor = armor;
        this.misc = misc;
    }

    public String name() {
        return name;
    }

    public String resource() {
        return resource;
    }

    public int hp() {
        return hp;
    }

    public int maxHp() {
        return maxHp;
    }

    public int dmg() {
        return dmg;
    }

    public int armor() {
        return armor;
    }

    public int getParam(String name) {
        return misc.get(name);
    }
}
