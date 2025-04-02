package org.andnekon.game.action;

import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.action.cards.Status;
import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.action.intents.player.DrawAttack;
import org.andnekon.game.action.intents.player.DrawSkill;
import org.andnekon.game.action.intents.Effect;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;

/** CardBuilder */
public class CardBuilder {

    private Card card;

    public CardBuilder(String type, String name, int cost) {
        this.card =
                switch (type) {
                    case "attack" -> new Shot(name, cost);
                    case "skill" -> new Armor(name, cost);
                    case "status" -> new Status(name, cost);
                    default -> throw new IllegalStateException("Unknown card type " + type);
                };
        this.card.setArt("tui/cards/Shot");
        this.card.setDescription("Card is broken");
    }

    public CardBuilder description(String description) {
        this.card.setDescription(description);
        return this;
    }

    public CardBuilder art(String art) {
        this.card.setArt(art);
        return this;
    }

    public CardBuilder addIntent(
            String type, String target, int value, String payload, Player player) {
        Entity[] targets =
                switch (target) {
                    case "enemy" -> null;
                    case "self" -> new Entity[] {player};
                    case "no" -> new Entity[] {player};
                    default -> throw new IllegalStateException("Unknown target type " + type);
                };
        Intent intent =
                switch (type) {
                    case "attack" -> new Attack(player, value, targets);
                    case "defence" -> new Defence(player, value, targets);
                    case "effect" -> new Effect(player, payload, value, targets);
                    case "draw" -> {
                        if ("attack".equals(payload)) {
                            yield new DrawAttack(player, value, targets);
                        } else {
                            yield new DrawSkill(player, value, targets);
                        }
                    }
                    default -> throw new IllegalStateException("Unknown intent type " + type);
                };
        this.card.addIntent(intent);
        return this;
    }

    public Card build() {
        return this.card;
    }
}
