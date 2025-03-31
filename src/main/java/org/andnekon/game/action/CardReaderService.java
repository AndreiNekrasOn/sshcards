package org.andnekon.game.action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.andnekon.game.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** CardReaderService */
public class CardReaderService {

    // READ json file and parse card values
    public static Card readCard(
            String name, Player player) { // READ json file and parse card values
        String filename = String.format("cards/%s.json", name);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classloader.getResourceAsStream(filename)) {
            JsonObject j = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            CardBuilder cb =
                    new CardBuilder(
                            j.get("type").getAsString(),
                            j.get("name").getAsString(),
                            j.get("cost").getAsInt());
            cb.description(j.get("description").getAsString());
            cb.art(j.get("art").getAsString());
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
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
