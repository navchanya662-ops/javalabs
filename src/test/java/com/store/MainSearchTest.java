package com.store;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainSearchTest {
    @Test
    void shouldFindClothesByNamePartIgnoringCase() {
        ArrayList<Clothes> clothes = createSampleClothes();

        ArrayList<Clothes> results = Main.findByName(clothes, "кур");

        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof Jackets);
    }

    @Test
    void shouldFindClothesBySize() {
        ArrayList<Clothes> clothes = createSampleClothes();

        ArrayList<Clothes> results = Main.findBySize(clothes, ClothesSize.M);

        assertEquals(2, results.size());
        assertTrue(results.get(0) instanceof Shirts);
        assertTrue(results.get(1) instanceof Dresses);
    }

    @Test
    void shouldFindClothesByColorPartIgnoringCase() {
        ArrayList<Clothes> clothes = createSampleClothes();

        ArrayList<Clothes> results = Main.findByColor(clothes, "чор");

        assertEquals(2, results.size());
        assertEquals("Шапка", results.get(0).getName());
        assertEquals("Куртка", results.get(1).getName());
    }

    @Test
    void shouldFindClothesByObjectType() {
        ArrayList<Clothes> clothes = createSampleClothes();

        ArrayList<Clothes> results = Main.findByType(clothes, 2);

        assertEquals(1, results.size());
        assertTrue(results.get(0) instanceof Pants);
    }

    @Test
    void shouldFindOnlyBaseClothesByBaseTypeChoice() {
        ArrayList<Clothes> clothes = createSampleClothes();

        ArrayList<Clothes> results = Main.findByType(clothes, 1);

        assertEquals(1, results.size());
        assertEquals(Clothes.class, results.get(0).getClass());
    }

    @Test
    void shouldReturnEmptyResultWhenNothingMatches() {
        ArrayList<Clothes> clothes = createSampleClothes();

        ArrayList<Clothes> results = Main.findByName(clothes, "неіснуюча назва");

        assertTrue(results.isEmpty());
    }

    @Test
    void shouldNotChangeCollectionDuringSearch() {
        ArrayList<Clothes> clothes = createSampleClothes();
        Clothes firstItem = clothes.get(0);
        int originalSize = clothes.size();

        Main.findByColor(clothes, "чор");

        assertEquals(originalSize, clothes.size());
        assertSame(firstItem, clothes.get(0));
    }

    private ArrayList<Clothes> createSampleClothes() {
        ArrayList<Clothes> clothes = new ArrayList<>();
        clothes.add(new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        clothes.add(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true));
        clothes.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"));
        clothes.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        clothes.add(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true));
        return clothes;
    }
}
