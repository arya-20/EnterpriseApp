package example.common;

import example.common.domain.Entity;
import example.common.domain.Identity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EntityTests {

    private Entity entity1;
    private Entity entity2;
    private Identity identity1;
    private Identity identity2;

    @BeforeEach
    void setUp() {
        identity1 = new Identity("1");
        identity2 = new Identity("2");

        entity1 = new Entity(identity1) {};
        entity2 = new Entity(identity1) {};
        entity2 = new Entity(identity2) {};

    }

    @Test
    @DisplayName("Should return correct ID")
    void testId() {
        assertEquals(identity1, entity1.id());
    }

    @Test
    @DisplayName("Should be equal if IDs are the same")
    void test01() {
        assertTrue(entity1.equals(entity2));
    }

    @Test
    @DisplayName("Should not be equal if IDs are different")
    void test02() {
        assertFalse(entity1.equals(entity2));
    }


    @Test
    @DisplayName("Should initialize with empty event")
    void test03() {
        assertTrue(entity1.getEvent().isEmpty());
    }

    @Test
    @DisplayName("Should return Optional.empty() when no event is set")
    void test04() {
        assertEquals(Optional.empty(), entity1.getEvent());
    }
}
