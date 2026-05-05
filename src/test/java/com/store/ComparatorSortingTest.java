package com.store;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparatorSortingTest {
    @Test
    void shouldCreateAnonymousComparatorsForSortingCriteria() {
        Comparator<Clothes> nameComparator = Main.createNameComparator();
        Comparator<Clothes> priceComparator = Main.createPriceComparator();
        Comparator<Clothes> sizeComparator = Main.createSizeComparator();

        assertTrue(nameComparator.getClass().isAnonymousClass());
        assertTrue(priceComparator.getClass().isAnonymousClass());
        assertTrue(sizeComparator.getClass().isAnonymousClass());
    }

    @Test
    void shouldSortClothesByNameUsingComparator() {
        ArrayList<Clothes> clothes = createUnsortedClothes();

        Collections.sort(clothes, Main.createNameComparator());

        assertEquals("Куртка", clothes.get(0).getName());
        assertEquals("Сорочка", clothes.get(1).getName());
        assertEquals("Шапка", clothes.get(2).getName());
    }

    @Test
    void shouldSortClothesByPriceUsingComparator() {
        ArrayList<Clothes> clothes = createUnsortedClothes();

        Collections.sort(clothes, Main.createPriceComparator());

        assertEquals("Шапка", clothes.get(0).getName());
        assertEquals("Сорочка", clothes.get(1).getName());
        assertEquals("Куртка", clothes.get(2).getName());
    }

    @Test
    void shouldSortClothesBySizeUsingComparator() {
        ArrayList<Clothes> clothes = createUnsortedClothes();

        Collections.sort(clothes, Main.createSizeComparator());

        assertEquals(ClothesSize.S, clothes.get(0).getSize());
        assertEquals(ClothesSize.M, clothes.get(1).getSize());
        assertEquals(ClothesSize.XL, clothes.get(2).getSize());
    }

    @Test
    void shouldSortSingleElementByComparatorWithoutChanges() {
        ArrayList<Clothes> clothes = new ArrayList<>();
        clothes.add(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));

        Collections.sort(clothes, Main.createPriceComparator());

        assertEquals(1, clothes.size());
        assertEquals("Шапка", clothes.get(0).getName());
    }

    @Test
    void shouldSortEmptyListByComparatorWithoutErrors() {
        ArrayList<Clothes> clothes = new ArrayList<>();

        Collections.sort(clothes, Main.createSizeComparator());

        assertTrue(clothes.isEmpty());
    }

    private ArrayList<Clothes> createUnsortedClothes() {
        ArrayList<Clothes> clothes = new ArrayList<>();
        clothes.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        clothes.add(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        clothes.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"));
        return clothes;
    }
}
