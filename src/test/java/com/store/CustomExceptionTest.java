package com.store;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomExceptionTest {
    @Test
    void shouldThrowInvalidFieldValueExceptionWhenNameIsBlank() {
        assertThrows(InvalidFieldValueException.class, () ->
                new BasicClothes("", ClothesSize.M, "Білий", "Бавовна", 499.99)
        );
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenSleeveTypeIsBlank() {
        assertThrows(InvalidFieldValueException.class, () ->
                new Shirts("Сорочка", ClothesSize.M, "Білий", "Бавовна", 899.0, "")
        );
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenInsulationTypeIsBlank() {
        assertThrows(InvalidFieldValueException.class, () ->
                new Jackets("Куртка", ClothesSize.XL, "Чорний", "Поліестер", 2499.0, true, "")
        );
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingMissingClothes() {
        Store store = new Store();
        Clothes missingClothes = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);

        assertThrows(ObjectNotFoundException.class, () -> store.deleteOrThrow(missingClothes));
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenUpdatingMissingClothes() {
        Store store = new Store();
        Clothes missingClothes = new BasicClothes("Шапка", ClothesSize.S, "Чорний", "Вовна", 399.0);
        Clothes newClothes = new BasicClothes("Нова шапка", ClothesSize.M, "Сірий", "Вовна", 499.0);

        assertThrows(ObjectNotFoundException.class, () -> store.updateOrThrow(missingClothes, newClothes));
    }
}
