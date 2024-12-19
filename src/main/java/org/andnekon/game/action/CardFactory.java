package org.andnekon.game.action;

import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.entity.Player;

public class CardFactory {

    private CardFactory() {}

    public static Card getCard(Player player, CardName name) {
        return switch (name) {
            case SHOT -> new Shot("Shot", 1, new Attack(player, 1));
            case ARMORUP -> new Armor("Armor Up!", 1, new Defence(player, 1, player));
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
