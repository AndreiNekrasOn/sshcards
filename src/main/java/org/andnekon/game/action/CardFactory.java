package org.andnekon.game.action;

import org.andnekon.game.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CardFactory {

    // TODO: should we make CardFactory non-static and instantiated for player instead?
    static class PlayerWithCardName {
        Player p;
        String name;

        public PlayerWithCardName(Player p, String name) {
            this.p = p;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof PlayerWithCardName)) {
                return false;
            }
            PlayerWithCardName other = (PlayerWithCardName) obj;
            return other.name.equals(name) && other.p.equals(p);
        }

        @Override
        public int hashCode() {
            long value = name.hashCode() + p.hashCode();
            return (int) (value ^ (value >>> 32));
        }
    }

    private static volatile Set<Player> init = new HashSet<>();

    private static volatile Map<PlayerWithCardName, Card> nameToCard = new HashMap<>();

    private CardFactory() {}

    private static synchronized void initialize(Player player) {
        if (init.contains(player)) {
            return;
        }
        init.add(player);
        List<String> all = new ArrayList<>();
        all.addAll(CARDS);
        for (String name : all) {
            Card card = CardReaderService.readCard(name, player);
            // do we panic if card is null?
            nameToCard.put(new PlayerWithCardName(player, name), card);
        }
    }

    public static final List<String> CARDS =
            List.of(
                    "Shot",
                    "Lucky Shot",
                    "Triple Shot",
                    "Armor Up",
                    "Better Armor",
                    "Thorns Armor",
                    "Overdrive",
                    "Draw Shot",
                    "Draw Skill",
                    "Crack",
                    "Corrosion");

    public static Card getCard(Player player, String name) {
        initialize(player);
        return nameToCard.get(new PlayerWithCardName(player, name));
    }

    public static Card getRandomCard(Player player) {
        int limit = CARDS.size();
        int random = (int) (Math.random() * limit);
        return getCard(player, CARDS.get(random));
    }
}
