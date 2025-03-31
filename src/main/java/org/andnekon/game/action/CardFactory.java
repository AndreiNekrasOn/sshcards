package org.andnekon.game.action;

import org.andnekon.game.action.cards.Armor;
import org.andnekon.game.action.cards.Shot;
import org.andnekon.game.action.cards.Status;
import org.andnekon.game.action.intents.Attack;
import org.andnekon.game.action.intents.Defence;
import org.andnekon.game.action.intents.DrawAttack;
import org.andnekon.game.action.intents.DrawSkill;
import org.andnekon.game.action.intents.Effect;
import org.andnekon.game.entity.Entity;
import org.andnekon.game.entity.Player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            return (int)(value ^ (value >>> 32));
        }

    }

    private static volatile Set<Player> init = new HashSet<>();

    private static volatile Map<PlayerWithCardName, Card> nameToCard = new HashMap<>();

    private CardFactory() {}

    private synchronized static void initialize(Player player) {
        if (init.contains(player)) {
            return;
        }
        init.add(player);
        // read all cards from SHOTS, ARMORS and STATUSES into map
        List<String> all = new ArrayList<>();
        all.addAll(SHOTS);
        all.addAll(ARMORS);
        all.addAll(STATUSES);
        for (String name : all) {
            try {
                Card card = readCard(name, player);
                nameToCard.put(new PlayerWithCardName(player, name), card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // READ json file and parse card values
    private static Card readCard(String name, Player player) throws IOException {// READ json file and parse card values
        String filename = String.format("cards/%s.json", name);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream(filename)) {
            JsonObject j = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            CardBuilder cb = new CardBuilder(j.get("type").getAsString(),
                    j.get("name").getAsString(), j.get("cost").getAsInt());
            JsonArray actions = j.get("actions").getAsJsonArray();
            if (actions.isEmpty()) {
                return cb.build(); // early stop
            }
            for (int i = 0; i < actions.size(); i++) {
                String type = actions.get(i).getAsJsonObject().get("type").getAsString();
                String target = actions.get(i).getAsJsonObject().get("target").getAsString();
                int value = actions.get(i).getAsJsonObject().get("value").getAsInt();
                JsonElement payload = actions.get(i).getAsJsonObject().get("payload");
                if (payload != null) {
                    cb.addIntent(type, target, value, payload.getAsString(), player);
                } else {
                    cb.addIntent(type, target, value, null, player);
                }
            }
            return cb.build();
        }
    }


    public static final List<String> SHOTS = List.of("Shot", "Lucky Shot", "Triple Shot");

    public static final List<String> ARMORS =
            List.of(
                    "Armor Up",
                    "Better Armor",
                    "Thorns Armor",
                    "Overdrive",
                    "Draw Shot",
                    "Draw Skill");

    public static final List<String> STATUSES = List.of("Crack", "Corrosion");

    public static Card getCard(Player player, String name) {
        initialize(player);
        return nameToCard.get(new PlayerWithCardName(player, name));
    }

    public static Card getRandomCard(Player player) {
        int shotLimit = SHOTS.size();
        int armorLimit = shotLimit + ARMORS.size();
        int statusLimit = armorLimit + STATUSES.size();
        int random = (int) (Math.random() * statusLimit);
        if (random < shotLimit) {
            return getCard(player, SHOTS.get(random));
        } else if (random < armorLimit) {
            return getCard(player, ARMORS.get(random - shotLimit));
        } else {
            return getCard(player, STATUSES.get(random - armorLimit));
        }
    }
}
