package org.andnekon.game.state.balance;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;
import org.andnekon.game.action.CardFactory;
import org.andnekon.game.state.State;

/**
 * Draft
 */
public class Draft extends State {

    private int idx = 0;

    private List<String> cards;

    public Draft(GameSession session) {
        super(session);
        cards = new ArrayList<>();
        cards.addAll(CardFactory.SHOTS);
        cards.addAll(CardFactory.ARMORS);
        cards.addAll(CardFactory.STATUSES);
        session.reset(); // make this optional???
        idx = 0;
    }

    @Override
    public State handleInput(GameAction gameAction) {
        int max = cards.size();
        switch (gameAction.action()) {
            case DRAFT_NEXT:
                // should be synced with View
                // this is for testing, so it's fine
                idx++;
                break;
            case DRAFT_ADD:
                session.getCardManager().addCard(
                        CardFactory.getCard(session.getPlayer(), cards.get(idx % max)));
                break;
            case DRAFT_SKIP:
                return new Select(session);
            default:
                break;
        }
        return this;
    }

    @Override
    protected void setType() {
        this.type = State.Type.BALANCE_DRAFT;
    }
}
