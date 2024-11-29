package org.andnekon.game;

public class CardFactory {


    public static Card getCard(Player player, String name) {
        Card card;
        switch (name) {
            case "Shoot":
                card = new Card(name, 1, new Intent(player, Intent.IntentType.ATTACK, 1));
                break;
            case "Defend":
                card = new Card(name, 1, new Intent(player, Intent.IntentType.DEFEND, 1, player));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + name);
        }
        return card;
    }
}
