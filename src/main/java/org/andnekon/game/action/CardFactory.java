package org.andnekon.game.action;

import org.andnekon.game.entity.Player;
import org.andnekon.game.manage.BattleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** CardFactory is a per-player singleton */
public class CardFactory {

    private static volatile Map<Player, CardFactory> instances = new HashMap<>();

    private BattleManager manager;
    private Map<String, Card> nameToCard = new HashMap<>();

    private CardFactory(BattleManager manager) {
        this.manager = manager;
    }

    public static final List<String> CARDS =
            List.of(
                    "Armor Up",
                    "Better Armor",
                    "Crack",
                    "Corrosion",
                    "Draw Shot",
                    "Draw Skill",
                    "Junk",
                    "Lucky Shot",
                    "Overdrive",
                    "Shot",
                    "Thorns Armor",
                    "Triple Shot");

    public static synchronized CardFactory instance(BattleManager manager) {
        Player player = manager.getPlayer();
        if (instances.containsKey(player)) {
            return instances.get(player);
        }
        CardFactory result = new CardFactory(manager);
        instances.put(player, result);

        List<String> all = new ArrayList<>();
        all.addAll(CARDS);
        for (String name : all) {
            Card card = CardReaderService.readCard(name, manager);
            result.nameToCard.put(name, card);
        }
        return result;
    }

    public Card getCard(String name) {
        return nameToCard.get(name);
    }

    public Card getRandomCard() {
        int limit = CARDS.size();
        int random = (int) (Math.random() * limit);
        return getCard(CARDS.get(random));
    }
}
