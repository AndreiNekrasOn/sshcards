package org.andnekon.game.action;

import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.action.cards.Status;
import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.action.intents.DrawAttack;
import org.andnekon.game.action.intents.DrawSkill;
import org.andnekon.game.action.intents.Effect;
import org.andnekon.game.entity.Entity;

import java.util.List;

public class CardFactory {

    private CardFactory() {}

    public static final List<String> SHOTS =
            List.of("Shot", "Lucky Shot", "Triple Shot", "Corrosion");

    public static final List<String> ARMORS =
            List.of(
                    "Armor Up",
                    "Better Armor",
                    "Thorns Armor",
                    "Overdrive",
                    "Draw Shot",
                    "Draw Armor");

    public static final List<String> STATUSES = List.of("Crack");

    public static Card getCard(Entity player, String name) {
        return switch (name) {
            // shot
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
            // status
            case "Corrosion" -> new Status(name, 1, new Effect(player, "Poison", 1));
            case "Crack" -> new Status(name, 1, new Effect(player, "Vulnurable", 2));
            // armor
            case "Overdrive" -> new Shot(name, -2, new Attack(player, 1, player));
            case "Armor Up" -> new Armor(name, 1, new Defence(player, 1, player));
            case "Better Armor" ->
                    new Armor(
                            name, 2, new Attack(player, 1, player), new Defence(player, 5, player));
            case "Thorns Armor" ->
                    new Armor(name, 1, new Defence(player, 2, player), new Attack(player, 1));
            case "Draw Shot" -> new Armor(name, 1, new DrawAttack(player, 1));
            case "Draw Armor" -> new Armor(name, 1, new DrawSkill(player, 1));
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    public static Card getRandomCard(Entity player) {
        int shotLimit = SHOTS.size();
        int armorLimit = shotLimit + ARMORS.size();
        int statusLimit = armorLimit + STATUSES.size();
        int random = (int) (Math.random() * statusLimit);
        if (random < shotLimit) {
            return getCard(player, SHOTS.get(random));
        } else if (random < armorLimit) {
            return getCard(player, ARMORS.get(random - shotLimit));
        } else {
            return getCard(player, STATUSES.get(random - armorLimit));
        }
    }
}
