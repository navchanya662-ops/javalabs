package com.store;

/**
 * Описує звичайний елемент одягу як конкретну реалізацію базового класу Clothes.
 */
public class BasicClothes extends Clothes {
    /**
     * Створює новий звичайний елемент одягу.
     *
     * @param name назва одягу
     * @param size розмір одягу
     * @param color колір одягу
     * @param material матеріал одягу
     * @param price ціна одягу
     */
    public BasicClothes(String name, ClothesSize size, String color, String material, double price) {
        super(name, size, color, material, price);
    }

    /**
     * Створює копію звичайного елемента одягу.
     *
     * @param other об'єкт для копіювання
     */
    public BasicClothes(Clothes other) {
        super(other);
    }
}
