package org.andnekon.game.entity;

import org.andnekon.game.action.Card;

/** Player model. */
public class Player extends Entity {

    private int numInBattleAttacks;
    private int numInBattleDeffence;

    private Deck shotDeck;
    private Deck armorDeck;

    private int energy;

    public Player() {
        this.hp = 50;
        this.maxHp = 50;
        this.defense = 0;

        numInBattleAttacks = 2;
        numInBattleDeffence = 1;

        shotDeck = new Deck(numInBattleAttacks);
        armorDeck = new Deck(numInBattleDeffence);
    }

    public int getEnergy() {
        return energy;
    }

    /** Modifies player's deck for each type of Card */
    public void initTurn() {
        shotDeck.initHand();
        armorDeck.initHand();
        energy = 3;
        defense = 0;
    }

    public void initBattle() {
        shotDeck.initBattle();
        armorDeck.initBattle();
    }

    public void useCard(Card card, Entity target)
            throws NotEnoughEnergyException, CardNotInHandException {
        if (card.getCost() > energy) {
            throw new NotEnoughEnergyException();
        }
        // it's cheap enough to check this
        if (shotDeck.getHand().contains(card)) {
            shotDeck.useCard(card);
        } else if (armorDeck.getHand().contains(card)) {
            armorDeck.useCard(card);
        } else {
            throw new CardNotInHandException();
        }
        card.use(target);
        energy -= card.getCost();
    }

    public Deck getShotDeck() {
        return shotDeck;
    }

    public Deck getArmorDeck() {
        return armorDeck;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return "Player";
    }
}
