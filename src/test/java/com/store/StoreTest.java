package com.store;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreTest {
    @Test
    void shouldAddNewClothesWithQuantity() {
        Store store = new Store();

        store.addNewClothes(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 3);

        assertEquals(1, store.getTotalUniqueItems());
        assertEquals(3, store.getQuantity(0));
    }

    @Test
    void shouldIncreaseQuantityWhenSameClothesAlreadyExists() {
        Store store = new Store();
        Clothes hat = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);

        store.addNewClothes(hat, 2);
        store.addNewClothes(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 5);

        assertEquals(1, store.getTotalUniqueItems());
        assertEquals(7, store.getQuantity(0));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNotPositive() {
        Store store = new Store();
        Clothes hat = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);

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

    @Test
    void shouldFindClothesByUuid() {
        Store store = new Store();
        Clothes hat = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);
        store.addNewClothes(hat, 4);

        Clothes result = store.findByUuid(hat.getUuid());

        assertEquals(hat, result);
    }

    @Test
    void shouldReturnNullWhenUuidDoesNotExist() {
        Store store = createSampleStore();

        Clothes result = store.findByUuid(UUID.randomUUID());

        assertNull(result);
    }

    @Test
    void shouldUpdateExistingClothesAndKeepQuantity() {
        Store store = new Store();
        Clothes oldHat = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);
        Clothes newHat = new BasicClothes("Тепла шапка", ClothesSize.M, "Сірий", "Вовна", 499.0);
        store.addNewClothes(oldHat, 4);

        boolean updated = store.update(oldHat, newHat);

        assertTrue(updated);
        assertEquals("Тепла шапка", store.getClothes().get(0).getName());
        assertEquals(4, store.getQuantity(0));
    }

    @Test
    void shouldReturnFalseWhenUpdatingMissingClothes() {
        Store store = createSampleStore();
        Clothes missing = new BasicClothes("Немає", ClothesSize.S, "Білий", "Бавовна", 100.0);
        Clothes replacement = new BasicClothes("Заміна", ClothesSize.M, "Чорний", "Льон", 200.0);

        boolean updated = store.update(missing, replacement);

        assertEquals(5, store.getTotalUniqueItems());
        assertFalse(updated);
    }

    @Test
    void shouldDeleteExistingClothesAndQuantity() {
        Store store = createSampleStore();
        Clothes item = store.getClothes().get(1);

        boolean deleted = store.delete(item);

        assertTrue(deleted);
        assertEquals(4, store.getTotalUniqueItems());
        assertEquals("Сорочка", store.getClothes().get(1).getName());
        assertEquals(5, store.getQuantity(1));
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingClothes() {
        Store store = createSampleStore();
        Clothes missing = new BasicClothes("Немає", ClothesSize.S, "Білий", "Бавовна", 100.0);

        boolean deleted = store.delete(missing);

        assertFalse(deleted);
        assertEquals(5, store.getTotalUniqueItems());
    }

    private Store createSampleStore() {
        Store store = new Store();
        store.addNewClothes(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 4);
        store.addNewClothes(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true), 2);
        store.addNewClothes(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"), 5);
        store.addNewClothes(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"), 3);
        store.addNewClothes(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true), 1);
        return store;
    }
}
