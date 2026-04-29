package com.store;

import java.util.Objects;

/**
 * Описує один елемент одягу з назвою, розміром, кольором, матеріалом і ціною.
 */
public class Clothes {
    private static int objectCount;

    private String name;
    private ClothesSize size;
    private String color;
    private String material;
    private double price;

    /**
     * Створює новий елемент одягу.
     *
     * @param name назва елемента одягу
     * @param size розмір елемента одягу
     * @param color колір елемента одягу
     * @param material матеріал елемента одягу
     * @param price ціна елемента одягу
     * @throws IllegalArgumentException якщо текстове поле порожнє або ціна від'ємна
     */
    public Clothes(String name, ClothesSize size, String color, String material, double price) {
        setName(name);
        setSize(size);
        setColor(color);
        setMaterial(material);
        setPrice(price);
        objectCount++;
    }

    /**
     * Створює копію іншого елемента одягу.
     *
     * @param other елемент одягу, який потрібно скопіювати
     * @throws IllegalArgumentException якщо об'єкт для копіювання null
     */
    public Clothes(Clothes other) {
        this(
                requireNonNullClothes(other).name,
                requireNonNullClothes(other).size,
                requireNonNullClothes(other).color,
                requireNonNullClothes(other).material,
                requireNonNullClothes(other).price
        );
    }

    /**
     * Повертає кількість створених об'єктів Clothes.
     *
     * @return кількість створених об'єктів Clothes
     */
    public static int getObjectCount() {
        return objectCount;
    }

    /**
     * Повертає назву елемента одягу.
     *
     * @return назва елемента одягу
     */
    public String getName() {
        return name;
    }

    /**
     * Встановлює назву елемента одягу.
     *
     * @param name нова назва
     * @throws IllegalArgumentException якщо назва порожня
     */
    public void setName(String name) {
        validateText(name, "Назва");
        this.name = name;
    }

    /**
     * Повертає розмір елемента одягу.
     *
     * @return розмір елемента одягу
     */
    public ClothesSize getSize() {
        return size;
    }

    /**
     * Встановлює розмір елемента одягу.
     *
     * @param size новий розмір
     * @throws IllegalArgumentException якщо розмір не вказаний
     */
    public void setSize(ClothesSize size) {
        validateSize(size);
        this.size = size;
    }

    /**
     * Повертає колір елемента одягу.
     *
     * @return колір елемента одягу
     */
    public String getColor() {
        return color;
    }

    /**
     * Встановлює колір елемента одягу.
     *
     * @param color новий колір
     * @throws IllegalArgumentException якщо колір порожній
     */
    public void setColor(String color) {
        validateText(color, "Колір");
        this.color = color;
    }

    /**
     * Повертає матеріал елемента одягу.
     *
     * @return матеріал елемента одягу
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Встановлює матеріал елемента одягу.
     *
     * @param material новий матеріал
     * @throws IllegalArgumentException якщо матеріал порожній
     */
    public void setMaterial(String material) {
        validateText(material, "Матеріал");
        this.material = material;
    }

    /**
     * Повертає ціну елемента одягу.
     *
     * @return ціна елемента одягу
     */
    public double getPrice() {
        return price;
    }

    /**
     * Встановлює ціну елемента одягу.
     *
     * @param price нова ціна
     * @throws IllegalArgumentException якщо ціна від'ємна
     */
    public void setPrice(double price) {
        validatePrice(price);
        this.price = price;
    }

    /**
     * Перевіряє, що текстове значення не є порожнім.
     *
     * @param value значення для перевірки
     * @param fieldName назва поля для повідомлення про помилку
     * @throws IllegalArgumentException якщо значення null або порожнє
     */
    private static void validateText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не може бути порожнім");
        }
    }

    /**
     * Перевіряє, що розмір одягу вказаний.
     *
     * @param size розмір для перевірки
     * @throws IllegalArgumentException якщо розмір null
     */
    private static void validateSize(ClothesSize size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір не може бути порожнім");
        }
    }

    /**
     * Перевіряє, що ціна не є від'ємною.
     *
     * @param price ціна для перевірки
     * @throws IllegalArgumentException якщо ціна від'ємна
     */
    private static void validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Ціна не може бути від'ємною");
        }
    }

    /**
     * Перевіряє, що об'єкт для копіювання існує.
     *
     * @param clothes об'єкт для перевірки
     * @return переданий об'єкт, якщо він не null
     * @throws IllegalArgumentException якщо об'єкт null
     */
    private static Clothes requireNonNullClothes(Clothes clothes) {
        if (clothes == null) {
            throw new IllegalArgumentException("Об'єкт для копіювання не може бути null");
        }
        return clothes;
    }

    /**
     * Повертає текстовий опис елемента одягу.
     *
     * @return опис елемента одягу
     */
    @Override
    public String toString() {
        return "Одяг{"
                + "назва='" + name + '\''
                + ", розмір='" + size + '\''
                + ", колір='" + color + '\''
                + ", матеріал='" + material + '\''
                + ", ціна=" + price
                + '}';
    }

    /**
     * Порівнює елементи одягу за значеннями всіх полів.
     *
     * @param object об'єкт для порівняння
     * @return true, якщо всі поля об'єктів збігаються
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Clothes clothes)) {
            return false;
        }
        return Double.compare(price, clothes.price) == 0
                && Objects.equals(name, clothes.name)
                && Objects.equals(size, clothes.size)
                && Objects.equals(color, clothes.color)
                && Objects.equals(material, clothes.material);
    }

    /**
     * Повертає хеш-код на основі всіх полів елемента одягу.
     *
     * @return хеш-код елемента одягу
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, size, color, material, price);
    }
}
