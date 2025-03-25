package org.andnekon.game.entity;

import org.andnekon.game.action.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// TODO: tests
public class Deck {

    private List<Card> total;

    private List<Card> hand;
    private List<Card> draw; // should be Queue

    private List<Card> discard;

    private int handSize;

    public Deck(int handSize) {
        this.handSize = handSize;
        this.total = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.draw = new LinkedList<>();
        this.discard = new ArrayList<>();
    }

    public List<Card> getTotal() {
        return total;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<Card> getDraw() {
        return draw;
    }

    public List<Card> getDiscard() {
        return discard;
    }

    public void initBattle() {
        this.draw.clear();
        this.discard.clear();
        this.draw.addAll(this.total);
        Collections.shuffle(this.draw);
    }

    // initBattle should have been called before this
    public void initHand() {
        // discard hand
        while (!hand.isEmpty()) {
            discard.add(hand.remove(0));
        }

        // shiffle discard if needed
        if (draw.size() < handSize) {
            Collections.shuffle(discard);
            draw.addAll(discard);
        }

        // add cards from the draw to the hand
        while (!draw.isEmpty() && hand.size() < handSize) {
            drawCard();
        }
    }

    public void drawCard() {
        if (draw.size() == 0) {
            Collections.shuffle(discard);
            draw.addAll(discard);
        }
        if (!draw.isEmpty() && hand.size() < handSize) {
            hand.add(draw.remove(0));
        }
    }

    public void useCard(Card card) throws CardNotInHandException {
        if (!hand.contains(card)) {
            throw new CardNotInHandException();
        }
        hand.remove(card);
        discard.add(card);
    }

    public void add(Card card) {
        this.total.add(card);
    }
}
