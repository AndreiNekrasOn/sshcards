package org.andnekon.game;

import org.andnekon.game.action.CardFactory;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.entity.enemy.EnemyFactory;

public class GameSession {

    private Enemy enemy;
    private Player player;
    private int turn;
    private Enemy enemyNavRight;
    private Enemy enemyNavLeft;
    private boolean navigationInit;

    public Enemy getEnemyNavRight() {
        return enemyNavRight;
    }

    public Enemy getEnemyNavLeft() {
        return enemyNavLeft;
    }
    public Enemy getEnemy() {
        return enemy;
    }
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void initBattle() {
        this.player.setDefense(0);
        this.turn = 1;
        this.player.setBattleDeck();
    }
    // public void initTurn() {
    //     this.player.setEnergy(3);
    //     this.enemy.fillIntents(this.player);
    //     this.enemy.setDefense(0);
    // }
    public void incTurn() {
        this.turn++;
        // initTurn();
    }
    public void initializeDefaultDeck() {
        for (int i = 0; i < 4; i++) {
            player.addCard(CardFactory.getCard(player, "Shoot"));
        }
        for (int i = 0; i < 3; i++) {
            player.addCard(CardFactory.getCard(player, "Defend"));
        }
    }
    public void initNavigation() {
        this.enemyNavLeft = EnemyFactory.getRandomEnemy();
        this.enemyNavRight = EnemyFactory.getRandomEnemy();
        this.navigationInit = true;
    }

    public boolean isNavigationInit() {
        return navigationInit;
    }

    public void setNavigationInit(boolean navigationInit) {
        this.navigationInit = navigationInit;
    }
}

