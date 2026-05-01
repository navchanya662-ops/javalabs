package com.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClothesFileManagerTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldLoadAllSupportedTypesFromFile() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, String.join(System.lineSeparator(),
                "CLOTHES;Шапка;S;Чорний;Вовна;399.0",
                "PANTS;Джинси;L;Синій;Денім;1299.0;true",
                "SHIRTS;Сорочка;M;Білий;Бавовна;899.0;довгий",
                "JACKETS;Куртка;XL;Чорний;Поліестер;2499.0;true;синтепон",
                "DRESSES;Сукня;M;Червоний;Шовк;1899.0;міді;true"
        ));

        ClothesFileManager fileManager = new ClothesFileManager();
        ArrayList<Clothes> clothes = fileManager.loadFromFile(file.toString());

        assertEquals(5, clothes.size());
        assertTrue(clothes.get(0) instanceof Clothes);
        assertTrue(clothes.get(1) instanceof Pants);
        assertTrue(clothes.get(2) instanceof Shirts);
        assertTrue(clothes.get(3) instanceof Jackets);
        assertTrue(clothes.get(4) instanceof Dresses);
    }

    @Test
    void shouldSkipInvalidLinesWhenLoadingFromFile() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, String.join(System.lineSeparator(),
                "BROKEN;bad;line",
                "",
                "CLOTHES;Шапка;S;Чорний;Вовна;399.0"
        ));

        ClothesFileManager fileManager = new ClothesFileManager();
        ArrayList<Clothes> clothes = fileManager.loadFromFile(file.toString());

        assertEquals(1, clothes.size());
        assertEquals("Шапка", clothes.get(0).getName());
    }

    @Test
    void shouldSaveAndLoadAllSupportedTypes() {
        Path file = tempDir.resolve("input.txt");
        ArrayList<Clothes> original = new ArrayList<>();
        original.add(new Clothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        original.add(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true));
        original.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"));
        original.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        original.add(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true));

        ClothesFileManager fileManager = new ClothesFileManager();
        fileManager.saveToFile(original, file.toString());
        ArrayList<Clothes> loaded = fileManager.loadFromFile(file.toString());

        assertEquals(5, loaded.size());
        assertTrue(loaded.get(0) instanceof Clothes);
        assertTrue(loaded.get(1) instanceof Pants);
        assertTrue(loaded.get(2) instanceof Shirts);
        assertTrue(loaded.get(3) instanceof Jackets);
        assertTrue(loaded.get(4) instanceof Dresses);
        assertEquals(original.get(0).getName(), loaded.get(0).getName());
    }
}
