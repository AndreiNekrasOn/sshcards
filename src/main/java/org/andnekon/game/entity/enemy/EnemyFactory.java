package org.andnekon.game.entity.enemy;

import org.andnekon.utils.config.Demarshal;
import org.andnekon.utils.config.EnemyBase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EnemyFactory {

    // initialize with nulls in place
    static Map<String, EnemyBase> nameToStats =
            new HashMap<>() {
                {
                    put("RegularShip", null);
                    put("MirroredShip", null);
                    put("Pirates", null);
                    put("TheHorror", null);
                }
            };
    static Map<String, Class<?>> nameToClass =
            new HashMap<>() {
                {
                    put("RegularShip", RegularShip.class);
                    put("MirroredShip", MirroredShip.class);
                    put("Pirates", Pirates.class);
                    put("TheHorror", TheHorror.class);
                }
            };

    public static Enemy getEnemy(String name) {
        try {
            nameToStats.putIfAbsent(
                    name, Demarshal.configEnemy(String.format("enemy/%s.config", name)));
        } catch (NoSuchFieldException | IOException e) {
            e.printStackTrace();
            return null; // fuck off
        }
        EnemyBase base = nameToStats.get(name);
        try {
            Enemy enemy = (Enemy) nameToClass.get(name).getConstructor().newInstance();
            return enemy.withStats(base);
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Enemy not found: " + name);
    }

    public static Enemy getRandomEnemy() {
        int random = new Random().nextInt(nameToClass.size());
        String name = nameToClass.keySet().stream().skip(random).findFirst().orElseThrow();
        return getEnemy(name);
    }
}
