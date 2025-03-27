package org.andnekon.game.entity;

import org.andnekon.game.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

/** Combat */
public class Combat {

    // is this only for View purpose? Is this the place for it then?
    // not only for single view -> so it's generally useful
    class IdentifiedEnemy extends Enemy {

        private Enemy enemy;

        private int id;

        public IdentifiedEnemy(Enemy enemy, int id) {
            this.enemy = enemy;
            this.id = id;
        }
        @Override
        public void fillIntents(Player player) {
            enemy.fillIntents(player);
        }

        @Override
        public String toString() {
            return "#" + id + " " + enemy.toString();
        }
    }

    private List<IdentifiedEnemy> enemies;

    private int idx;

    private String name;

    public Combat(String name, Enemy... enemies) {
        this.name = name;
        this.enemies = new ArrayList<>();
        for (int i = 0; i < enemies.length; i++) {
            this.enemies.add(new IdentifiedEnemy(enemies[i], i));
        }
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
        refresh(); // some are ded on turn start because Corrosion
        enemies.forEach(e -> e.onTurnBegin());
    }

    public void onTurnEnd() {
        refresh();
        enemies.forEach(e -> e.clearIntents());
    }
}
