package org.andnekon.game.entity;

import org.andnekon.game.entity.enemy.Enemy;

import java.util.List;

/** Combat */
public class Combat {

    private List<Enemy> enemies;

    private int idx;

    private String name;

    public Combat(String name, Enemy... enemies) {
        this.name = name;
        this.enemies = List.of(enemies);
        this.idx = 0;
    }

    public Enemy[] getEnemies() {
        return enemies.toArray(Enemy[]::new);
    }

    public void selectNext() {
        idx = (idx + 1) % enemies.size();
    }

    public int getIdx() {
        return idx;
    }

    public Enemy getSelectedEnemy() {
        return enemies.get(idx);
    }

    public String getName() {
        return name;
    }

    /** Combat is done when all the enemies are dead */
    public boolean isEnded() {
        for (Enemy enemy : enemies) {
            if (enemy.getHp() > 0) {
                return false;
            }
        }
        return true;
    }

    public void refresh() {
        enemies = enemies.stream().filter(e -> e.getHp() > 0).toList();
    }

    public void onTurnBegin() {
        refresh();
        enemies.forEach(e -> e.onTurnBegin());
    }

    public void onTurnEnd() {
        refresh();
        enemies.forEach(e -> e.clearIntents());
    }
}
