package com.store;

/**
 * Виняток для некоректних значень полів предметної області.
 */
public class InvalidFieldValueException extends IllegalArgumentException {
    /**
     * Створює виняток із повідомленням про помилку.
     *
     * @param message повідомлення про помилку
     */
    public InvalidFieldValueException(String message) {
        super(message);
    }
}
