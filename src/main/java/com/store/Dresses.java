package com.store;

/**
 * Описує сукню як спеціалізований тип одягу.
 */
public class Dresses extends Clothes {
    private String lengthType;
    private boolean isFormal;

    /**
     * Створює новий об'єкт сукні.
     *
     * @param name назва сукні
     * @param size розмір сукні
     * @param color колір сукні
     * @param material матеріал сукні
     * @param price ціна сукні
     * @param lengthType тип довжини сукні
     * @param isFormal чи є сукня офіційною
     */
    public Dresses(String name, ClothesSize size, String color, String material,
                   double price, String lengthType, boolean isFormal) {
        super(name, size, color, material, price);
        setLengthType(lengthType);
        this.isFormal = isFormal;
    }

    /**
     * Повертає тип довжини сукні.
     *
     * @return тип довжини сукні
     */
    public String getLengthType() {
        return lengthType;
    }

    /**
     * Встановлює тип довжини сукні.
     *
     * @param lengthType тип довжини сукні
     * @throws IllegalArgumentException якщо тип довжини порожній
     */
    public void setLengthType(String lengthType) {
        if (lengthType == null || lengthType.trim().isEmpty()) {
            throw new IllegalArgumentException("Тип довжини не може бути порожнім");
        }
        this.lengthType = lengthType;
    }

    /**
     * Перевіряє, чи є сукня офіційною.
     *
     * @return true, якщо сукня офіційна
     */
    public boolean isFormal() {
        return isFormal;
    }

    /**
     * Встановлює ознаку офіційності сукні.
     *
     * @param formal true, якщо сукня офіційна
     */
    public void setFormal(boolean formal) {
        isFormal = formal;
    }

    /**
     * Повертає текстовий опис сукні.
     *
     * @return опис сукні
     */
    @Override
    public String toString() {
        return "Сукня{"
                + "назва='" + getName() + '\''
                + ", розмір='" + getSize() + '\''
                + ", колір='" + getColor() + '\''
                + ", матеріал='" + getMaterial() + '\''
                + ", ціна=" + getPrice()
                + ", тип довжини='" + lengthType + '\''
                + ", офіційна=" + (isFormal ? "так" : "ні")
                + '}';
    }
}
