package com.store;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreTest {
    @Test
    void shouldAddNewClothesWithQuantity() {
        Store store = new Store();

        store.addNewClothes(new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 3);

        assertEquals(1, store.getTotalUniqueItems());
        assertEquals(3, store.getQuantity(0));
    }

    @Test
    void shouldIncreaseQuantityWhenSameClothesAlreadyExists() {
        Store store = new Store();
        Clothes hat = new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);

        store.addNewClothes(hat, 2);
        store.addNewClothes(new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 5);

        assertEquals(1, store.getTotalUniqueItems());
        assertEquals(7, store.getQuantity(0));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNotPositive() {
        Store store = new Store();
        Clothes hat = new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);

        assertThrows(IllegalArgumentException.class, () -> store.addNewClothes(hat, 0));
        assertThrows(IllegalArgumentException.class, () -> store.addNewClothes(hat, -1));
    }

    @Test
    void shouldFindClothesByName() {
        Store store = createSampleStore();

        ArrayList<Clothes> results = store.findByName("кур");

        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof Jackets);
    }

    @Test
    void shouldFindClothesBySize() {
        Store store = createSampleStore();

        ArrayList<Clothes> results = store.findBySize(ClothesSize.M);

        assertEquals(2, results.size());
        assertTrue(results.get(0) instanceof Shirts);
        assertTrue(results.get(1) instanceof Dresses);
    }

    @Test
    void shouldFindClothesByColor() {
        Store store = createSampleStore();

        ArrayList<Clothes> results = store.findByColor("чор");

        assertEquals(2, results.size());
        assertEquals("Шапка", results.get(0).getName());
        assertEquals("Куртка", results.get(1).getName());
    }

    @Test
    void shouldFindClothesByType() {
        Store store = createSampleStore();

        ArrayList<Clothes> results = store.findByType(5);

        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof Dresses);
    }

    private Store createSampleStore() {
        Store store = new Store();
        store.addNewClothes(new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 4);
        store.addNewClothes(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true), 2);
        store.addNewClothes(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"), 5);
        store.addNewClothes(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"), 3);
        store.addNewClothes(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true), 1);
        return store;
    }
}
