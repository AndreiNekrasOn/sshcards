package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;

import java.util.List;

public class Reward extends State {

    public Reward(GameSession session) {
        super(session);

        if (!session.getRewardManager().isInit()) {
            session.getRewardManager().init();
        }
    }

    @Override
    public State handleInput(GameAction action) {
        if (action.action() != GameAction.Type.NAVIGATION) {
            throw new UnsupportedOperationException("Wrong input for Reward state");
        }
        List<Card> rewardOptions = session.getRewardManager().getRewardOptions();
        int actionId = action.id() - 1;
        if (actionId >= 0 && actionId < rewardOptions.size()) {
            session.getCardManager().addCard(rewardOptions.get(actionId));
            session.getRewardManager().setInit(false);
        }
        return new Navigation(session);
    }

    @Override
    protected void setType() {
        this.type = State.Type.REWARD;
    }
}
