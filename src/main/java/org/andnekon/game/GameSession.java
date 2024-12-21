package org.andnekon.game;

import org.andnekon.game.action.CardFactory;
import org.andnekon.game.action.CardName;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.entity.enemy.EnemyFactory;
import org.andnekon.game.state.State;
import org.andnekon.view.HelpType;

public class GameSession {

    private Enemy enemy;
    private Player player;
    private int turn;
    private Enemy enemyNavRight;
    private Enemy enemyNavLeft;
    private boolean navigationInit;
    private HelpType helpType;
    private State previousState;

    public HelpType getHelpType() {
        return helpType;
    }

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
        this.turn = 1;
        this.player.initBattle();
    }

    public void initTurn() {
        this.turn++;
        this.player.initTurn();
        this.enemy.fillIntents(this.player);
    }

    public void initializeDefaultDeck() {
        for (int i = 0; i < 4; i++) {
            player.addCard(CardFactory.getCard(player, CardName.SHOT));
        }
        for (int i = 0; i < 3; i++) {
            player.addCard(CardFactory.getCard(player, CardName.ARMORUP));
        }
        player.addCard(CardFactory.getCard(player, CardName.LUCKY_SHOT));
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

    public int getTurnNumber() {
        return turn;
    }

    public void setHelpType(HelpType type) {
        this.helpType = type;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public State getPreviousState() {
        return previousState;
    }
}
