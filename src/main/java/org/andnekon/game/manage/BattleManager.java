package org.andnekon.game.manage;

import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;

public class BattleManager implements StateManager {

    private Player player;
    private Enemy enemy;

    /** Turn number, with turn 0 meaning that the battle is not initialized */
    private int turn;

    public BattleManager(Player player) {
        this.player = player;
    }

    @Override
    public void init() {
        this.turn = 1;
        this.player.initBattle();
    }

    @Override
    public boolean isInit() {
        return this.turn > 0;
    }

    @Override
    public void setInit(boolean init) {
        this.turn = init ? 1 : 0;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void initTurn() {
        this.turn++;
        this.player.initTurn();
        this.enemy.fillIntents(this.player);
    }

    public int getTurnNumber() {
        return turn;
    }

    public int getTurn() {
        return turn;
    }
}
