package org.andnekon.game.manage;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.CardFactory;
import org.andnekon.game.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RewardManager implements StateManager {

    private final Player player;
    private boolean rewardInit;
    private List<Card> rewardOptions;
    private static final int rewardOptionsSize = 3;

    public RewardManager(Player player) {
        this.rewardInit = false;
        this.rewardOptions = new ArrayList<>();
        this.player = player;
    }

    public List<Card> getRewardOptions() {
        return this.rewardOptions;
    }

    public int getRewardOptionsSize() {
        return rewardOptionsSize;
    }

    @Override
    public void init() {
        this.rewardOptions.clear();
        for (int i = 0; i < rewardOptionsSize; i++) {
            rewardOptions.add(CardFactory.getRandomCard(this.player));
        }
        this.rewardInit = true;
    }

    @Override
    public boolean isInit() {
        return this.rewardInit;
    }

    @Override
    public void setInit(boolean init) {
        this.rewardInit = init;
    }
}
