package org.andnekon.game.entity.enemy;

import org.andnekon.game.entity.Combat;

/** CombatFactory */
public class CombatFactory {

    String[] combats = new String[] {"Horror", "DEATH", "Two ships", "Ambush!", "Horrors"};

    public static Combat getCombat(String combat) {
        switch (combat) {
            case "Horrors":
                return new Combat(
                        combat,
                        EnemyFactory.getEnemy("TheHorror"),
                        EnemyFactory.getEnemy("TheHorror"),
                        EnemyFactory.getEnemy("TheHorror"));
            case "Horror":
                return new Combat(combat, EnemyFactory.getEnemy("TheHorror"));
            case "DEATH":
                return new Combat(
                        combat,
                        EnemyFactory.getEnemy("RegularShip"),
                        EnemyFactory.getEnemy("RegularShip"));
            case "Two ships":
                return new Combat(
                        combat,
                        EnemyFactory.getEnemy("RegularShip"),
                        EnemyFactory.getEnemy("MirroredShip"));
            case "Ambush!":
                return new Combat(
                        combat, EnemyFactory.getEnemy("Pirates"), EnemyFactory.getEnemy("Pirates"));
            default:
                throw new IllegalStateException("Combat not found");
        }
    }
}
