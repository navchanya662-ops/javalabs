package com.store;

/**
 * Виняток для ситуацій, коли потрібний об'єкт не знайдено в колекції.
 */
public class ObjectNotFoundException extends RuntimeException {
    /**
     * Створює виняток із повідомленням про помилку.
     *
     * @param message повідомлення про помилку
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
