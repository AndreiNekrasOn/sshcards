package org.andnekon.game.entity.enemy;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;
import org.andnekon.utils.config.EnemyBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Enemy extends Entity {

    protected List<Intent> currentIntents;

    protected int turnNumber;

    protected int dmg;
    protected int armor;
    protected String name;

    protected String ascii;

    protected Map<String, Integer> misc;

    public Enemy() {
        currentIntents = new ArrayList<>();
        turnNumber = 0;
    }

    @Override
    public void onTurnBegin(Entity... targets) {
        super.onTurnBegin(targets);
        turnNumber++;
        setDefense(0);
        for (Intent intent : currentIntents) {
            intent.execute();
        }
    }

    public abstract void fillIntents(Player player);

    public String displayIntents() {
        StringBuilder intents = new StringBuilder();
        for (Intent intent : currentIntents) {
            intents.append(intent);
        }
        return intents.toString();
    }

    public void clearIntents() {
        currentIntents.clear();
    }

    public List<Intent> getCurrentIntents() {
        return currentIntents;
    }

    @Override
    public String toString() {
        return name;
    }

    public Enemy withStats(EnemyBase base) {
        this.name = base.name();
        this.ascii = base.resource();
        this.hp = base.hp();
        this.maxHp = base.maxHp();
        this.dmg = base.dmg();
        this.armor = base.armor();
        this.misc = base.getMisc();
        return this;
    }

    public String getAscii() {
        return ascii;
    }
}
