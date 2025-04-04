package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.manage.BattleManager;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Three attack patterns:
 *
 * <ul>
 *   <li>Chomp
 *   <li>Eldrich blast
 *   <li>Tentacle defence
 * </ul>
 *
 * Always starts with Chomp. Then changes to Eldrich blast or Tentacle tickle. Cannot have two
 * Tentacle defences in a row, rerandoms
 */
public class TheHorror extends Enemy {

    // each state corresponds to its attack pattern, mod 3
    private List<Integer> stateHistory = new ArrayList<>();

    @Override
    public void fillIntents(BattleManager manager) {
        changeState();
        int currentState = stateHistory.get(stateHistory.size() - 1);
        switch (currentState) {
            case 0:
                this.currentIntents.add(new Attack(this, dmg, manager, manager.getPlayer()));
                break;
            case 1:
                this.currentIntents.add(new Attack(this, dmg + 1, manager, manager.getPlayer()));
                this.currentIntents.add(new Attack(this, 1, manager, this));
                break;
            case 2:
                this.currentIntents.add(new Attack(this, dmg / 3, manager, manager.getPlayer()));
                this.currentIntents.add(new Defence(this, armor, manager, this));
                this.currentIntents.add(new Defence(this, armor, manager, manager.getPlayer()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + stateHistory);
        }
        LoggerFactory.getLogger(TheHorror.class)
                .debug("TheHorror has {} intents", this.currentIntents.size());
    }

    @Override
    public void clearIntents() {
        // The Horror should be terrifying, so it accumulates attacks
    }

    private void changeState() {
        if (stateHistory.isEmpty()) {
            stateHistory.add(0);
            return;
        }
        int previousState = stateHistory.get(stateHistory.size() - 1);
        if (previousState == 2) {
            stateHistory.add((int) (Math.random() * 2));
            return;
        }
        stateHistory.add((int) (Math.random() * 3));
    }
}
