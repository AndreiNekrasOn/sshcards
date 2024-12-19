package org.andnekon.game.entity;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;

/** Player model. */
public class Player extends Entity {

    private int numInBattleAttacks;
    private int numInBattleDeffence;
    // private int numInBattleSupport;
    // private int numMissles; // TODO: add missles-logic

    private Deck<Shot> shotDeck;
    private Deck<Armor> armorDeck;
    // private Set<Effect> effectDeck;

    private int energy;

    public Player() {
        this.hp = 50;
        this.maxHp = 50;
        this.defense = 0;

        numInBattleAttacks = 2;
        numInBattleDeffence = 1;

        shotDeck = new Deck<>(numInBattleAttacks);
        armorDeck = new Deck<>(numInBattleDeffence);
    }

    public int getEnergy() {
        return energy;
    }

    // TODO: instanceof is a code smell. There's a problem with design.
    public void addCard(Card card) {
        if (card instanceof Shot) {
            shotDeck.add((Shot) card);
        } else if (card instanceof Armor) {
            armorDeck.add((Armor) card);
        } else {
            throw new UnsupportedOperationException(
                    "Card type not supported" + card.getClass().getName());
        }
    }

    /** Modifies player's deck for each type of Card */
    public void initTurn() {
        shotDeck.initInBattle();
        armorDeck.initInBattle();
        energy = 3;
        defense = 0;
    }

    public void useCard(Card card, Entity target)
            throws NotEnoughEnergyException, CardNotInHandException {
        if (!shotDeck.getInBattle().contains(card) && !armorDeck.getInBattle().contains(card)) {
            throw new CardNotInHandException();
        }
        for (Intent intent : card.getIntents()) {
            intent.execute(target);
        }
        if (card.getCost() > this.energy) {
            throw new NotEnoughEnergyException();
        }
        this.energy -= card.getCost();
    }

    public Deck<Shot> getShotDeck() {
        return shotDeck;
    }

    public Deck<Armor> getArmorDeck() {
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
