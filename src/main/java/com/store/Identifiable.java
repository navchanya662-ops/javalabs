package com.store;

import java.util.UUID;

/**
 * Задає контракт для об'єктів, які мають унікальний ідентифікатор.
 */
public interface Identifiable {
    /**
     * Повертає унікальний ідентифікатор об'єкта.
     *
     * @return UUID об'єкта
     */
    UUID getUuid();
}
