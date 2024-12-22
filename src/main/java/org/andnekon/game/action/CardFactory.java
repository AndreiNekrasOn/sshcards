package org.andnekon.game.action;

import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.entity.Entity;

import java.util.List;

public class CardFactory {

    private CardFactory() {}

    public static final List<String> SHOTS = List.of("Shot", "Lucky Shot", "Triple Shot");

    public static final List<String> ARMORS =
            List.of("Armor Up", "Better Armor", "Thorns Armor", "Overdrive");

    public static Card getCard(Entity player, String name) {
        return switch (name) {
            case "Shot" -> new Shot(name, 1, new Attack(player, 1));
            case "Lucky Shot" ->
                    new Shot(name, 2, new Attack(player, 4), new Attack(player, 1, player));
            case "Triple Shot" ->
                    new Shot(
                            name,
                            3,
                            new Attack(player, 1),
                            new Attack(player, 1),
                            new Attack(player, 1));
            case "Overdrive" -> new Shot(name, -2, new Attack(player, 5, player));
            case "Armor Up" -> new Armor(name, 1, new Defence(player, 1, player));
            case "Better Armor" ->
                    new Armor(
                            name, 2, new Attack(player, 1, player), new Defence(player, 5, player));
            case "Thorns Armor" ->
                    new Armor(name, 1, new Defence(player, 2, player), new Attack(player, 1));
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    public static Card getRandomCard(Entity player) {
        int total = SHOTS.size() + ARMORS.size();
        int random = (int) (Math.random() * total);
        if (random < SHOTS.size()) {
            return getCard(player, SHOTS.get(random));
        } else {
            return getCard(player, ARMORS.get(random - SHOTS.size()));
        }
    }
}
