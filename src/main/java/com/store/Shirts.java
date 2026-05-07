package com.store;

/**
 * Описує сорочку як спеціалізований тип одягу.
 */
public class Shirts extends Clothes {
    private String sleeveType;

    /**
     * Створює новий об'єкт сорочки.
     *
     * @param name назва сорочки
     * @param size розмір сорочки
     * @param color колір сорочки
     * @param material матеріал сорочки
     * @param price ціна сорочки
     * @param sleeveType тип рукава
     */
    public Shirts(String name, ClothesSize size, String color, String material, double price, String sleeveType) {
        super(name, size, color, material, price);
        setSleeveType(sleeveType);
    }

    /**
     * Повертає тип рукава сорочки.
     *
     * @return тип рукава
     */
    public String getSleeveType() {
        return sleeveType;
    }

    /**
     * Встановлює тип рукава сорочки.
     *
     * @param sleeveType тип рукава
     * @throws IllegalArgumentException якщо тип рукава порожній
     */
    public void setSleeveType(String sleeveType) {
        if (sleeveType == null || sleeveType.trim().isEmpty()) {
            throw new InvalidFieldValueException("Тип рукава не може бути порожнім");
        }
        this.sleeveType = sleeveType;
    }

    /**
     * Повертає текстовий опис сорочки.
     *
     * @return опис сорочки
     */
    @Override
    public String toString() {
        return "Сорочка{"
                + "назва='" + getName() + '\''
                + ", розмір='" + getSize() + '\''
                + ", колір='" + getColor() + '\''
                + ", матеріал='" + getMaterial() + '\''
                + ", ціна=" + getPrice()
                + ", тип рукава='" + sleeveType + '\''
                + '}';
    }
}
