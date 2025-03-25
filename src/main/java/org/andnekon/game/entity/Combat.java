package org.andnekon.game.entity;


import org.andnekon.game.entity.enemy.Enemy;

/** Combat */
public class Combat {

    private Enemy[] enemies;

    private int idx;

    private String name;

    public Combat(String name, Enemy... enemies) {
        this.enemies = enemies;
        this.idx = 0;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void selectNext() {
        idx += idx % enemies.length;
    }

    public int getIdx() {
        return idx;
    }

    public Enemy getSelectedEnemy() {
        return enemies[idx];
    }

    public String getName() {
        return name;
    }
}
