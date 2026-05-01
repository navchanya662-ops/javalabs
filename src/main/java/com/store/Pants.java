package com.store;

/**
 * Описує штани як спеціалізований тип одягу.
 */
public class Pants extends Clothes {
    private boolean hasPockets;

    /**
     * Створює новий об'єкт штанів.
     *
     * @param name назва штанів
     * @param size розмір штанів
     * @param color колір штанів
     * @param material матеріал штанів
     * @param price ціна штанів
     * @param hasPockets чи мають штани кишені
     */
    public Pants(String name, ClothesSize size, String color, String material, double price, boolean hasPockets) {
        super(name, size, color, material, price);
        this.hasPockets = hasPockets;
    }

    /**
     * Перевіряє, чи мають штани кишені.
     *
     * @return true, якщо штани мають кишені
     */
    public boolean hasPockets() {
        return hasPockets;
    }

    /**
     * Встановлює наявність кишень у штанів.
     *
     * @param hasPockets true, якщо штани мають кишені
     */
    public void setHasPockets(boolean hasPockets) {
        this.hasPockets = hasPockets;
    }

    /**
     * Повертає текстовий опис штанів.
     *
     * @return опис штанів
     */
    @Override
    public String toString() {
        return "Штани{"
                + "назва='" + getName() + '\''
                + ", розмір='" + getSize() + '\''
                + ", колір='" + getColor() + '\''
                + ", матеріал='" + getMaterial() + '\''
                + ", ціна=" + getPrice()
                + ", кишені=" + (hasPockets ? "так" : "ні")
                + '}';
    }
}
