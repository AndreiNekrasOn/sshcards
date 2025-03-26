package org.andnekon.game.entity;


import org.andnekon.game.entity.enemy.Enemy;

/** Combat */
public class Combat {

    private Enemy[] enemies;

    private int idx;

    private String name;

    public Combat(String name, Enemy... enemies) {
        this.name = name;
        this.enemies = enemies;
        this.idx = 0;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public void selectNext() {
        idx = (idx + 1) % enemies.length;
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

    /** Combat is done when all the enemies are dead */
    public boolean isEnded() {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].getHp() > 0) {
                return false;
            }
        }
        return true;
    }

	public void onTurnBegin() {
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].onTurnBegin();
        }
	}

	public void onTurnEnd() {
        for (int i = 0; i < enemies.length; i++) {
            enemies[i].clearIntents();
        }
	}
}
