package com.store;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClothesTest {
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new Clothes("Футболка", ClothesSize.M, "Білий", "Бавовна", 499.99);

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () ->
                new Clothes("", ClothesSize.M, "Білий", "Бавовна", 499.99)
        );
    }

    @Test
    void shouldCreateEqualCopyWhenCopyConstructorUsed() {
        Clothes original = new Clothes("Футболка", ClothesSize.M, "Білий", "Бавовна", 499.99);
        Clothes copy = new Clothes(original);

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void shouldThrowExceptionWhenCopiedObjectIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Clothes(null));
    }
}
