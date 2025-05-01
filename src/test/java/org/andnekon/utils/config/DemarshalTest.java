package org.andnekon.utils.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.io.IOException;

/** DemarshalTest */
public class DemarshalTest {

    @Test
    void testConfigEnemyNormal() throws NoSuchFieldException, IOException {
        EnemyBase base = Demarshal.configEnemy("enemy/TestShip.config");
        assertEquals(base.hp(), 20);
        assertEquals(base.maxHp(), 20);
        assertEquals(base.dmg(), 1);
        assertEquals(base.armor(), 1);
        assertEquals(base.armor(), 1);
        assertEquals(base.name(), "Test Ship");
    }

    void testConfigEnemyMissing() throws IOException {
        assertThrows(
                NoSuchFieldException.class,
                () -> Demarshal.configEnemy("enemy/MissingFields.java"));
    }
}
