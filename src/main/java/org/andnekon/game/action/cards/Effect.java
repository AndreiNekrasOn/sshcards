package org.andnekon.game.action.cards;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;

public class Effect extends Card {

    public Effect(String name, int cost, Intent... intent) {
        super(name, cost, intent);
    }
}
