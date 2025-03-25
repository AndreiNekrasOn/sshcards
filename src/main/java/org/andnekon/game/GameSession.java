package org.andnekon.game;

import org.andnekon.game.entity.Player;
import org.andnekon.game.manage.BattleManager;
import org.andnekon.game.manage.CardManager;
import org.andnekon.game.manage.NavigationManager;
import org.andnekon.game.manage.RewardManager;
import org.andnekon.game.state.State;
import org.andnekon.view.HelpType;

public class GameSession {

    private Player player;

    private NavigationManager navigationManager;
    private RewardManager rewardManager;
    private BattleManager battleManager;
    private CardManager cardManager;

    private HelpType helpType;
    private State previousState;
    private boolean end = false;

	private boolean inBattle;

    public CardManager getCardManager() {
        return cardManager;
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

    public boolean isInBattle() {
        return this.inBattle;
    }

    public void setIsInBattle(boolean inBattle) {
        this.inBattle = inBattle;
    }

    public boolean isEnd() {
        return this.end;
    }

    public void reset() {
        this.player = new Player();
        this.navigationManager = new NavigationManager();
        this.rewardManager = new RewardManager(player);
        this.battleManager = new BattleManager(player);
        this.cardManager = new CardManager(player);
        cardManager.initializeDefaultDeck();
    }
}
