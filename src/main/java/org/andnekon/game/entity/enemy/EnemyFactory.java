package org.andnekon.game.entity.enemy;

import org.andnekon.game.entity.EnemyType;

import java.util.Random;

public class EnemyFactory {

    public static Enemy getEnemy(EnemyType enemyType) {
        switch (enemyType) {
            case REGULAR_SHIP:
                return new RegularShip();
            case MIRRORED_SHIP:
                return new MirroredShip();
            case PIRATES:
                return new Pirates();
            case THE_HORROR:
                return new TheHorror();
            default:
                throw new UnsupportedOperationException("enemy type not supported: " + enemyType);
        }
    }

    public static Enemy getRandomEnemy() {
        while (true) { // TODO: remove when all types are supported
            int random = new Random().nextInt(EnemyType.values().length);
            try {
                return getEnemy(EnemyType.values()[random]);
            } catch (Exception ignored) {
                // pass, because it's intentional logic
            }
        }
    }
}
