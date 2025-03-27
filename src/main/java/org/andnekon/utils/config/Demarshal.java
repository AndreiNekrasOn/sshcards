package org.andnekon.utils.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Demarshal {

    /**
     * Demashes reads file from resources and parses it into the map.
     *
     * @return Map of parsed values
     */
    public static Map<String, String> demarshal(String resourceName) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(classloader.getResourceAsStream(resourceName)))) {
            Map<String, String> result = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    continue;
                }
                try {
                    result.put(parts[0], parts[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing value for key: " + parts[0]);
                }
            }
            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println(resourceName);
            throw e;
        }
    }

    private static int parseKey(Map<String, String> config, String key) {
        return Integer.parseInt(config.get(key));
    }

    public static EnemyBase configEnemy(String resourceName)
            throws IOException, NoSuchFieldException {
        Map<String, String> config = demarshal(resourceName);
        try {
            int dmg = parseKey(config, "dmg");
            int hp = parseKey(config, "hp");
            int maxHp = parseKey(config, "maxHp");
            int armor = parseKey(config, "armor");

            Map<String, Integer> misc = new HashMap<>();
            for (var m : config.entrySet()) {
                if (m.getKey().startsWith("misc.")) {
                    misc.put(m.getKey(), parseKey(config, m.getKey()));
                }
            }
            return new EnemyBase(
                    config.get("name"), config.get("resource"), hp, maxHp, dmg, armor, misc);
        } catch (NullPointerException e) {
            throw new NoSuchFieldException();
        }
    }
}
