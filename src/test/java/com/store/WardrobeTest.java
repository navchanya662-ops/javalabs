package com.store;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WardrobeTest {
    @Test
    void shouldAddClothesToWardrobe() {
        Wardrobe wardrobe = new Wardrobe();
        Clothes clothes = new Clothes("Футболка", ClothesSize.M, "Білий", "Бавовна", 499.99);

        wardrobe.addClothes(clothes);

        assertFalse(wardrobe.isEmpty());
        assertEquals(1, wardrobe.getClothesCount());
        assertEquals(clothes, wardrobe.getClothes(0));
    }

    @Test
    void shouldBeEmptyWhenCreated() {
        Wardrobe wardrobe = new Wardrobe();

        assertTrue(wardrobe.isEmpty());
        assertEquals(0, wardrobe.getClothesCount());
    }

    @Test
    void shouldThrowExceptionWhenNullClothesAdded() {
        Wardrobe wardrobe = new Wardrobe();

        assertThrows(IllegalArgumentException.class, () -> wardrobe.addClothes(null));
    }
}
