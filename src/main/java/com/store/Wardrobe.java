package com.store;

import java.util.ArrayList;

/**
 * Описує шафу, яка агрегує елементи одягу.
 */
public class Wardrobe {
    private final ArrayList<Clothes> clothes;

    /**
     * Створює порожню шафу.
     */
    public Wardrobe() {
        clothes = new ArrayList<>();
    }

    /**
     * Додає елемент одягу до шафи.
     *
     * @param item елемент одягу
     * @throws IllegalArgumentException якщо елемент одягу null
     */
    public void addClothes(Clothes item) {
        if (item == null) {
            throw new IllegalArgumentException("Елемент одягу не може бути null");
        }
        clothes.add(item);
    }

    /**
     * Повертає всі елементи одягу в шафі.
     *
     * @return копія списку елементів одягу
     */
    public ArrayList<Clothes> getClothes() {
        return new ArrayList<>(clothes);
    }

    /**
     * Повертає елемент одягу за індексом.
     *
     * @param index індекс елемента
     * @return елемент одягу
     */
    public Clothes getClothes(int index) {
        return clothes.get(index);
    }

    /**
     * Повертає кількість елементів одягу в шафі.
     *
     * @return кількість елементів одягу
     */
    public int getClothesCount() {
        return clothes.size();
    }

    /**
     * Перевіряє, чи шафа порожня.
     *
     * @return true, якщо в шафі немає елементів одягу
     */
    public boolean isEmpty() {
        return clothes.isEmpty();
    }
}
