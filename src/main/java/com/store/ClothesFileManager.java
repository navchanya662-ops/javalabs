package com.store;

import java.util.ArrayList;

/**
 * Відповідає за зчитування та збереження елементів одягу у текстовому файлі.
 */
public class ClothesFileManager {
    /**
     * Завантажує елементи одягу з файлу.
     *
     * @param fileName назва файлу для зчитування
     * @return список елементів одягу
     */
    public ArrayList<Clothes> loadFromFile(String fileName) {
        return new ArrayList<>();
    }

    /**
     * Зберігає елементи одягу у файл.
     *
     * @param clothes список елементів одягу
     * @param fileName назва файлу для запису
     */
    public void saveToFile(ArrayList<Clothes> clothes, String fileName) {
    }
}
