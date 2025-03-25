package org.andnekon.game.entity.enemy;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class EnemyFactory {

    public static final Class<?>[] enemyTypes = {
        RegularShip.class, MirroredShip.class, Pirates.class, TheHorror.class
    };

    public static Enemy getRandomEnemy() {
        int random = new Random().nextInt(enemyTypes.length);
        return getEnemy(random);
    }

    public static final Enemy getEnemy(int id) {
        try {
            return (Enemy) enemyTypes[id].getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return new Pirates(); // default? or crash?
        }
    }
}
