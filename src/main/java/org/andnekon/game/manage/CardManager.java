package org.andnekon.game.manage;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.CardFactory;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.entity.Player;

public class CardManager {

    private BattleManager battleManager;

    public CardManager(BattleManager manager) {
        this.battleManager = manager;
    }

    public void initializeDefaultDeck() {
        for (int i = 0; i < 4; i++) {
            addCard(CardFactory.getCard(battleManager, "Shot"));
        }
        for (int i = 0; i < 3; i++) {
            addCard(CardFactory.getCard(battleManager, "Armor Up"));
        }
        addCard(CardFactory.getCard(battleManager, "Lucky Shot"));
    }

    public void addCard(Card card) {
        Player player = battleManager.getPlayer();
        if (card instanceof Shot) {
            player.getShotDeck().add(card);
        } else {
            player.getArmorDeck().add(card);
            return;
        }
    }
}
