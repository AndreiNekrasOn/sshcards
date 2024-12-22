package org.andnekon.game.state.manage;

import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.entity.enemy.EnemyFactory;

import java.util.ArrayList;
import java.util.List;

/** NavigationManager */
public class NavigationManager implements StateManager {

    private boolean navigationInit;

    private List<Enemy> navigationOptions;
    private static final int navigationOptionsSize = 2;

    public NavigationManager() {
        this.navigationInit = false;
        this.navigationOptions = new ArrayList<>();
    }

    public List<Enemy> getNavigationOptions() {
        return this.navigationOptions;
    }

    public String[] getNavigationOptionsArray() {
        return this.navigationOptions.stream().map(Enemy::toString).toArray(String[]::new);
    }

    @Override
    public void init() {
        navigationOptions.clear();
        for (int i = 0; i < navigationOptionsSize; i++) {
            navigationOptions.add(EnemyFactory.getRandomEnemy());
        }
        this.navigationInit = true;
    }

    @Override
    public boolean isInit() {
        return this.navigationInit;
    }

    @Override
    public void setInit(boolean init) {
        this.navigationInit = init;
    }
}
