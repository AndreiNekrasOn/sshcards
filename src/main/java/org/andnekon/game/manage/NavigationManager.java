package org.andnekon.game.manage;

import org.andnekon.game.entity.Combat;
import org.andnekon.game.entity.enemy.CombatFactory;

import java.util.ArrayList;
import java.util.List;

/** NavigationManager */
public class NavigationManager implements StateManager {

    private boolean navigationInit;

    private List<Combat> navigationOptions;
    private static final int navigationOptionsSize = 2;

    public NavigationManager() {
        this.navigationInit = false;
        this.navigationOptions = new ArrayList<>();
    }

    public List<Combat> getNavigationOptions() {
        return this.navigationOptions;
    }

    public String[] getNavigationOptionsArray() {
        return this.navigationOptions.stream().map(Combat::getName).toArray(String[]::new);
    }

    @Override
    public void init() {
        navigationOptions.clear();
        for (int i = 0; i < navigationOptionsSize; i++) {
            navigationOptions.add(CombatFactory.getCombat("Horrors"));
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
