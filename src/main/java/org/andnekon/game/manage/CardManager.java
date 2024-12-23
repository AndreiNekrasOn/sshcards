package org.andnekon.game.manage;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.CardFactory;
import org.andnekon.game.entity.Player;

public class CardManager {

    private Player player;

    public CardManager(Player player) {
        this.player = player;
    }

    public void initializeDefaultDeck() {
        for (int i = 0; i < 4; i++) {
            addCard(CardFactory.getCard(player, "Shot"));
        }
        for (int i = 0; i < 3; i++) {
            addCard(CardFactory.getCard(player, "Armor Up"));
        }
        addCard(CardFactory.getCard(player, "Lucky Shot"));
    }

    public void addCard(Card card) {
        String name = card.getName();
        if (CardFactory.SHOTS.contains(name)) {
            player.getShotDeck().add(card);
            return;
        } else if (CardFactory.ARMORS.contains(name) || CardFactory.STATUSES.contains(name)) {
            player.getArmorDeck().add(card);
            return;
            // } else if (CardFactory.STATUSES.contains(name)) {
            //     player.getStatusDeck().add(card);
        }
        throw new IllegalStateException("Unknown card: " + name);
    }
}
