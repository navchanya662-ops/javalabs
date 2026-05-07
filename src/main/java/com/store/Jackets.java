package com.store;

/**
 * Описує куртку як спеціалізований тип одягу.
 */
public class Jackets extends Clothes {
    private boolean hasHood;
    private String insulationType;

    /**
     * Створює новий об'єкт куртки.
     *
     * @param name назва куртки
     * @param size розмір куртки
     * @param color колір куртки
     * @param material матеріал куртки
     * @param price ціна куртки
     * @param hasHood чи має куртка капюшон
     * @param insulationType тип утеплення куртки
     */
    public Jackets(String name, ClothesSize size, String color, String material,
                   double price, boolean hasHood, String insulationType) {
        super(name, size, color, material, price);
        this.hasHood = hasHood;
        setInsulationType(insulationType);
    }

    /**
     * Перевіряє, чи має куртка капюшон.
     *
     * @return true, якщо куртка має капюшон
     */
    public boolean hasHood() {
        return hasHood;
    }

    /**
     * Встановлює наявність капюшона в куртки.
     *
     * @param hasHood true, якщо куртка має капюшон
     */
    public void setHasHood(boolean hasHood) {
        this.hasHood = hasHood;
    }

    /**
     * Повертає тип утеплення куртки.
     *
     * @return тип утеплення
     */
    public String getInsulationType() {
        return insulationType;
    }

    /**
     * Встановлює тип утеплення куртки.
     *
     * @param insulationType тип утеплення
     * @throws IllegalArgumentException якщо тип утеплення порожній
     */
    public void setInsulationType(String insulationType) {
        if (insulationType == null || insulationType.trim().isEmpty()) {
            throw new InvalidFieldValueException("Тип утеплення не може бути порожнім");
        }
        this.insulationType = insulationType;
    }

    /**
     * Повертає текстовий опис куртки.
     *
     * @return опис куртки
     */
    @Override
    public String toString() {
        return "Куртка{"
                + "назва='" + getName() + '\''
                + ", розмір='" + getSize() + '\''
                + ", колір='" + getColor() + '\''
                + ", матеріал='" + getMaterial() + '\''
                + ", ціна=" + getPrice()
                + ", капюшон=" + (hasHood ? "так" : "ні")
                + ", тип утеплення='" + insulationType + '\''
                + '}';
    }
}
