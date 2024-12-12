package org.andnekon.game.entity;

import org.andnekon.game.action.Card;

import java.util.ArrayList;
import java.util.List;

/** Player model. */
public class Player extends Entity {

    public static final int BATTLE_DECK_SIZE = 4;
    private List<Card> deck;

    private List<Card> battleDeck;

    private int energy;

    public Player() {
        this.hp = 50;
        this.maxHp = 50;
        this.defense = 0;
        this.deck = new ArrayList<>();
    }

    public int getEnergy() {
        return energy;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public void setBattleDeck() {
        if (deck.size() < BATTLE_DECK_SIZE) {
            this.battleDeck = deck;
            return;
        }
        // select BATTLE_DECK_SIZE random cards to add to battle deck
        List<Card> newDeck = new ArrayList<>();
        for (int i = 0; i < BATTLE_DECK_SIZE; i++) {
            int index = (int) (Math.random() * deck.size());
            newDeck.add(deck.get(index));
        }
        this.battleDeck = newDeck;
    }

    public void useCard(Card card, Entity target) {
        if (card.getIntent().isTargetSelf()) {
            card.getIntent().execute();
        } else {
            card.getIntent().execute(target);
        }
        this.energy -= card.getCost();
    }

    public List<Card> getBattleDeck() {
        return battleDeck;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
