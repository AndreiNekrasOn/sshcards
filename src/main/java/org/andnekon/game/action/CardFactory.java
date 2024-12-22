package org.andnekon.game.action;

import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.entity.Entity;

public class CardFactory {

    private CardFactory() {}

    public static Card getCard(Entity player, CardName name) {
        return switch (name) {
            case SHOT -> new Shot("Shot", 1, new Attack(player, 1));
            case LUCKY_SHOT ->
                    new Shot("Luck Shot", 2, new Attack(player, 4), new Attack(player, 1, player));
            case TRIPPLE_SHOT ->
                    new Shot(
                            "Triple Shot",
                            3,
                            new Attack(player, 1),
                            new Attack(player, 1),
                            new Attack(player, 1));
            case OVERDRIVE -> new Shot("Explode", -2, new Attack(player, 5, player));
            case ARMORUP -> new Armor("Armor Up!", 1, new Defence(player, 1, player));
            case BETTERARMOR ->
                    new Armor(
                            "Better Armor!",
                            2,
                            new Attack(player, 1, player),
                            new Defence(player, 5, player));
            case THORNSARMOR ->
                    new Armor(
                            "Thorns Armor!",
                            1,
                            new Defence(player, 2, player),
                            new Attack(player, 1));
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    public static Card getRandomCard(Entity player) {
        int rndIdx = (int) (Math.random() * CardName.values().length);
        return getCard(player, CardName.values()[rndIdx]);
    }
}
