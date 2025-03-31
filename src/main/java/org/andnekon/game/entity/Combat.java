package org.andnekon.game.entity;

import org.andnekon.game.action.Intent;
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

        @Override
        public void clearIntents() {
            enemy.clearIntents();
        }

        @Override
        public String displayIntents() {
            return enemy.displayIntents();
        }

        @Override
        public List<Intent> getCurrentIntents() {
            return enemy.getCurrentIntents();
        }

        @Override
        public void onTurnBegin(Entity... targets) {
            enemy.onTurnBegin(targets);
        }

        @Override
        public int getDefense() {
            return enemy.getDefense();
        }

        @Override
        public int getEffectValue(String effect) {
            return enemy.getEffectValue(effect);
        }

        @Override
        public int getHp() {
            return enemy.getHp();
        }

        @Override
        public int getMaxHp() {
            return enemy.getMaxHp();
        }

        @Override
        public void heal(int value) {
            enemy.heal(value);
        }

        @Override
        public void increaseEffect(String effect, int value) {
            enemy.increaseEffect(effect, value);
        }

        @Override
        public void setDefense(int defense) {
            enemy.setDefense(defense);
        }

        @Override
        public void setEffect(String effect, int value) {
            enemy.setEffect(effect, value);
        }

        @Override
        public void setHp(int hp) {
            enemy.setHp(hp);
        }

        @Override
        public void setMaxHp(int maxHp) {
            enemy.setMaxHp(maxHp);
        }

        @Override
        public void takeDamage(int damage) {
            enemy.takeDamage(damage);
        }

        @Override
        public String getAscii() {
            return enemy.getAscii();
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
        if (enemies.size() > 0) {
            idx = (idx + 1) % enemies.size();
        }
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
