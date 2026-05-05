package com.store;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClothesTest {
    @Test
    void shouldCreateBasicClothesAsConcreteClothes() {
        Clothes clothes = new BasicClothes("Футболка", ClothesSize.M, "Білий", "Бавовна", 499.99);

        assertTrue(clothes instanceof BasicClothes);
        assertTrue(clothes instanceof Clothes);
        assertTrue(clothes.toString().contains("Футболка"));
    }

    @Test
    void shouldDeclareClothesAsAbstractClass() {
        assertTrue(Modifier.isAbstract(Clothes.class.getModifiers()));
    }

    @Test
    void shouldCompareClothesByNameIgnoringCase() {
        Clothes first = new BasicClothes("брюки", ClothesSize.M, "Чорний", "Бавовна", 799.0);
        Clothes second = new BasicClothes("Куртка", ClothesSize.L, "Синій", "Поліестер", 1499.0);

        assertTrue(first.compareTo(second) < 0);
        assertEquals(0, first.compareTo(new BasicClothes("БРЮКИ", ClothesSize.S, "Сірий", "Льон", 699.0)));
    }

    @Test
    void shouldSortMultipleClothesByName() {
        ArrayList<Clothes> clothes = new ArrayList<>();
        clothes.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        clothes.add(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        clothes.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"));

        Collections.sort(clothes);

        assertEquals("Куртка", clothes.get(0).getName());
        assertEquals("Сорочка", clothes.get(1).getName());
        assertEquals("Шапка", clothes.get(2).getName());
    }

    @Test
    void shouldSortSingleClothesWithoutChanges() {
        ArrayList<Clothes> clothes = new ArrayList<>();
        clothes.add(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));

        Collections.sort(clothes);

        assertEquals(1, clothes.size());
        assertEquals("Шапка", clothes.get(0).getName());
    }

    @Test
    void shouldSortEmptyClothesListWithoutErrors() {
        ArrayList<Clothes> clothes = new ArrayList<>();

        Collections.sort(clothes);

        assertTrue(clothes.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Clothes clothes = new BasicClothes("Футболка", ClothesSize.M, "Білий", "Бавовна", 499.99);

        assertThrows(IllegalArgumentException.class, () -> clothes.setPrice(-1));
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () ->
                new BasicClothes("", ClothesSize.M, "Білий", "Бавовна", 499.99)
        );
    }

    @Test
    void shouldCreateEqualCopyWhenCopyConstructorUsed() {
        Clothes original = new BasicClothes("Футболка", ClothesSize.M, "Білий", "Бавовна", 499.99);
        Clothes copy = new BasicClothes(original);

        assertEquals(original, copy);
        assertNotSame(original, copy);
    }

    @Test
    void shouldThrowExceptionWhenCopiedObjectIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new BasicClothes(null));
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

        clothes.add(new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0));
        clothes.add(new Pants("Штани", ClothesSize.L, "Сірий", "Бавовна", 1099.0, false));
        clothes.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Льон", 999.0, "короткий"));
        clothes.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        clothes.add(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true));

        assertEquals(5, clothes.size());
        assertTrue(clothes.get(1) instanceof Pants);
        assertTrue(clothes.get(2) instanceof Shirts);
        assertTrue(clothes.get(3) instanceof Jackets);
        assertTrue(clothes.get(4) instanceof Dresses);
    }

    @Test
    void shouldUsePolymorphicToStringForDerivedTypes() {
        ArrayList<Clothes> clothes = new ArrayList<>();

        clothes.add(new Pants("Джинси", ClothesSize.L, "Синій", "Денім", 1299.0, true));
        clothes.add(new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "довгий"));
        clothes.add(new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон"));
        clothes.add(new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true));

        assertTrue(clothes.get(0).toString().startsWith("Штани"));
        assertTrue(clothes.get(1).toString().startsWith("Сорочка"));
        assertTrue(clothes.get(2).toString().startsWith("Куртка"));
        assertTrue(clothes.get(3).toString().startsWith("Сукня"));
    }

    @Test
    void shouldThrowExceptionWhenShirtSleeveTypeIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "")
        );
    }

    @Test
    void shouldCreateJacketsAsClothes() {
        Clothes jacket = new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "синтепон");

        assertTrue(jacket instanceof Jackets);
        assertTrue(jacket.toString().contains("Куртка"));
    }

    @Test
    void shouldCreateDressesAsClothes() {
        Clothes dress = new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "міді", true);

        assertTrue(dress instanceof Dresses);
        assertTrue(dress.toString().contains("Сукня"));
    }

    @Test
    void shouldThrowExceptionWhenJacketInsulationTypeIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "")
        );
    }

    @Test
    void shouldThrowExceptionWhenDressLengthTypeIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                new Dresses("Сукня", ClothesSize.M, "Червоний", "Шовк", 1899.0, "", true)
        );
    }
}
