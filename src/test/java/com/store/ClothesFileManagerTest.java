package com.store;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClothesFileManagerTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldLoadAllSupportedTypesFromJsonFile() throws IOException {
        Path file = tempDir.resolve("input.json");
        Files.writeString(file, """
                [
                  {"type":"CLOTHES","name":"Шапка","size":"S","color":"Чорний","material":"Вовна","price":399.0},
                  {"type":"PANTS","name":"Джинси","size":"L","color":"Синій","material":"Денім","price":1299.0,"hasPockets":true},
                  {"type":"SHIRTS","name":"Сорочка","size":"M","color":"Білий","material":"Бавовна","price":899.0,"sleeveType":"довгий"},
                  {"type":"JACKETS","name":"Куртка","size":"XL","color":"Чорний","material":"Поліестер","price":2499.0,"hasHood":true,"insulationType":"синтепон"},
                  {"type":"DRESSES","name":"Сукня","size":"M","color":"Червоний","material":"Шовк","price":1899.0,"lengthType":"міді","formal":true}
                ]
                """);

        ClothesFileManager fileManager = new ClothesFileManager();
        ArrayList<Clothes> clothes = fileManager.loadFromFile(file.toString());

        assertEquals(5, clothes.size());
        assertTrue(clothes.get(0) instanceof BasicClothes);
        assertTrue(clothes.get(1) instanceof Pants);
        assertTrue(clothes.get(2) instanceof Shirts);
        assertTrue(clothes.get(3) instanceof Jackets);
        assertTrue(clothes.get(4) instanceof Dresses);
    }

    @Test
    void shouldSkipInvalidRecordsWhenLoadingFromJsonFile() throws IOException {
        Path file = tempDir.resolve("input.json");
        Files.writeString(file, """
                [
                  {"type":"BROKEN","name":"bad","size":"M","color":"bad","material":"bad","price":1.0},
                  {"type":"CLOTHES","name":"Шапка","size":"S","color":"Чорний","material":"Вовна","price":399.0}
                ]
                """);

        ClothesFileManager fileManager = new ClothesFileManager();
        ArrayList<Clothes> clothes = fileManager.loadFromFile(file.toString());

        assertEquals(1, clothes.size());
        assertEquals("Шапка", clothes.get(0).getName());
    }

    @Test
    void shouldSaveAndLoadAllSupportedTypesAsJson() {
        Path file = tempDir.resolve("input.json");
        ArrayList<Clothes> original = new ArrayList<>();
        original.add(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        original.add(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true));
        original.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"));
        original.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        original.add(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true));

        ClothesFileManager fileManager = new ClothesFileManager();
        fileManager.saveToFile(original, file.toString());
        ArrayList<Clothes> loaded = fileManager.loadFromFile(file.toString());

        assertEquals(5, loaded.size());
        assertTrue(loaded.get(0) instanceof BasicClothes);
        assertTrue(loaded.get(1) instanceof Pants);
        assertTrue(loaded.get(2) instanceof Shirts);
        assertTrue(loaded.get(3) instanceof Jackets);
        assertTrue(loaded.get(4) instanceof Dresses);
        assertEquals(original.get(0).getName(), loaded.get(0).getName());
    }

    @Test
    void shouldSaveAndLoadStoreWithQuantitiesAsJson() {
        Path file = tempDir.resolve("input.json");
        Store original = new Store();
        original.addNewClothes(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0), 4);
        original.addNewClothes(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true), 2);

        ClothesFileManager fileManager = new ClothesFileManager();
        fileManager.saveStoreToFile(original, file.toString());
        Store loaded = fileManager.loadStoreFromFile(file.toString());

        assertEquals(2, loaded.getTotalUniqueItems());
        assertEquals(4, loaded.getQuantity(0));
        assertEquals(2, loaded.getQuantity(1));
        assertTrue(loaded.getClothes().get(1) instanceof Pants);
    }

    @Test
    void shouldSaveAndRestoreUuidAsJson() {
        Path file = tempDir.resolve("input.json");
        ArrayList<Clothes> original = new ArrayList<>();
        Clothes hat = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);
        original.add(hat);

        ClothesFileManager fileManager = new ClothesFileManager();
        fileManager.saveToFile(original, file.toString());
        ArrayList<Clothes> loaded = fileManager.loadFromFile(file.toString());

        assertEquals(hat.getUuid(), loaded.get(0).getUuid());
    }

    @Test
    void shouldGenerateNewUuidWhenJsonUuidIsInvalid() throws IOException {
        Path file = tempDir.resolve("input.json");
        Files.writeString(file, """
                [
                  {"type":"CLOTHES","uuid":"bad-uuid","name":"Шапка","size":"S","color":"Чорний","material":"Вовна","price":399.0}
                ]
                """);

        ClothesFileManager fileManager = new ClothesFileManager();
        ArrayList<Clothes> loaded = fileManager.loadFromFile(file.toString());

        assertEquals(1, loaded.size());
        UUID.fromString(loaded.get(0).getUuid().toString());
    }
}
