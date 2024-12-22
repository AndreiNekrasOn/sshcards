package org.andnekon.game;

import org.andnekon.game.action.CardFactory;
import org.andnekon.game.action.CardName;
import org.andnekon.game.entity.Player;
import org.andnekon.game.state.State;
import org.andnekon.game.state.manage.BattleManager;
import org.andnekon.game.state.manage.NavigationManager;
import org.andnekon.game.state.manage.RewardManager;
import org.andnekon.view.HelpType;

public class GameSession {

    private Player player;

    private NavigationManager navigationManager;
    private RewardManager rewardManager;
    private BattleManager battleManager;

    private HelpType helpType;
    private State previousState;
    private boolean end = false;

    public GameSession(Player player) {
        this.player = player;
        this.navigationManager = new NavigationManager();
        this.rewardManager = new RewardManager(player);
        this.battleManager = new BattleManager(player);
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

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public BattleManager getBattleManager() {
        return battleManager;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setHelpType(HelpType type) {
        this.helpType = type;
    }

    public HelpType getHelpType() {
        return helpType;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void end() {
        this.end = true;
    }

    public boolean isEnd() {
        return this.end;
    }
}
