package com.store;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void shouldCreatePantsAsClothes() {
        Clothes pants = new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true);

        assertTrue(pants instanceof Pants);
        assertTrue(pants.toString().contains("Штани"));
    }

    @Test
    void shouldCreateShirtsAsClothes() {
        Clothes shirt = new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий");

        assertTrue(shirt instanceof Shirts);
        assertTrue(shirt.toString().contains("Сорочка"));
    }

    @Test
    void shouldStoreDifferentDerivedTypesInOneClothesList() {
        ArrayList<Clothes> clothes = new ArrayList<>();

        clothes.add(new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        clothes.add(new Pants("Штани", ClothesSize.L, "Сірий", "Бавовна", 1099.0, false));
        clothes.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Льон", 999.0, "короткий"));

        assertEquals(3, clothes.size());
        assertTrue(clothes.get(1) instanceof Pants);
        assertTrue(clothes.get(2) instanceof Shirts);
    }
}
