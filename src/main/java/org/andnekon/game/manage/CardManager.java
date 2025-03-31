package org.andnekon.game.manage;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.CardFactory;
import org.andnekon.game.action.cards.Shot;
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
        if (card instanceof Shot) {
            player.getShotDeck().add(card);
        } else {
            player.getArmorDeck().add(card);
            return;
        }
    }
}
