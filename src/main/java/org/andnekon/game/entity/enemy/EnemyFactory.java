package org.andnekon.game.entity.enemy;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class EnemyFactory {

    private static Class<?>[] enemyTypes = {
        RegularShip.class, MirroredShip.class, Pirates.class, TheHorror.class
    };

    public static Enemy getRandomEnemy() {
        try {
            int random = new Random().nextInt(enemyTypes.length);
            return (Enemy) enemyTypes[random].getConstructor().newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
            return new Pirates(); // default? or crash?
        }
    }
}
